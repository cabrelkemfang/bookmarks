package grow.together.io.bookmarks.dtomodel;


import grow.together.io.bookmarks.validator.annotation.PasswordConfirmed;
import grow.together.io.bookmarks.validator.annotation.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UniqueEmail
@PasswordConfirmed
public class ResetPasswordDto {

    @NotEmpty(message = "Please Provide You Password")
    private String password;

    @NotEmpty(message = "Please Provide a Confirmation Password")
    private String confirmPassword;
}
