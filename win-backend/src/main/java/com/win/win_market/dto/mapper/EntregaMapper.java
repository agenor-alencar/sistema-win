package com.win.win_market.dto.mapper;

import com.win.win_market.dto.response.EntregaResponseDTO;
import com.win.win_market.model.Entrega;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MotoristaMapper.class})
public interface EntregaMapper {

    EntregaResponseDTO toResponseDTO(Entrega entrega);
}
