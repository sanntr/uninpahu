package com.uninpahu.uninpahu.infraestructure.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorManager {

    // Se ejecuta cuando no se encuentra una entidad en la BD
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionError404 (){
        return ResponseEntity.notFound().build(); // Devuelve HTTP 404 Not Found
    }

    // Se ejecuta cuando un @Valid falla en un DTO (validaciones de entrada)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionerror400(MethodArgumentNotValidException exception){
        var errores= exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(datosError::new).toList());
    }

    // Se ejecuta cuando se lanza una ValidacionException (custom en tu app)
    @ExceptionHandler
    public  ResponseEntity tratarErrorValidacion (ValidacionException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // Se ejecuta cuando el JSON enviado no se puede parsear
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity gestionarError400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Se ejecuta cuando el login falla por credenciales incorrectas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity gestionarErrorBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas"); // HTTP 401
    }

    // Se ejecuta cuando hay un problema de autenticación genérico
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity gestionarErrorAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falla en la autenticación"); // HTTP 401
    }

    // Se ejecuta cuando un usuario autenticado intenta acceder a un recurso prohibido
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity gestionarErrorAccesoDenegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado"); // HTTP 403
    }

    // Captura cualquier excepción no controlada previamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity gestionarError500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " +ex.getLocalizedMessage()); // HTTP 500
    }

    // Record interno para representar errores de validación
    public record datosError(String campo, String error){
        public datosError(FieldError fieldError){
            this(fieldError.getField(),fieldError.getDefaultMessage());
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> gestionarErrorDataIntegrity(DataIntegrityViolationException ex) {
        String mensaje = "Error de integridad de datos";

        if (ex.getCause() instanceof ConstraintViolationException constraintEx) {
            mensaje = "Ya existe un registro con ese valor único: " + constraintEx.getConstraintName();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(mensaje);
    }
}
