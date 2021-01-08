package grow.together.io.bookmarks.config;

import grow.together.io.bookmarks.serviceimpl.CustomerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomerUserService userService;
    private final ResponseExceptionTranslator responseExceptionTranslator;

    @Value("${spring.aouth.client_id}")
    private String clientId;

    @Value("${spring.aouth.client_secret}")
    private String clientSecret;

    @Value("${spring.aouth.tokenValidity}")
    private int accessTokenValiditySeconds;

    @Value(("${spring.aouth.signingKey}"))
    private String signingKey;

    @Value("${spring.aouth.resourceIds}")
    private String resourceIds;

    @Autowired
    public AuthorizationServerConfiguration(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, CustomerUserService userService, ResponseExceptionTranslator responseExceptionTranslator) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.responseExceptionTranslator = responseExceptionTranslator;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(accessTokenValiditySeconds)
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("read", "write")
                .resourceIds(resourceIds);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .accessTokenConverter(accessTokenConverter())
                .tokenStore(tokenStore())
                .userDetailsService(userService)
                .authenticationManager(authenticationManager)
                .exceptionTranslator(responseExceptionTranslator);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer)  {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}
