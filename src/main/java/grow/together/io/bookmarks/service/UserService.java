package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.dtomodel.UserDtaoIn;
import grow.together.io.bookmarks.dtomodel.UserDtaoOut;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    DataResponse<Void> createUser(UserDtaoIn userDtaoIn);

    DataResponse<Void> createAdminUser(UserDtaoIn userDtaoIn);

    PageableResult<UserDtaoOut> getAllUser(int page, int size);

    DataResponse<UserDtaoOut> getUserById(Long user_id);

    DataResponse<Void> updateUserStatus(String email, boolean status);

    DataResponse<Void> updateUser(UserDtaoIn userDtaoIn, String name);

    DataResponse<Void> logout(HttpServletRequest request);
}
