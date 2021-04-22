package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.*;
import grow.together.io.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/oauth")
public class AuthUserController {

    private final UserService userService;

    @Autowired
    public AuthUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/sign-up")
    public DataResponse<Void> createUser(@Valid @RequestBody UserDtaoIn userDtaoIn) {
        return this.userService.createUser(userDtaoIn);
    }

    @GetMapping(path = "user-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse<LoginUser> getLoginUserInfo() {
        return this.userService.loginUser();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/reset-password")
    public DataResponse<Void> resetPassword(@RequestParam String email) {
        return this.userService.resetPassword(email);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/change-password")
    public DataResponse<Void> changePassword(@RequestBody ResetPasswordDto resetPasswordDto, @RequestParam String token) {
        return this.userService.changePassword(resetPasswordDto, token);
    }

//    @DeleteMapping(path = "/logout/{tokenValue}")
//    public DataResponse<Void> logout(@PathVariable String tokenValue) {
//        return this.userService.logout(tokenValue);
//    }
}
