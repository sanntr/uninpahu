package com.uninpahu.uninpahu.application.usuario.service;

import com.uninpahu.uninpahu.application.usuario.dto.DataLogin;
import com.uninpahu.uninpahu.domain.usuario.model.Usuario;
import com.uninpahu.uninpahu.infraestructure.security.token.DatosToken;
import com.uninpahu.uninpahu.infraestructure.security.token.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticationService {
    final AuthenticationManager authenticationManager;
    final TokenService tokenService;

    public AutenticationService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public DatosToken ValidationUser(DataLogin login){
        var token= new UsernamePasswordAuthenticationToken(login.nombreUsuario(),login.password());
        var autenticacion = authenticationManager.authenticate(token);
        return new DatosToken(tokenService.crearToken((Usuario) autenticacion.getPrincipal()));
    }



}
