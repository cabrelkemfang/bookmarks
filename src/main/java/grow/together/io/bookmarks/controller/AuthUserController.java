package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.LoginUser;
import grow.together.io.bookmarks.dtomodel.UserDtaoIn;
import grow.together.io.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookmarks/v1/oauth")
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

//    @DeleteMapping(path = "/logout/{tokenValue}")
//    public DataResponse<Void> logout(@PathVariable String tokenValue) {
//        return this.userService.logout(tokenValue);
//    }
}
