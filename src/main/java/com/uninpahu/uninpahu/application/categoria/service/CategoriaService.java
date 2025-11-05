package com.uninpahu.uninpahu.application.categoria.service;

import com.uninpahu.uninpahu.application.categoria.dto.ActualizarCategoriaDTO;
import com.uninpahu.uninpahu.application.categoria.dto.CategoriaListaDTO;
import com.uninpahu.uninpahu.application.categoria.dto.CategoriaResponseDTO;
import com.uninpahu.uninpahu.application.categoria.dto.CrearCategoriaDTO;
import com.uninpahu.uninpahu.domain.categoria.model.Categoria;
import com.uninpahu.uninpahu.domain.categoria.repository.CategoriaRepository;
import com.uninpahu.uninpahu.infraestructure.exception.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaResponseDTO crearCategoria(CrearCategoriaDTO dto) {
        // Verificar si ya existe una categoría con ese tipo
        if (categoriaRepository.existsByTipo(dto.tipo())) {
            throw new ValidacionException("Ya existe una categoría con ese tipo: " + dto.tipo());
        }

        // Crear la categoría
        Categoria categoria = new Categoria(dto.descuento(), dto.tipo());
        categoria = categoriaRepository.save(categoria);

        return mapearAResponseDTO(categoria);
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Categoría no encontrada con ID: " + id));
        return mapearAResponseDTO(categoria);
    }

    @Transactional(readOnly = true)
    public List<CategoriaListaDTO> listarTodasLasCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::mapearAListaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorTipo(String tipo) {
        Categoria categoria = categoriaRepository.findByTipo(tipo)
                .orElseThrow(() -> new ValidacionException("Categoría no encontrada con tipo: " + tipo));
        return mapearAResponseDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO actualizarCategoria(Long id, ActualizarCategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Categoría no encontrada con ID: " + id));

        // Actualizar solo los campos que no son nulos
        if (dto.tipo() != null && !dto.tipo().equals(categoria.getTipo())) {
            // Verificar que el nuevo tipo no exista
            if (categoriaRepository.existsByTipo(dto.tipo())) {
                throw new ValidacionException("Ya existe una categoría con ese tipo: " + dto.tipo());
            }
            categoria.setTipo(dto.tipo());
        }

        if (dto.descuento() != null) {
            categoria.setDescuento(dto.descuento());
        }

        categoria = categoriaRepository.save(categoria);
        return mapearAResponseDTO(categoria);
    }

    @Transactional
    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Categoría no encontrada con ID: " + id));

        // Verificar si tiene productos asociados
        if (categoria.getProductos() != null && !categoria.getProductos().isEmpty()) {
            throw new ValidacionException("No se puede eliminar la categoría porque tiene " +
                    categoria.getProductos().size() + " productos asociados");
        }

        categoriaRepository.deleteById(id);
    }

    // Método auxiliar para contar productos por categoría
    @Transactional(readOnly = true)
    public Integer contarProductosPorCategoria(Long idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ValidacionException("Categoría no encontrada"));
        return categoria.getProductos() != null ? categoria.getProductos().size() : 0;
    }

    // Métodos auxiliares de mapeo
    private CategoriaResponseDTO mapearAResponseDTO(Categoria categoria) {
        int cantidadProductos = categoria.getProductos() != null ?
                categoria.getProductos().size() : 0;

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getTipo(),
                categoria.getDescuento(),
                cantidadProductos
        );
    }

    private CategoriaListaDTO mapearAListaDTO(Categoria categoria) {
        return new CategoriaListaDTO(
                categoria.getId(),
                categoria.getTipo(),
                categoria.getDescuento()
        );
    }
}
