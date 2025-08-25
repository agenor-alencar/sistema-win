package com.win.win_market.dto.mapper;

import com.win.win_market.dto.response.MotoristaResponseDTO;
import com.win.win_market.model.Motorista;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MotoristaMapper {

    @Mapping(target = "avaliacaoMedia", expression = "java(calcularAvaliacaoMedia(motorista))")
    @Mapping(target = "totalAvaliacoes", expression = "java(calcularTotalAvaliacoes(motorista))")
    MotoristaResponseDTO toResponseDTO(Motorista motorista);

    default Double calcularAvaliacaoMedia(Motorista motorista) {
        // Implementação será feita no service
        return 0.0;
    }

    default Integer calcularTotalAvaliacoes(Motorista motorista) {
        // Implementação será feita no service
        return 0;
    }
}
