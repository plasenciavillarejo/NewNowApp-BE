package com.now.app.be.main.infrastructure.web.convert.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.now.app.be.main.domain.model.ImageModel;
import com.now.app.be.main.infrastructure.web.response.dto.ImageResponseDto;

@Mapper
public interface ImageDtoConvertMapper {

  public ImageDtoConvertMapper mapper = Mappers.getMapper(ImageDtoConvertMapper.class);

  public ImageResponseDto convertImageModelToResponseDto(ImageModel imageModel);
  
  public List<ImageResponseDto> convertListImageModelToReponseDto(List<ImageModel> imageModel);
  
}
