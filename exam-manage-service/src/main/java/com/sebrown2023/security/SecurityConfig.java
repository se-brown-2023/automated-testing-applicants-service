package com.sebrown2023.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {
    @Value("spring.security.oauth2.client.registration.keycloak.clientSecret")
    private String clientSecret;

    @Value("spring.security.oauth2.client.registration.keycloak.clientId")
    private String clientId;

    @Value("spring.security.oauth2.client.provider.keycloak.tokenUri")
    private String tokenUri;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/**").permitAll() // permit all the routers after swagger-ui.html
                .anyRequest()
                .authenticated()
                //.hasAnyRole("ADMIN")
                .and()
                .sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(auth -> auth.jwt(s -> s.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())))
                .cors(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .formLogin(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public InMemoryClientRegistrationRepository getRegistration() {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("automated-testing-applicants-service")
                .tokenUri(tokenUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .build();
        return new InMemoryClientRegistrationRepository(registration);
    }
}

@Component
class KeycloakJwtAuthenticationConverter extends JwtAuthenticationConverter {
    public static final String PRINCIPAL_CLAIM_NAME = "preferred_username";

    public KeycloakJwtAuthenticationConverter() {
        var grantedAuthoritiesConverter = new KeycloakRealmRolesGrantedAuthoritiesConverter();

        setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        setPrincipalClaimName(PRINCIPAL_CLAIM_NAME);
    }
}

class KeycloakRealmRolesGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String rolePrefix = "ROLE_";
    public KeycloakRealmRolesGrantedAuthoritiesConverter() {

    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> realmAccess = source.getClaim("realm_access");
        if (Objects.isNull(realmAccess)) {
            return Collections.emptySet();
        }

        Object roles = realmAccess.get("roles");
        if (Objects.isNull(roles) || !Collection.class.isAssignableFrom(roles.getClass())) {
            return Collections.emptySet();
        }

        var rolesCollection = (Collection<?>) roles;

        return rolesCollection.stream()
                .filter(String.class::isInstance)
                .map(x -> new SimpleGrantedAuthority(rolePrefix + x))
                .collect(Collectors.toSet());
    }
}
