package com.uninpahu.uninpahu.controller.usuario;

import com.uninpahu.uninpahu.application.usuario.dto.CrearUsuario;
import com.uninpahu.uninpahu.application.usuario.dto.DataLogin;
import com.uninpahu.uninpahu.application.usuario.dto.UsuarioResponse;
import com.uninpahu.uninpahu.application.usuario.service.AutenticationService;
import com.uninpahu.uninpahu.application.usuario.service.UsuarioCreationService;
import com.uninpahu.uninpahu.application.usuario.service.UsuarioService;
import com.uninpahu.uninpahu.infraestructure.security.token.DatosToken;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    final UsuarioCreationService usuarioCreationService;
    final UsuarioService usuarioService;
    final AutenticationService autenticationService;
    public UsuarioController(UsuarioCreationService usuarioCreationService, UsuarioService usuarioService, AutenticationService autenticationService) {
        this.usuarioCreationService = usuarioCreationService;
        this.usuarioService = usuarioService;
        this.autenticationService=autenticationService;
    }

    @PostMapping("/crear")
    public ResponseEntity CreateUser(@RequestBody @Valid CrearUsuario crearUsuario, UriComponentsBuilder uriBuilder){
        UsuarioResponse usuario=usuarioCreationService.create(crearUsuario);
        var uri = uriBuilder.path("/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/{id}")
    public ResponseEntity buscarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscar(id));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody DataLogin dataLogin){
        DatosToken jwt=autenticationService.ValidationUser(dataLogin);
        return  ResponseEntity.ok(jwt);
    }
}
