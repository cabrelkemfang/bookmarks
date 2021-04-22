package grow.together.io.bookmarks.mapper;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.dtomodel.UserDtaoIn;
import grow.together.io.bookmarks.dtomodel.UserDtoOut;

public final class UserMapper {


    public static UserDtoOut map(final User user, long publicCount, long privateCount) {

        final UserDtoOut userDtoOut = new UserDtoOut();
        userDtoOut.setId(user.getId());
        userDtoOut.setActive(user.isActive());
        userDtoOut.setGithub(user.getGithub());
        userDtoOut.setEmail(user.getEmail());
        userDtoOut.setName(user.getName());
        userDtoOut.setRoleName(user.getRole().getName());
        userDtoOut.setPublicBookmarks(publicCount);
        userDtoOut.setPrivateBookmarks(privateCount);
        userDtoOut.setCreatedAt(user.getCreatedAt().toString());

        return userDtoOut;
    }


    public static User map(User user, final UserDtaoIn userDtaoIn) {
        user.setGithub(userDtaoIn.getGithub());
        user.setEmail(userDtaoIn.getEmail());
        user.setName(userDtaoIn.getName());

        return user;
    }
}
