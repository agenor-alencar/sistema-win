package com.win.win_market.dto.mapper;

import com.win.win_market.dto.response.ImagemProdutoResponseDTO;
import com.win.win_market.model.ImagemProduto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ImagemProdutoMapper {

    ImagemProdutoResponseDTO toResponseDTO(ImagemProduto imagemProduto);
}
