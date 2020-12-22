package grow.together.io.bookmarks.serviceImpl;

import grow.together.io.bookmarks.domain.Permission;
import grow.together.io.bookmarks.domain.Role;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.errorHandler.ResourceNotFoundException;
import grow.together.io.bookmarks.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CustomerUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomerUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = this.userRepository.findByEmail(s).orElseThrow(() -> new ResourceNotFoundException("UserName or password Not Correct"));
        Optional<User> userInfo = this.userRepository.findByEmail(s);

        if (!userInfo.isPresent()) {
            throw new UsernameNotFoundException("UserName or password Not Correct");
        } else if (userInfo.get().isAccountNonLocked()) {
            throw new UsernameNotFoundException("Your account has been locked due to 3 failed attempts. It will be unlocked after 24 hours.");
        } else if (!userInfo.get().isActive()) {
            throw new UsernameNotFoundException("You Account Have Not yet been Activated Please Contact The Admin");
        } else if (userInfo.get().isDelete()) {
            throw new UsernameNotFoundException("UserName is Not Longer Part Of The System The account Have Been Delete");
        }
        return new org.springframework.security.core.userdetails.User(
                userInfo.get().getGmail(), userInfo.get().getPassword(), userInfo.get().isActive(), true, true,
                userInfo.get().isAccountNonLocked(), getAuthorities(userInfo.get().getRole()));
    }


    private Collection<? extends GrantedAuthority> getAuthorities(Role roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }


    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = privileges.stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege))
                .collect(Collectors.toList());
        return authorities;
    }

    private List<String> getPrivileges(Role roles) {
        List<String> privileges = roles.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
        return privileges;
    }

}
