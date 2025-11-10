package com.uninpahu.uninpahu.controller.producto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import com.uninpahu.uninpahu.domain.producto.model.ProductImage;
import com.uninpahu.uninpahu.domain.producto.repository.ProductImageRepository;
import com.uninpahu.uninpahu.domain.producto.repository.ProductoRepository;
import com.uninpahu.uninpahu.infraestructure.exception.ValidacionException;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
@Tag(name = "Producto Imágenes", description = "Operaciones para listar y descargar imágenes de productos")
public class ProductImageController {

    private final ProductImageRepository productImageRepository;
    private final ProductoRepository productoRepository;

    public ProductImageController(ProductImageRepository productImageRepository, ProductoRepository productoRepository) {
        this.productImageRepository = productImageRepository;
        this.productoRepository = productoRepository;
    }

    // Lista imágenes de un producto o devuelve la imagen directamente si sólo hay una
    @Operation(summary = "Listar imágenes de un producto",
            description = "Si el producto tiene una sola imagen devuelve el binario; si tiene varias devuelve metadatos y URLs para descargarlas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de metadatos o imagen binaria",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sin contenido (producto sin imágenes)")
            })
    @GetMapping("/{productoId}/imagenes")
    public ResponseEntity<?> listarImagenesDeProducto(@PathVariable Long productoId) {
        // verificar que exista el producto
        productoRepository.findById(productoId)
                .orElseThrow(() -> new ValidacionException("Producto no encontrado con ID: " + productoId));

        List<ProductImage> imagenes = productImageRepository.findByProductoId(productoId);

        if (imagenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Si sólo hay una imagen, devolver el contenido binario directamente
        if (imagenes.size() == 1) {
            ProductImage pi = imagenes.get(0);
            byte[] data = pi.getImagen();
            if (data == null || data.length == 0) return ResponseEntity.noContent().build();

            String contentType = detectarContentType(data);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(data.length);
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        }

        // Si hay varias imágenes, devolver metadatos + URL para cada una
        List<Map<String, Object>> resp = imagenes.stream().map(pi -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", pi.getId());
            m.put("size", pi.getImagen() != null ? pi.getImagen().length : 0);
            m.put("url", "/productos/imagenes/" + pi.getId());
            return m;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(resp);
    }

    // Descargar una imagen por id
    @Operation(summary = "Descargar imagen por id",
            description = "Devuelve el contenido binario de la imagen especificada",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Imagen (binaria)",
                            content = @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "204", description = "Sin contenido")
            })
    @GetMapping("/imagenes/{imagenId}")
    public ResponseEntity<byte[]> descargarImagen(@PathVariable Long imagenId) {
        ProductImage pi = productImageRepository.findById(imagenId)
                .orElseThrow(() -> new ValidacionException("Imagen no encontrada con ID: " + imagenId));

        byte[] data = pi.getImagen();
        if (data == null || data.length == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new byte[0]);
        }

        String contentType = detectarContentType(data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(data.length);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    // Simple mime type detection by magic numbers for common image types
    private String detectarContentType(byte[] bytes) {
        if (bytes.length >= 4) {
            // PNG: 89 50 4E 47
            if ((bytes[0] & 0xFF) == 0x89 && bytes[1] == 0x50 && bytes[2] == 0x4E && bytes[3] == 0x47) {
                return "image/png";
            }
            // JPG: FF D8 FF
            if ((bytes[0] & 0xFF) == 0xFF && (bytes[1] & 0xFF) == 0xD8 && (bytes[2] & 0xFF) == 0xFF) {
                return "image/jpeg";
            }
            // GIF: 47 49 46 38
            if (bytes[0] == 0x47 && bytes[1] == 0x49 && bytes[2] == 0x46 && bytes[3] == 0x38) {
                return "image/gif";
            }
        }
        return "application/octet-stream";
    }
}
