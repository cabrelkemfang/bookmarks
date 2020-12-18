package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.PageableResult;
import grow.together.io.bookmarks.dtoModel.UserDtaoIn;
import grow.together.io.bookmarks.dtoModel.UserDtaoOut;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    DataResponse<Void> createUser(UserDtaoIn userDtaoIn);

    PageableResult<UserDtaoOut> getAllUser(int page, int size);

    DataResponse<UserDtaoOut> getUserById(Long user_id);

    DataResponse<Void> updateUserStatus(Long user_id , boolean status);

    DataResponse<Void> updateUser(Long user_id, UserDtaoIn userDtaoIn);

    DataResponse<Void> logout(HttpServletRequest request);
}
