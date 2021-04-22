package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.*;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    DataResponse<Void> createUser(UserDtaoIn userDtaoIn);

    DataResponse<Void> createAdminUser(UserDtaoIn userDtaoIn);

    PageableResult<UserDtoOut> fetchUsers(int page, int size);

    DataResponse<UserDtoOut> findUser(Long userId);

    DataResponse<Void> updateUserStatus(String email, boolean status);

    DataResponse<Void> updateUser(UserDtaoIn userDtaoIn, String name);

    DataResponse<Void> resetPassword(String userEmail);

    DataResponse<Void> changePassword(ResetPasswordDto resetPasswordDto, String token);

    DataResponse<Void> logout(HttpServletRequest request);

    DataResponse<LoginUser> loginUser();
}
