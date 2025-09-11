package com.now.app.be.main.infrastructure.persitence.convert.mapper;

import com.now.app.be.main.domain.model.ImageModel;
import com.now.app.be.main.infrastructure.persitence.entity.ImageEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-11T14:13:48+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
public class ImageConvertMapperImpl implements ImageConvertMapper {

    @Override
    public ImageEntity convertModelToEntity(ImageModel imageModel) {
        if ( imageModel == null ) {
            return null;
        }

        ImageEntity.ImageEntityBuilder imageEntity = ImageEntity.builder();

        imageEntity.createdAt( imageModel.getCreatedAt() );
        imageEntity.id( imageModel.getId() );
        imageEntity.originalFile( imageModel.getOriginalFile() );
        imageEntity.resolution( imageModel.getResolution() );
        imageEntity.urlImage( imageModel.getUrlImage() );

        return imageEntity.build();
    }

    @Override
    public ImageModel convertEntityToModel(ImageEntity imageEntity) {
        if ( imageEntity == null ) {
            return null;
        }

        ImageModel.ImageModelBuilder imageModel = ImageModel.builder();

        imageModel.createdAt( imageEntity.getCreatedAt() );
        imageModel.id( imageEntity.getId() );
        imageModel.originalFile( imageEntity.getOriginalFile() );
        imageModel.resolution( imageEntity.getResolution() );
        imageModel.urlImage( imageEntity.getUrlImage() );

        return imageModel.build();
    }

    @Override
    public List<ImageModel> convertListEntityToDto(List<ImageEntity> listImgEntity) {
        if ( listImgEntity == null ) {
            return null;
        }

        List<ImageModel> list = new ArrayList<ImageModel>( listImgEntity.size() );
        for ( ImageEntity imageEntity : listImgEntity ) {
            list.add( convertEntityToModel( imageEntity ) );
        }

        return list;
    }
}
