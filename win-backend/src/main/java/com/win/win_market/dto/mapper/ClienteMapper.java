package com.win.win_market.dto.mapper;

import com.win.win_market.dto.response.ClienteResponseDTO;
import com.win.win_market.model.Cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponseDTO toResponseDTO(Cliente cliente);
}
