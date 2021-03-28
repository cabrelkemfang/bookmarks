package grow.together.io.bookmarks.serviceimpl;


import grow.together.io.bookmarks.common.VariableName;
import grow.together.io.bookmarks.domain.GroupStatus;
import grow.together.io.bookmarks.domain.Role;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.dtomodel.*;
import grow.together.io.bookmarks.errorhandler.BadRequestException;
import grow.together.io.bookmarks.errorhandler.ResourceNotFoundException;
import grow.together.io.bookmarks.eventlistener.UserRegistrationEvent;
import grow.together.io.bookmarks.eventlistener.UserStatusEvent;
import grow.together.io.bookmarks.repository.BookmarkRepository;
import grow.together.io.bookmarks.repository.RoleRepository;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BookmarkRepository bookmarkRepository, PasswordEncoder passwordEncoder, TokenStore tokenStore, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.eventPublisher = eventPublisher;
    }


    @Override
    @Transactional
    public DataResponse<Void> createUser(UserDtaoIn userDtaoIn) {
        User user = userMapper(userDtaoIn, "role_user");
        this.userRepository.save(user);

        eventPublisher.publishEvent(new UserRegistrationEvent(user));
        return new DataResponse<>("User Created Successfully", HttpStatus.CREATED.value());
    }

    @Override
    public DataResponse<Void> createAdminUser(UserDtaoIn userDtaoIn) {
        User user = userMapper(userDtaoIn, "role_admin");
        this.userRepository.save(user);

        eventPublisher.publishEvent(new UserRegistrationEvent(user));
        return new DataResponse<>("User Admin Created Successfully", HttpStatus.CREATED.value());
    }

    @Override
    public PageableResult<UserDtaoOut> getAllUser(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }
        Page<User> userPage = this.userRepository.findAll(PageRequest.of(page - 1, size));
        return new PageableResult<>(page,
                size,
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.getContent().stream().map(user -> getUserDtoOut(user, this.bookmarkRepository)).collect(Collectors.toList())
        );
    }

    @Override
    public DataResponse<UserDtaoOut> getUserById(Long userId) {
        User user = this.getById(userId);
        return new DataResponse<>("User Load Successfully", HttpStatus.OK.value(), getUserDtoOut(user, this.bookmarkRepository));
    }

    @Override
    @Transactional
    public DataResponse<Void> updateUserStatus(String email, boolean status) {

        User user = this.getUser(email);

        user.setActive(status);
        this.userRepository.save(user);

        eventPublisher.publishEvent(new UserStatusEvent(user));
        return new DataResponse<>("Status Updated Successfully", HttpStatus.OK.value());
    }

    @Override
    public DataResponse<Void> updateUser(UserDtaoIn userDtaoIn, String name) {
        User user = getUser(name);

        this.userRepository.save(userMapper(user, userDtaoIn));

        return new DataResponse<>("User Updated Successfully", HttpStatus.OK.value());
    }

    @Override
    public DataResponse<Void> resetPassword(String userEmail) {
        return null;
    }

    @Override
    public DataResponse<Void> logout(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")) {
            String tokenValue = authorization.replace("Bearer", "").trim();

            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);

            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
            tokenStore.removeRefreshToken(refreshToken);
        }
        return new DataResponse<>("Logout Successfully", HttpStatus.NO_CONTENT.value());
    }

    @Override
    public DataResponse<LoginUser> loginUser() {

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        LoginUser loginUser = new LoginUser();

        loginUser.setEmail(principal.getName());

        List<String> permission = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        loginUser.setPermissions(permission);

        return new DataResponse<>("Current User", HttpStatus.OK.value(), loginUser);

    }


    private User getById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No User Found With Id :" + userId));
    }


    private User getUser(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found With User Email " + email));
    }


    private static User userMapper(User user, UserDtaoIn userDtaoIn) {

        user.setGithub(userDtaoIn.getGithub());
        user.setEmail(userDtaoIn.getEmail());
        user.setName(userDtaoIn.getName());

        return user;
    }

    private User userMapper(UserDtaoIn userDtaoIn, String roleName) {
        User user = new User();

        user.setGithub(userDtaoIn.getGithub());
        user.setEmail(userDtaoIn.getEmail());
        user.setName(userDtaoIn.getName());
        user.setPassword(passwordEncoder.encode(userDtaoIn.getPassword()));
        Role role = this.roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found with Role Name: " + roleName));
        user.setRole(role);

        return user;
    }

    private static UserDtaoOut getUserDtoOut(User user, BookmarkRepository bookmarkRepository) {
        UserDtaoOut userMapper = new UserDtaoOut();

        userMapper.setId(user.getId());
        userMapper.setActive(user.isActive());
        userMapper.setGithub(user.getGithub());
        userMapper.setEmail(user.getEmail());
        userMapper.setName(user.getName());
        userMapper.setRoleName(user.getRole().getName());
        userMapper.setPublicBookmarks(bookmarkRepository.countBookmarksByStatus(user.getId(), GroupStatus.PUBLIC));
        userMapper.setPrivateBookmarks(bookmarkRepository.countBookmarksByStatus(user.getId(), GroupStatus.PRIVATE));
        userMapper.setCreatedAt(user.getCreatedAt().toString());

        return userMapper;
    }
}
