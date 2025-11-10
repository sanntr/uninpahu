package com.uninpahu.uninpahu.controller.producto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uninpahu.uninpahu.application.producto.dto.ActualizarProductoDTO;
import com.uninpahu.uninpahu.application.producto.dto.ActualizarStockDTO;
import com.uninpahu.uninpahu.application.producto.dto.CrearProductoDTO;
import com.uninpahu.uninpahu.application.producto.dto.ProductoListaDTO;
import com.uninpahu.uninpahu.application.producto.dto.ProductoResponseDTO;
import com.uninpahu.uninpahu.application.producto.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {


    final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @SecurityRequirement(name = "bearer-key")
    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "Crear producto",
            description = "Crea un producto. Para subir imágenes envía campos form-data: 'imagenes' (varios archivos).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = CrearProductoDTO.class))))
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @ModelAttribute CrearProductoDTO dto) {
        ProductoResponseDTO producto = productoService.crearProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProducto(@PathVariable Long id) {
        ProductoResponseDTO producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping
    public ResponseEntity<List<ProductoListaDTO>> listarProductos() {
        List<ProductoListaDTO> productos = productoService.listarTodosLosProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/negocio/{idNegocio}")
    public ResponseEntity<List<ProductoListaDTO>> listarProductosPorNegocio(@PathVariable Long idNegocio) {
        List<ProductoListaDTO> productos = productoService.listarProductosPorNegocio(idNegocio);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoListaDTO>> buscarProductos(@RequestParam String nombre) {
        List<ProductoListaDTO> productos = productoService.buscarProductosPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/con-stock")
    public ResponseEntity<List<ProductoListaDTO>> obtenerProductosConStock() {
        List<ProductoListaDTO> productos = productoService.obtenerProductosConStock();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/con-descuento")
    public ResponseEntity<List<ProductoListaDTO>> obtenerProductosConDescuento() {
        List<ProductoListaDTO> productos = productoService.obtenerProductosConDescuento();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/mejores")
    public ResponseEntity<List<ProductoListaDTO>> obtenerMejoresProductos() {
        List<ProductoListaDTO> productos = productoService.obtenerMejoresProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/rango-precio")
    public ResponseEntity<List<ProductoListaDTO>> buscarPorRangoPrecio(
            @RequestParam BigDecimal precioMin,
            @RequestParam BigDecimal precioMax) {
        List<ProductoListaDTO> productos = productoService.buscarPorRangoPrecio(precioMin, precioMax);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoListaDTO>> buscarPorCategoria(@PathVariable Long categoriaId) {
        List<ProductoListaDTO> productos = productoService.buscarPorCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }
    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarProductoDTO dto) {
        ProductoResponseDTO producto = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(producto);
    }
    @SecurityRequirement(name = "bearer-key")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarStockDTO dto) {
        ProductoResponseDTO producto = productoService.actualizarStock(id, dto);
        return ResponseEntity.ok(producto);
    }
    @SecurityRequirement(name = "bearer-key")
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activarProducto(@PathVariable Long id) {
        productoService.activarProducto(id);
        return ResponseEntity.ok().build();
    }
    
    @SecurityRequirement(name = "bearer-key")
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarProducto(@PathVariable Long id) {
        productoService.desactivarProducto(id);
        return ResponseEntity.ok().build();
    }
    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarExcepciones(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
