package grow.together.io.bookmarks.dtomodel;

import grow.together.io.bookmarks.validation.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UniqueEmail
public class SubcriberDtoIn {
    @NotEmpty(message = "Please provide Name")
    private String name;

    @Email(message = "Email not Valid")
    @NotEmpty(message = "Please Provide the Email")
    private String email;
}
