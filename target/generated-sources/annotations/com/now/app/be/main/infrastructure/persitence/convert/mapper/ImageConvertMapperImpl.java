package com.now.app.be.main.infrastructure.persitence.convert.mapper;

import com.now.app.be.main.domain.model.ImageModel;
import com.now.app.be.main.infrastructure.persitence.entity.ImageEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-11T09:18:28+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
public class ImageConvertMapperImpl implements ImageConvertMapper {

    @Override
    public ImageModel convertEntityToModel(ImageEntity imageEntity) {
        if ( imageEntity == null ) {
            return null;
        }

        ImageModel imageModel = new ImageModel();

        return imageModel;
    }
}
