package com.uninpahu.uninpahu.application.negocio.service;

import com.uninpahu.uninpahu.application.negocio.dto.ActualizarNegocioDTO;
import com.uninpahu.uninpahu.application.negocio.dto.CrearNegocioDTO;
import com.uninpahu.uninpahu.application.negocio.dto.NegocioListaDTO;
import com.uninpahu.uninpahu.application.negocio.dto.NegocioResponseDTO;
import com.uninpahu.uninpahu.domain.negocio.model.Negocio;
import com.uninpahu.uninpahu.domain.negocio.repository.NegocioRepository;
import com.uninpahu.uninpahu.domain.producto.repository.ProductoRepository;
import com.uninpahu.uninpahu.domain.usuario.model.Usuario;
import com.uninpahu.uninpahu.infraestructure.exception.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NegocioService {


    final NegocioRepository negocioRepository;
    final ProductoRepository productoRepository;

    public NegocioService(NegocioRepository negocioRepository, ProductoRepository productoRepository) {
        this.negocioRepository = negocioRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public NegocioResponseDTO crearNegocio(CrearNegocioDTO dto) {
        // Verificar que el usuario autenticado es el dueño
        Usuario usuarioActual = obtenerUsuarioAutenticado();

        if (!usuarioActual.getId().equals( usuarioActual.getId())) {
            throw new ValidacionException("No puedes crear negocios para otros usuarios");
        }

        if (negocioRepository.existsByNombre(dto.nombre())) {
            throw new ValidacionException("Ya existe un negocio con ese nombre");
        }

        Negocio negocio = null;
        try {
            negocio = new Negocio(
                    dto.nombre(),
                    usuarioActual.getId(),
                    null,
                    dto.descripcion(),
                    dto.fechaInicio(),
                    dto.registradoCamara(),
                    dto.direccion(),
                    dto.paginaWeb(),
                    dto.ciudad(),
                    dto.imagen().getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        negocio = negocioRepository.save(negocio);
        return mapearAResponseDTO(negocio);
    }

    @Transactional(readOnly = true)
    public NegocioResponseDTO obtenerNegocioPorId(Long id) {
        Negocio negocio = negocioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Negocio no encontrado con ID: " + id));
        return mapearAResponseDTO(negocio);
    }

    @Transactional(readOnly = true)
    public List<NegocioListaDTO> listarTodosLosNegocios() {
        return negocioRepository.findAll().stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NegocioListaDTO> listarNegociosPorUsuario(Long idUsuario) {
        return negocioRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NegocioListaDTO> buscarNegociosPorNombre(String nombre) {
        return negocioRepository.buscarPorNombre(nombre).stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NegocioListaDTO> obtenerMejoresNegocios() {
        return negocioRepository.findTopNegocios().stream()
                .limit(10) // Top 10
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public NegocioResponseDTO actualizarNegocio(Long id, ActualizarNegocioDTO dto) {
        Negocio negocio = negocioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Negocio no encontrado con ID: " + id));

        // Verificar que el usuario autenticado es el dueño
        Usuario usuarioActual = obtenerUsuarioAutenticado();

        if (!negocio.getIdUsuario().equals(usuarioActual.getId()) && !esAdmin(usuarioActual)) {
            throw new ValidacionException("No tienes permiso para actualizar este negocio. Solo el propietario o un administrador pueden hacerlo");
        }

        // Actualizar campos básicos y extendidos si vienen en el DTO
        try {
            negocio.actualizarDatosExtendido(
                    dto.nombre(),
                    dto.descripcion(),
                    dto.fechaInicio(),
                    dto.registradoCamara(),
                    dto.direccion(),
                    dto.paginaWeb(),
                    dto.ciudad(),
                    dto.imagen().getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        negocio = negocioRepository.save(negocio);

        return mapearAResponseDTO(negocio);
    }

    @Transactional
    public void eliminarNegocio(Long id) {
        Negocio negocio = negocioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Negocio no encontrado con ID: " + id));

        // Verificar que el usuario autenticado es el dueño
        Usuario usuarioActual = obtenerUsuarioAutenticado();

        if (!negocio.getIdUsuario().equals(usuarioActual.getId()) && !esAdmin(usuarioActual)) {
            throw new ValidacionException("No tienes permiso para eliminar este negocio. Solo el propietario o un administrador pueden hacerlo");
        }

        negocioRepository.deleteById(id);
    }

    // Métodos auxiliares de seguridad
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
    private NegocioResponseDTO mapearAResponseDTO(Negocio negocio) {
        int cantidadProductos = (int) productoRepository.countByIdNegocioAndActivoTrue(negocio.getId());

        return new NegocioResponseDTO(
                negocio.getId(),
                negocio.getNombre(),
                negocio.getIdUsuario(),
                negocio.getCalificacion(),
                negocio.getDescripcion(),
                negocio.getFechaInicio(),
                negocio.getRegistradoCamara(),
                negocio.getDireccion(),
                negocio.getPaginaWeb(),
                negocio.getCiudad(),
                negocio.getImagen(),
                cantidadProductos
        );
    }

    private NegocioListaDTO mapearAListaDTO(Negocio negocio) {
        int cantidadProductos = (int) productoRepository.countByIdNegocioAndActivoTrue(negocio.getId());

        return new NegocioListaDTO(
                negocio.getId(),
                negocio.getNombre(),
                negocio.getCalificacion(),
                cantidadProductos
        );
    }
}