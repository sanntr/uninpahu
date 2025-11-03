package com.uninpahu.uninpahu.application.usuario.service;

import com.uninpahu.uninpahu.domain.usuario.repository.RepositoryUsuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticationrDetailsService implements UserDetailsService {

    final RepositoryUsuario repositoryUsuario;

    public AutenticationrDetailsService(RepositoryUsuario repositoryUsuario) {
        this.repositoryUsuario = repositoryUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositoryUsuario.findByNombreUsuario(username).get();
    }

}
