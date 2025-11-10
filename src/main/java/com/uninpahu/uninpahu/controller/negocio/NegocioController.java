package com.uninpahu.uninpahu.controller.negocio;

import com.uninpahu.uninpahu.application.negocio.dto.ActualizarNegocioDTO;
import com.uninpahu.uninpahu.application.negocio.dto.CrearNegocioDTO;
import com.uninpahu.uninpahu.application.negocio.dto.NegocioListaDTO;
import com.uninpahu.uninpahu.application.negocio.dto.NegocioResponseDTO;
import com.uninpahu.uninpahu.application.negocio.service.NegocioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/negocios")
@CrossOrigin(origins = "*")
public class NegocioController {

    final NegocioService negocioService;

    public NegocioController(NegocioService negocioService) {
        this.negocioService = negocioService;
    }

    @SecurityRequirement(name = "bearer-key")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<NegocioResponseDTO> crearNegocio(
            @Valid @ModelAttribute CrearNegocioDTO dto) {
        System.out.println("Tipo imagen: " + dto.imagen().getClass());
        NegocioResponseDTO negocio = negocioService.crearNegocio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(negocio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NegocioResponseDTO> obtenerNegocio(@PathVariable Long id) {
        NegocioResponseDTO negocio = negocioService.obtenerNegocioPorId(id);
        return ResponseEntity.ok(negocio);
    }

    @GetMapping
    public ResponseEntity<List<NegocioListaDTO>> listarNegocios() {
        List<NegocioListaDTO> negocios = negocioService.listarTodosLosNegocios();
        return ResponseEntity.ok(negocios);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NegocioListaDTO>> listarNegociosPorUsuario(@PathVariable Long idUsuario) {
        List<NegocioListaDTO> negocios = negocioService.listarNegociosPorUsuario(idUsuario);
        return ResponseEntity.ok(negocios);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<NegocioListaDTO>> buscarNegocios(@RequestParam String nombre) {
        List<NegocioListaDTO> negocios = negocioService.buscarNegociosPorNombre(nombre);
        return ResponseEntity.ok(negocios);
    }

    @GetMapping("/mejores")
    public ResponseEntity<List<NegocioListaDTO>> obtenerMejoresNegocios() {
        List<NegocioListaDTO> negocios = negocioService.obtenerMejoresNegocios();
        return ResponseEntity.ok(negocios);
    }
    @SecurityRequirement(name = "bearer-key")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<NegocioResponseDTO> actualizarNegocio(
            @PathVariable Long id,
            @Valid @ModelAttribute ActualizarNegocioDTO dto) {

        NegocioResponseDTO negocio = negocioService.actualizarNegocio(id, dto);
        return ResponseEntity.ok(negocio);
    }
    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNegocio(@PathVariable Long id) {
        negocioService.eliminarNegocio(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
        try {
            byte[] imagen = negocioService.obtenerImagen(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG) // o MediaType.IMAGE_JPEG
                    .body(imagen);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarExcepciones(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


}
