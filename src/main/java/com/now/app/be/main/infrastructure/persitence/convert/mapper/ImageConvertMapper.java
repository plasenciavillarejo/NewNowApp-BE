package com.now.app.be.main.infrastructure.persitence.convert.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.now.app.be.main.domain.model.Image;
import com.now.app.be.main.infrastructure.persitence.entity.ImageEntity;

@Mapper
public interface ImageConvertMapper {

  public ImageConvertMapper mapper = Mappers.getMapper(ImageConvertMapper.class);
  
  public Image convertEntityToModel(ImageEntity imageEntity);
  
}
