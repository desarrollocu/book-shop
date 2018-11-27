package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.service.UserService;
import soft.co.books.domain.service.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Api(description="Account operations")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserService userService;

    public AccountResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/auth")
//    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
//    @Timed
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
                .map(UserDTO::new)
                .orElseThrow(() -> new CustomizeException("User could not be found"));
    }
}
