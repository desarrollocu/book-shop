package soft.co.books.configuration.error;

import com.mongodb.MongoWriteException;
import com.paypal.api.payments.ErrorDetails;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.CustomError;
import soft.co.books.domain.service.ErrorService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class ExceptionTranslator {

    private final ErrorService errorService;

    public ExceptionTranslator(ErrorService errorService) {
        this.errorService = errorService;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> throwable(Throwable ex) {
        ErrorDTO errorDTO = new ErrorDTO();

        CustomError customError = new CustomError();
        customError.setError(ex.getMessage());
//        customError.setTraceElement(ex.getStackTrace());

        if (ex instanceof MethodArgumentNotValidException) {
            errorDTO.setError("error.badRequest");
            Set<String> fields = new HashSet<>();
            ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().stream()
                    .forEach(fieldError -> fields.add(fieldError.getDefaultMessage()));
            errorDTO.setFields(new ArrayList<>(fields));
            customError.setRealError(ex.getMessage());
        } else if (ex instanceof DuplicateKeyException) {
            if (ex.getCause() instanceof MongoWriteException) {
                if (((MongoWriteException) ex.getCause()).getCode() == 11000) {
                    errorDTO.setError("error.duplicate");
                    String[] errorMsg = ex.getMessage().split("\"");
                    String value = errorMsg[1];
                    List<String> values = new ArrayList<>();
                    values.add(value);
                    errorDTO.setFields(values);
                    customError.setRealError(ex.getMessage());
                }
            }
        } else if (ex instanceof CustomizeException) {
            errorDTO.setError(ex.getMessage());
            errorDTO.setFields(((CustomizeException) ex).getParams());
            customError.setRealError(((CustomizeException) ex).getRealError());
        } else if (ex instanceof PayPalRESTException) {
            if (((PayPalRESTException) ex).getResponsecode() == 400) {
                List<String> params = new ArrayList<>();
                for (ErrorDetails errorDetails : ((PayPalRESTException) ex).getDetails().getDetails()) {
                    if (errorDetails.getField().equals("city") || errorDetails.getField().equals("state")
                            || errorDetails.getField().equals("zip") || errorDetails.getField().equals("line1"))
                        params.add(errorDetails.getField());
                }
                customError.setRealError(ex.getMessage());
                throw new CustomizeException(Constants.ERR_PAYPAL_DATA, params);
            } else{
                customError.setRealError(ex.getMessage());
                throw new CustomizeException(Constants.ERR_PAYPAL_CONEX);

            }
        } else {
            errorDTO.setError("error.general");
        }

        errorService.save(customError);
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
