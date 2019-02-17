package soft.co.books.configuration.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import soft.co.books.domain.collection.User;
import soft.co.books.domain.service.CartServices;
import soft.co.books.domain.service.UserService;
import soft.co.books.domain.service.session.ShippingSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private CartServices cartServices;


    public AjaxAuthenticationSuccessHandler() {
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        cartServices.clearSession();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findOneByUserName(userDetails.getUsername()).get();
            if (user != null) {
                ShippingSession shippingSession = new ShippingSession();
                shippingSession.setState(user.getState());
                shippingSession.setPostalCode(user.getCp());
                shippingSession.setPhone(String.valueOf(user.getPhone()));
                shippingSession.setFullName(user.getFirstName() + " " + user.getLastName());
                shippingSession.setCountry(user.getCountry());
                shippingSession.setLine1(user.getLine1());
                shippingSession.setLine2(user.getLine2());
                shippingSession.setCity(user.getCity());
                shippingSession.setEmail(user.getEmail());
                cartServices.addShippingInfo(shippingSession);
            }
        }
        response.setStatus(HttpStatus.OK.value());
    }
}
