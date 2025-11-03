package com.uninpahu.uninpahu.infraestructure.security;

import com.uninpahu.uninpahu.domain.usuario.repository.RepositoryUsuario;
import com.uninpahu.uninpahu.infraestructure.security.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.uninpahu.uninpahu.domain.usuario.repository.RepositoryUsuario;
import com.uninpahu.uninpahu.infraestructure.security.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final RepositoryUsuario repositoryUsuario;
    private final TokenService tokenService;

    public SecurityFilter(RepositoryUsuario repositoryUsuario, TokenService tokenService) {
        this.repositoryUsuario = repositoryUsuario;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = recuperarToken(request);

        if (token != null ) {
            String nombreUsuario = tokenService.getSubject(token);
            var usuarioOpt = repositoryUsuario.findByNombreUsuario(nombreUsuario);

            if (usuarioOpt.isPresent()) {
                var usuario = usuarioOpt.get();
                var authentication =
                        new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.replace("Bearer ", "").trim();
        }
        return null;
    }
}
