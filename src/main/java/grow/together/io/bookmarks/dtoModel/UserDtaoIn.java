package grow.together.io.bookmarks.dtoModel;

import grow.together.io.bookmarks.validator.annotation.PasswordConfirmed;
import grow.together.io.bookmarks.validator.annotation.UniqueEmail;
import grow.together.io.bookmarks.validator.annotation.UniqueGithub;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UniqueEmail
@PasswordConfirmed
public class UserDtaoIn {
    @NotEmpty(message = "Please Provide The Email")
    @Email(message = "Email Is Not Valid")
    private String gmail;

    @NotEmpty(message = "Please Provide You GitHub")
    @UniqueGithub
    private String github;

    @NotEmpty(message = "Please Provide You Password")
    private String password;

    @NotEmpty(message = "Please Provide a Confirmation Password")
    private String ConfirmPassword;

    @NotEmpty(message = "Please Provide You Name")
    private String name;

    @NotEmpty(message = "Please Provide a Role")
    private String role;

}
