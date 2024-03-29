package grow.together.io.bookmarks.dtomodel;

import grow.together.io.bookmarks.validation.ConfirmedPassword;
import grow.together.io.bookmarks.validation.UniqueEmail;
import grow.together.io.bookmarks.validation.UniqueGithub;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UniqueEmail
@ConfirmedPassword
public class UserDtaoIn {
    @NotEmpty(message = "Please Provide The Email")
    @Email(message = "Email Is Not Valid")
    private String email;

    @NotEmpty(message = "Please Provide You GitHub")
    @UniqueGithub
    private String github;

    @NotEmpty(message = "Please Provide You Password")
    private String password;

    @NotEmpty(message = "Please Provide a Confirmation Password")
    private String confirmPassword;

    @NotEmpty(message = "Please Provide You Name")
    private String name;
}
