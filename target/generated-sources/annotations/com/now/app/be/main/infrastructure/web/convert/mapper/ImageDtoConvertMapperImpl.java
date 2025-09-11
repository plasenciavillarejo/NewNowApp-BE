package com.now.app.be.main.infrastructure.web.convert.mapper;

import com.now.app.be.main.domain.model.ImageModel;
import com.now.app.be.main.infrastructure.web.response.dto.ImageResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-11T16:14:35+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
public class ImageDtoConvertMapperImpl implements ImageDtoConvertMapper {

    @Override
    public ImageResponseDto convertImageModelToResponseDto(ImageModel imageModel) {
        if ( imageModel == null ) {
            return null;
        }

        ImageResponseDto.ImageResponseDtoBuilder imageResponseDto = ImageResponseDto.builder();

        imageResponseDto.createdAt( imageModel.getCreatedAt() );
        imageResponseDto.id( imageModel.getId() );
        imageResponseDto.originalFile( imageModel.getOriginalFile() );
        imageResponseDto.resolution( imageModel.getResolution() );
        imageResponseDto.urlImage( imageModel.getUrlImage() );

        return imageResponseDto.build();
    }

    @Override
    public List<ImageResponseDto> convertListImageModelToReponseDto(List<ImageModel> imageModel) {
        if ( imageModel == null ) {
            return null;
        }

        List<ImageResponseDto> list = new ArrayList<ImageResponseDto>( imageModel.size() );
        for ( ImageModel imageModel1 : imageModel ) {
            list.add( convertImageModelToResponseDto( imageModel1 ) );
        }

        return list;
    }
}
