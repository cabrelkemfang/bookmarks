package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.UserDtaoIn;
import grow.together.io.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/bookmarks/oauth")
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
}
