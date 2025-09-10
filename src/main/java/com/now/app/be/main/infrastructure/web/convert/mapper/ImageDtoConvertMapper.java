package com.now.app.be.main.infrastructure.web.convert.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageDtoConvertMapper {

  public ImageDtoConvertMapper mapper = Mappers.getMapper(ImageDtoConvertMapper.class);
  
}
