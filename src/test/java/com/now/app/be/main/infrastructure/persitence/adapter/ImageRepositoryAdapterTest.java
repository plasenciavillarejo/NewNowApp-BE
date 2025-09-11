package com.now.app.be.main.infrastructure.persitence.adapter;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.now.app.be.main.domain.model.ImageModel;
import com.now.app.be.main.infrastructure.persitence.convert.mapper.ImageConvertMapper;
import com.now.app.be.main.infrastructure.persitence.dao.ImageDao;
import com.now.app.be.main.infrastructure.persitence.entity.ImageEntity;

@ExtendWith(MockitoExtension.class)

class ImageRepositoryAdapterTest {


  @Mock
  private ImageDao imageDao;

  @InjectMocks
  private ImageRepositoryAdapter adapter;

  private ImageConvertMapper mapper = Mappers.getMapper(ImageConvertMapper.class);

  private UUID id;
  private ImageEntity entity;
  private ImageModel model;

  @BeforeEach
  void setup() {
      id = UUID.randomUUID();

      entity = new ImageEntity();
      entity.setId(id);
      entity.setUrlImage("file.png");
      entity.setCreatedAt(LocalDateTime.now());
      entity.setResolution("800x600");
      entity.setOriginalFile("md5");

      model = mapper.convertEntityToModel(entity);
  }

  @Test
  void findById_found_returnsModel() {
      when(imageDao.findById(id)).thenReturn(Optional.of(entity));

      ImageModel result = adapter.findById(id);

      assertNotNull(result);
      assertEquals("file.png", result.getUrlImage());
      assertEquals("800x600", result.getResolution());
      verify(imageDao, times(1)).findById(id);
  }

  @Test
  void findById_notFound_returnsNull() {
      when(imageDao.findById(id)).thenReturn(Optional.empty());

      ImageModel result = adapter.findById(id);

      assertNull(result);
      verify(imageDao, times(1)).findById(id);
  }

  @Test
  void findAll_returnsListOfModels() {
      when(imageDao.findAll()).thenReturn(List.of(entity));

      List<ImageModel> result = adapter.findAll();

      assertNotNull(result);
      assertEquals(1, result.size());
      assertEquals("file.png", result.get(0).getUrlImage());
      verify(imageDao, times(1)).findAll();
  }

  
}
