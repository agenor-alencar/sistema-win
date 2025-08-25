package com.win.win_market.dto.mapper;

import com.win.win_market.dto.response.VendedorResponseDTO;
import com.win.win_market.model.Vendedor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VendedorMapper {

    @Mapping(target = "avaliacaoMedia", expression = "java(calcularAvaliacaoMedia(vendedor))")
    @Mapping(target = "totalAvaliacoes", expression = "java(calcularTotalAvaliacoes(vendedor))")
    VendedorResponseDTO toResponseDTO(Vendedor vendedor);

    default Double calcularAvaliacaoMedia(Vendedor vendedor) {
        // Implementação será feita no service
        return 0.0;
    }

    default Integer calcularTotalAvaliacoes(Vendedor vendedor) {
        // Implementação será feita no service
        return 0;
    }
}
