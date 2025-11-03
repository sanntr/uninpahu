package com.uninpahu.uninpahu.controller.negocio;

import com.uninpahu.uninpahu.application.negocio.dto.ActualizarNegocioDTO;
import com.uninpahu.uninpahu.application.negocio.dto.CrearNegocioDTO;
import com.uninpahu.uninpahu.application.negocio.dto.NegocioListaDTO;
import com.uninpahu.uninpahu.application.negocio.dto.NegocioResponseDTO;
import com.uninpahu.uninpahu.application.negocio.service.NegocioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/negocios")
@CrossOrigin(origins = "*")
public class NegocioController {

    @Autowired
    private NegocioService negocioService;

    @PostMapping
    public ResponseEntity<NegocioResponseDTO> crearNegocio(@Valid @RequestBody CrearNegocioDTO dto) {
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

    @PutMapping("/{id}")
    public ResponseEntity<NegocioResponseDTO> actualizarNegocio(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarNegocioDTO dto) {
        NegocioResponseDTO negocio = negocioService.actualizarNegocio(id, dto);
        return ResponseEntity.ok(negocio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNegocio(@PathVariable Long id) {
        negocioService.eliminarNegocio(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarExcepciones(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
