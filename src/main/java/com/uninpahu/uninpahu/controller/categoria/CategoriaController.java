package com.uninpahu.uninpahu.controller.categoria;

import com.uninpahu.uninpahu.application.categoria.dto.ActualizarCategoriaDTO;
import com.uninpahu.uninpahu.application.categoria.dto.CategoriaListaDTO;
import com.uninpahu.uninpahu.application.categoria.dto.CategoriaResponseDTO;
import com.uninpahu.uninpahu.application.categoria.dto.CrearCategoriaDTO;
import com.uninpahu.uninpahu.application.categoria.service.CategoriaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {


    final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Crear una nueva categoría (Solo ADMIN)
     * POST /api/categorias
     */
    @PostMapping
//    @PreAuthorize("hasRole('admin')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(@Valid @RequestBody CrearCategoriaDTO dto) {
        CategoriaResponseDTO categoria = categoriaService.crearCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    /**
     * Obtener una categoría por ID (Público)
     * GET /api/categorias/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable Long id) {
        CategoriaResponseDTO categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    /**
     * Listar todas las categorías (Público)
     * GET /api/categorias
     */
    @GetMapping
    public ResponseEntity<List<CategoriaListaDTO>> listarCategorias() {
        List<CategoriaListaDTO> categorias = categoriaService.listarTodasLasCategorias();
        return ResponseEntity.ok(categorias);
    }

    /**
     * Buscar categoría por tipo (Público)
     * GET /api/categorias/tipo/{tipo}
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorTipo(@PathVariable String tipo) {
        CategoriaResponseDTO categoria = categoriaService.buscarPorTipo(tipo);
        return ResponseEntity.ok(categoria);
    }

    /**
     * Actualizar una categoría (Solo ADMIN)
     * PUT /api/categorias/{id}
     */
    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarCategoriaDTO dto) {
        CategoriaResponseDTO categoria = categoriaService.actualizarCategoria(id, dto);
        return ResponseEntity.ok(categoria);
    }

    /**
     * Eliminar una categoría (Solo ADMIN)
     * DELETE /api/categorias/{id}
     */
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Contar productos de una categoría (Público)
     * GET /api/categorias/{id}/productos/count
     */
    @GetMapping("/{id}/productos/count")
    public ResponseEntity<Integer> contarProductos(@PathVariable Long id) {
        Integer cantidad = categoriaService.contarProductosPorCategoria(id);
        return ResponseEntity.ok(cantidad);
    }

    /**
     * Manejo global de excepciones
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> manejarExcepciones(RuntimeException e) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Clase interna para respuestas de error
     */
    record ErrorResponse(int status, String mensaje, long timestamp) {}
}