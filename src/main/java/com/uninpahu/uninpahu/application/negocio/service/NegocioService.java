package com.uninpahu.uninpahu.application.negocio.service;

import com.uninpahu.uninpahu.application.negocio.dto.ActualizarNegocioDTO;
import com.uninpahu.uninpahu.application.negocio.dto.CrearNegocioDTO;
import com.uninpahu.uninpahu.application.negocio.dto.NegocioListaDTO;
import com.uninpahu.uninpahu.application.negocio.dto.NegocioResponseDTO;
import com.uninpahu.uninpahu.domain.negocio.model.Negocio;
import com.uninpahu.uninpahu.domain.negocio.repository.NegocioRepository;
import com.uninpahu.uninpahu.domain.producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NegocioService {

    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public NegocioResponseDTO crearNegocio(CrearNegocioDTO dto) {
        // Verificar si ya existe un negocio con ese nombre
        if (negocioRepository.existsByNombre(dto.nombre())) {
            throw new RuntimeException("Ya existe un negocio con ese nombre");
        }

        Negocio negocio = new Negocio(
                dto.nombre(),
                dto.idUsuario(),
                null, // calificación inicial es null
                dto.descripcion()
        );

        negocio = negocioRepository.save(negocio);
        return mapearAResponseDTO(negocio);
    }

    @Transactional(readOnly = true)
    public NegocioResponseDTO obtenerNegocioPorId(Long id) {
        Negocio negocio = negocioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado"));
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
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado"));

        negocio.actualizarDatos(dto.nombre(), dto.descripcion());
        negocio = negocioRepository.save(negocio);

        return mapearAResponseDTO(negocio);
    }

    @Transactional
    public void eliminarNegocio(Long id) {
        if (!negocioRepository.existsById(id)) {
            throw new RuntimeException("Negocio no encontrado");
        }
        negocioRepository.deleteById(id);
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
