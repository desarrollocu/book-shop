package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.domain.service.UserService;
import soft.co.books.domain.service.dto.UserDTO;
import soft.co.books.configuration.security.other.AuthoritiesConstants;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Api(description="User operations")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER_LIST1 + "\")")
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER_MANAGEMENT + "\")")
    public UserDTO getUser(@RequestBody UserDTO userDTO) {
        return userService.findOne(userDTO.getId())
                .map(UserDTO::new).orElse(null);
    }
}
