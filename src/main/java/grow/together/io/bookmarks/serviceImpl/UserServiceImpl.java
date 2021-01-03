package grow.together.io.bookmarks.serviceImpl;

import grow.together.io.bookmarks.config.SpringSecurityAuditor;
import grow.together.io.bookmarks.domain.GroupStatus;
import grow.together.io.bookmarks.domain.Role;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.PageableResult;
import grow.together.io.bookmarks.dtoModel.UserDtaoIn;
import grow.together.io.bookmarks.dtoModel.UserDtaoOut;
import grow.together.io.bookmarks.errorHandler.BadRequestException;
import grow.together.io.bookmarks.errorHandler.ResourceNotFoundException;
import grow.together.io.bookmarks.eventListener.UserRegistrationEvent;
import grow.together.io.bookmarks.repository.PostRepository;
import grow.together.io.bookmarks.repository.RoleRepository;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final SpringSecurityAuditor springSecurityAuditor;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PostRepository postRepository, PasswordEncoder passwordEncoder, TokenStore tokenStore, SpringSecurityAuditor springSecurityAuditor, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.springSecurityAuditor = springSecurityAuditor;
        this.eventPublisher = eventPublisher;
    }


    @Override
    public DataResponse<Void> createUser(UserDtaoIn userDtaoIn) {
        User user = new User();

        user.setGithub(userDtaoIn.getGithub());
        user.setGmail(userDtaoIn.getGmail());
        user.setName(userDtaoIn.getName());
        //Password will be encoded later
        user.setPassword(passwordEncoder.encode(userDtaoIn.getPassword()));
        Role role = this.roleRepository.findByName(userDtaoIn.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found with Role Name: " + userDtaoIn.getRole()));
        user.setRole(role);
        this.userRepository.save(user);
        eventPublisher.publishEvent(new UserRegistrationEvent(user));
        return new DataResponse<>("User Created Successfully", HttpStatus.CREATED.value());
    }

    @Override
    public PageableResult<UserDtaoOut> getAllUser(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        Page<User> userPage = this.userRepository.findAll(PageRequest.of(page - 1, size));
        return new PageableResult<>(page,
                size,
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.getContent().stream().map(user -> {
                    return getUserDtaoOut(user, this.postRepository);
                }).collect(Collectors.toList())
        );
    }

    public static UserDtaoOut getUserDtaoOut(User user, PostRepository postRepository) {
        UserDtaoOut userMapper = new UserDtaoOut();

        userMapper.setActive(user.isActive());
        userMapper.setGithub(user.getGithub());
        userMapper.setGmail(user.getGmail());
        userMapper.setName(user.getName());
        userMapper.setDelete(user.isDelete());
        userMapper.setPublicPost(postRepository.countPostByStatus(user.getId(), GroupStatus.PUBLIC));
        userMapper.setPrivatePost(postRepository.countPostByStatus(user.getId(), GroupStatus.PRIVATE));
        userMapper.setCreated_at(user.getCreatedAt().toString());

        return userMapper;
    }

    @Override
    public DataResponse<UserDtaoOut> getUserById(Long user_id) {
        User user = this.getById(user_id);
        return new DataResponse<>("User Load Successfully", HttpStatus.OK.value(), getUserDtaoOut(user, this.postRepository));
    }

    @Override
    public DataResponse<Void> updateUserStatus(Long user_id, boolean status) {

        User user = this.getById(user_id);

        user.setActive(status);
        this.userRepository.save(user);

        return new DataResponse<>("Status Updated Successfully", HttpStatus.OK.value());
    }

    @Override
    public DataResponse<Void> updateUser(UserDtaoIn userDtaoIn) {
        User user = getUser(this.springSecurityAuditor.getCurrentAuditor().get());

        this.userRepository.save(userMapper(user, userDtaoIn, roleRepository));

        return new DataResponse<>("User Updated Successfully", HttpStatus.OK.value());
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


    public User getById(Long user_id) {
        return this.userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("No User Found With Id :" + user_id));
    }


    User getUser(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found With User Email " + email));
    }


    public static User userMapper(User user, UserDtaoIn userDtaoIn, RoleRepository roleRepository) {

        user.setGithub(userDtaoIn.getGithub());
        user.setGmail(userDtaoIn.getGmail());
        user.setName(userDtaoIn.getName());
        Role role = roleRepository.findByName(userDtaoIn.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found with Role Name: " + userDtaoIn.getRole()));
        user.setRole(role);

        return user;
    }
}
