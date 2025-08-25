package com.win.win_market.dto.mapper;

import com.win.win_market.dto.response.PagamentoResponseDTO;
import com.win.win_market.model.Pagamento;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {

    PagamentoResponseDTO toResponseDTO(Pagamento pagamento);
}
