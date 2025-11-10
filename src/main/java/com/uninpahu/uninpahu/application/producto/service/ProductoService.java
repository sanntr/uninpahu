package com.uninpahu.uninpahu.application.producto.service;

import com.uninpahu.uninpahu.application.producto.dto.CrearProductoDTO;
import com.uninpahu.uninpahu.application.producto.dto.ActualizarProductoDTO;
import com.uninpahu.uninpahu.application.producto.dto.ActualizarStockDTO;
import com.uninpahu.uninpahu.application.producto.dto.ProductoListaDTO;
import com.uninpahu.uninpahu.application.producto.dto.ProductoResponseDTO;
import com.uninpahu.uninpahu.domain.categoria.model.Categoria;
import com.uninpahu.uninpahu.domain.categoria.repository.CategoriaRepository;
import com.uninpahu.uninpahu.domain.negocio.model.Negocio;
import com.uninpahu.uninpahu.domain.negocio.repository.NegocioRepository;
import com.uninpahu.uninpahu.domain.producto.model.Producto;
import com.uninpahu.uninpahu.domain.producto.model.ProductImage;
import org.springframework.web.multipart.MultipartFile;
import com.uninpahu.uninpahu.domain.producto.repository.ProductoRepository;
import com.uninpahu.uninpahu.domain.usuario.model.Usuario;
import com.uninpahu.uninpahu.infraestructure.exception.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    final ProductoRepository productoRepository;
    final NegocioRepository negocioRepository;
    final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, NegocioRepository negocioRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.negocioRepository = negocioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public ProductoResponseDTO crearProducto(CrearProductoDTO dto) {
        // Verificar que el negocio existe
        Negocio negocio = negocioRepository.findById(dto.idNegocio())
                .orElseThrow(() -> new ValidacionException("Negocio no encontrado con ID: " + dto.idNegocio()));

        // Verificar que el usuario autenticado es el dueño del negocio
        Usuario usuarioActual = obtenerUsuarioAutenticado();

        if (!negocio.getIdUsuario().equals(usuarioActual.getId()) && !esAdmin(usuarioActual)) {
            throw new ValidacionException("No tienes permiso para crear productos en este negocio. Solo el propietario del negocio o un administrador pueden hacerlo");
        }

        Producto producto = new Producto(
                dto.nombre(),
                dto.descripcion(),
                dto.idNegocio(),
                dto.descuento(),
                dto.precio(),
                dto.stock(),
                null,
                true
        );

        // Si vienen imágenes, agregarlas al producto (persistidas en cascada)
        if (dto.imagenes() != null && !dto.imagenes().isEmpty()) {
            for (MultipartFile mf : dto.imagenes()) {
                try {
                    if (mf != null && !mf.isEmpty()) {
                        ProductImage img = new ProductImage(mf.getBytes());
                        producto.addImagen(img);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error leyendo imagen: " + e.getMessage(), e);
                }
            }
        }

        producto = productoRepository.save(producto);

        // Asignar categorías si se proporcionaron
        if (dto.categoriasIds() != null && !dto.categoriasIds().isEmpty()) {
            List<Categoria> categorias = categoriaRepository.findAllById(dto.categoriasIds());
            producto.getCategorias().addAll(categorias);
            producto = productoRepository.save(producto);
        }

    return mapearAResponseDTO(producto);
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Producto no encontrado con ID: " + id));
        return mapearAResponseDTO(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductoListaDTO> listarTodosLosProductos() {
        return productoRepository.findByActivoTrue().stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoListaDTO> listarProductosPorNegocio(Long idNegocio) {
        return productoRepository.findByIdNegocioAndActivoTrue(idNegocio).stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoListaDTO> buscarProductosPorNombre(String nombre) {
        return productoRepository.buscarPorNombre(nombre).stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoListaDTO> obtenerProductosConStock() {
        return productoRepository.findProductosConStock().stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoListaDTO> obtenerProductosConDescuento() {
        return productoRepository.findProductosConDescuento().stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoListaDTO> obtenerMejoresProductos() {
        return productoRepository.findTopProductos().stream()
                .limit(20) // Top 20
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoListaDTO> buscarPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax).stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoListaDTO> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoria(categoriaId).stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductoResponseDTO actualizarProducto(Long id, ActualizarProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Producto no encontrado con ID: " + id));

        // Verificar permisos: el usuario debe ser dueño del negocio o admin
        verificarPermisosProducto(producto);

        producto.actualizarDatos(dto.nombre(), dto.descripcion(), dto.precio(), dto.stock());

        if (dto.descuento() != null) {
            producto.aplicarDescuento(dto.descuento());
        }

        // Actualizar categorías si se proporcionaron
        if (dto.categoriasIds() != null) {
            producto.getCategorias().clear();
            List<Categoria> categorias = categoriaRepository.findAllById(dto.categoriasIds());
            producto.getCategorias().addAll(categorias);
        }

        producto = productoRepository.save(producto);
        return mapearAResponseDTO(producto);
    }

    @Transactional
    public ProductoResponseDTO actualizarStock(Long id, ActualizarStockDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Producto no encontrado con ID: " + id));

        // Verificar permisos
        verificarPermisosProducto(producto);

        producto.actualizarStock(dto.stock());
        producto = productoRepository.save(producto);

        return mapearAResponseDTO(producto);
    }

    @Transactional
    public void activarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Producto no encontrado con ID: " + id));

        // Verificar permisos
        verificarPermisosProducto(producto);

        producto.activar();
        productoRepository.save(producto);
    }

    @Transactional
    public void desactivarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Producto no encontrado con ID: " + id));

        // Verificar permisos
        verificarPermisosProducto(producto);

        producto.desactivar();
        productoRepository.save(producto);
    }

    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Producto no encontrado con ID: " + id));

        // Verificar permisos
        verificarPermisosProducto(producto);

        productoRepository.deleteById(id);
    }

    // Métodos auxiliares de seguridad
    private void verificarPermisosProducto(Producto producto) {
        Usuario usuarioActual = obtenerUsuarioAutenticado();

        Negocio negocio = negocioRepository.findById(producto.getIdNegocio())
                .orElseThrow(() -> new ValidacionException("Negocio no encontrado"));

        if (!negocio.getIdUsuario().equals(usuarioActual.getId()) && !esAdmin(usuarioActual)) {
            throw new ValidacionException("No tienes permiso para modificar este producto. Solo el propietario del negocio o un administrador pueden hacerlo");
        }
    }

    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new ValidacionException("Debes iniciar sesión para realizar esta acción");
        }
        return (Usuario) authentication.getPrincipal();
    }

    private boolean esAdmin(Usuario usuario) {
        return usuario.tieneRol("ADMIN");
    }

    // Métodos auxiliares de mapeo
    private ProductoResponseDTO mapearAResponseDTO(Producto producto) {
        String nombreNegocio = producto.getNegocio() != null ?
                producto.getNegocio().getNombre() : null;

        List<String> categorias = producto.getCategorias().stream()
                .map(Categoria::getTipo)
                .collect(Collectors.toList());

    List<byte[]> imagenes = producto.getImagenes().stream()
        .map(pi -> pi.getImagen())
        .collect(Collectors.toList());

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getIdNegocio(),
                nombreNegocio,
                producto.getDescuento(),
                producto.getPrecio(),
                producto.getPrecioConDescuento(),
                producto.getStock(),
                producto.getCalificacion(),
                producto.getActivo(),
        categorias,
        imagenes
        );
    }

    private ProductoListaDTO mapearAListaDTO(Producto producto) {
        return new ProductoListaDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getPrecioConDescuento(),
                producto.getStock(),
                producto.getCalificacion(),
                producto.getActivo()
        );
    }
}