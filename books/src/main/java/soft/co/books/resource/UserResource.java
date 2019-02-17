package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.domain.service.CartServices;
import soft.co.books.domain.service.UserService;
import soft.co.books.domain.service.dto.PageResultDTO;
import soft.co.books.domain.service.dto.PasswordChangeDTO;
import soft.co.books.domain.service.dto.UserDTO;
import soft.co.books.domain.service.session.ShippingSession;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(description = "User operations")
public class UserResource {

    private final UserService userService;
    private final CartServices cartServices;

    public UserResource(UserService userService, CartServices cartServices) {
        this.userService = userService;
        this.cartServices = cartServices;
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER_LIST + "\")")
    public PageResultDTO<UserDTO> getAllUsers(@RequestBody UserDTO userDTO, Pageable pageable) {
        return userService.findAll(userDTO, pageable);
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER_MANAGEMENT + "\")")
    public UserDTO getUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() != null)
            return userService.findOne(userDTO.getId())
                    .map(UserDTO::new).get();
        else
            return null;
    }

    @PostMapping("/saveUser")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER_MANAGEMENT + "\")")
    public UserDTO saveUser(@Valid @RequestBody UserDTO userDTO) {
        if (userDTO.getId() == null || userDTO.getId().isEmpty())
            return userService.createUser(userDTO).get();
        else
            return userService.updateUser(userDTO).get();
    }

    @PostMapping("/updateAccount")
    public UserDTO updateAccount(@Valid @RequestBody UserDTO userDTO) {
        UserDTO user = userService.updateAccount(userDTO).get();
        if (user != null) {
            ShippingSession shippingSession = new ShippingSession();
            shippingSession.setLine1(user.getLine1());
            shippingSession.setLine2(user.getLine2());
            shippingSession.setCity(user.getCity());
            shippingSession.setCountry(user.getCountry());
            shippingSession.setEmail(user.getEmail());
            shippingSession.setFullName(user.getFullName());
            shippingSession.setPhone(String.valueOf(user.getPhone()));
            shippingSession.setState(user.getState());
            shippingSession.setPostalCode(user.getCp());
            cartServices.addShippingInfo(shippingSession);
        }

        return user;
    }

    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER_MANAGEMENT + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/registerUser")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/changeLang")
    public ResponseEntity<Void> changeLang(@RequestBody String lang) {
        userService.updateLang(lang);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
