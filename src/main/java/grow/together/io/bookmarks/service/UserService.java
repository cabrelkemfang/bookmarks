package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface UserService {
    DataResponse<Void> createUser(UserDtaoIn userDtaoIn);

    DataResponse<Void> createAdminUser(UserDtaoIn userDtaoIn);

    PageableResult<UserDtaoOut> getAllUser(int page, int size);

    DataResponse<UserDtaoOut> getUserById(Long user_id);

    DataResponse<Void> updateUserStatus(String email, boolean status);

    DataResponse<Void> updateUser(UserDtaoIn userDtaoIn, String name);

    DataResponse<Void> resetPassword(String userEmail);

    DataResponse<Void> logout(HttpServletRequest request);

    DataResponse<LoginUser> loginUser();
}
