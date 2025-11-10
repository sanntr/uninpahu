package com.uninpahu.uninpahu.infraestructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguratio {

    final SecurityFilter securityFilter;

    public SecurityConfiguratio(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //Todos los endpoints que se realizen que deban se accesibles a culaquierea sin nececidad del jwt se debe registrar
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf->csrf.disable())
                .sessionManagement(sm-> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req->{
                    req.requestMatchers("/usuario/**").permitAll();//se debe registrar la url en este espacio
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                    // Endpoints públicos (sin autenticación)
                    req.requestMatchers(HttpMethod.GET, "/negocios/**", "/productos/**").permitAll();
                    // Endpoints de negocios (requieren autenticación)
                    req.requestMatchers(HttpMethod.POST, "/negocios/**").authenticated();
                    req.requestMatchers(HttpMethod.PUT, "/negocios/**").authenticated();
                    req.requestMatchers(HttpMethod.DELETE, "/negocios/**").authenticated();

                            // Endpoints de productos (requieren autenticación)
                    req.requestMatchers(HttpMethod.POST, "/productos").authenticated();
                    req.requestMatchers(HttpMethod.PUT, "/productos/**").authenticated();
                    req.requestMatchers(HttpMethod.PATCH, "/productos/**").authenticated();
                    req.requestMatchers(HttpMethod.DELETE, "/productos/**").authenticated();
                    req.requestMatchers(HttpMethod.POST, "/cart").authenticated();
                    req.requestMatchers(HttpMethod.GET, "/cart/**").authenticated();
                    req.requestMatchers(HttpMethod.PATCH, "/cart/**").authenticated();
                    req.requestMatchers(HttpMethod.GET, "/categorias/**").permitAll();

                    // Endpoints de categorías (solo admin)
                    req.requestMatchers(HttpMethod.POST, "/categorias").authenticated();
                    req.requestMatchers(HttpMethod.PUT, "/categorias/**").authenticated();
                    req.requestMatchers(HttpMethod.DELETE, "/categorias/**").authenticated();
//                    req.requestMatchers("/usuario/**").hasRole("usuario");//de esta forma se debe registrar las urls segun el rol
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
