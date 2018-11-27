package soft.co.books.configuration.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public static final String UNAUTHORIZED_MESSAGE = "Authentication failed";

    public AjaxAuthenticationFailureHandler() {
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication failed");
    }
}
