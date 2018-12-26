package soft.co.books.configuration.error;

import com.mongodb.MongoWriteException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> throwable(Throwable ex) {
        ErrorDTO errorDTO = new ErrorDTO();

        if (ex instanceof MethodArgumentNotValidException) {
            errorDTO.setError("error.badRequest");
            Set<String> fields = new HashSet<>();
            ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().stream()
                    .forEach(fieldError -> fields.add(fieldError.getDefaultMessage()));
            errorDTO.setFields(new ArrayList<>(fields));
        } else if (ex instanceof DuplicateKeyException) {
            if (ex.getCause() instanceof MongoWriteException) {
                if (((MongoWriteException) ex.getCause()).getCode() == 11000) {
                    errorDTO.setError("error.duplicate");
                    String[] errorMsg = ex.getMessage().split("\"");
                    String value = errorMsg[1];
                    List<String> values = new ArrayList<>();
                    values.add(value);
                    errorDTO.setFields(values);
                }
            }
        } else if (ex instanceof CustomizeException) {
            errorDTO.setError(ex.getMessage());
        } else {
            errorDTO.setError("error.general");
        }
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
