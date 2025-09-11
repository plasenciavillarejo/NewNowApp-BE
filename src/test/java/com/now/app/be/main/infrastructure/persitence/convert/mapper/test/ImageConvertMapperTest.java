package com.now.app.be.main.infrastructure.persitence.convert.mapper.test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import com.now.app.be.main.domain.model.ImageModel;
import com.now.app.be.main.infrastructure.persitence.convert.mapper.ImageConvertMapper;
import com.now.app.be.main.infrastructure.persitence.entity.ImageEntity;

@ExtendWith(MockitoExtension.class)
public class ImageConvertMapperTest {

  private static ImageConvertMapper mapper;

  @BeforeAll
  static void init() {
      mapper = Mappers.getMapper(ImageConvertMapper.class);
      assertNotNull(mapper, "El mapper de MapStruct no se generó. Revisa la configuración del plugin/annotation processor.");
  }

  @Test
  void convertModelToEntity_ok() {
      // Arrange
      UUID id = UUID.randomUUID();
      LocalDateTime now = LocalDateTime.now();

      ImageModel model = new ImageModel();
      model.setId(id);
      model.setUrlImage("path/thumb.png");
      model.setCreatedAt(now);
      model.setResolution("800x600");
      model.setOriginalFile("098f6bcd4621d373cade4e832627b4f6");

      // Act
      ImageEntity entity = mapper.convertModelToEntity(model);

      // Assert
      assertNotNull(entity);
      assertEquals(id, entity.getId());
      assertEquals("path/thumb.png", entity.getUrlImage());
      assertEquals(now, entity.getCreatedAt());
      assertEquals("800x600", entity.getResolution());
      assertEquals("098f6bcd4621d373cade4e832627b4f6", entity.getOriginalFile());
  }

  @Test
  void convertEntityToModel_ok() {
      // Arrange
      UUID id = UUID.randomUUID();
      LocalDateTime now = LocalDateTime.now();

      ImageEntity entity = new ImageEntity();
      entity.setId(id);
      entity.setUrlImage("path/thumb2.jpg");
      entity.setCreatedAt(now);
      entity.setResolution("1024x768");
      entity.setOriginalFile("md5-xyz");

      // Act
      ImageModel model = mapper.convertEntityToModel(entity);

      // Assert
      assertNotNull(model);
      assertEquals(id, model.getId());
      assertEquals("path/thumb2.jpg", model.getUrlImage());
      assertEquals(now, model.getCreatedAt());
      assertEquals("1024x768", model.getResolution());
      assertEquals("md5-xyz", model.getOriginalFile());
  }

  @Test
  void convertListEntityToDto_ok() {
      // Arrange
      ImageEntity e1 = new ImageEntity();
      e1.setId(UUID.randomUUID());
      e1.setUrlImage("a.png");
      e1.setCreatedAt(LocalDateTime.now());
      e1.setResolution("200x200");
      e1.setOriginalFile("md5-a");

      ImageEntity e2 = new ImageEntity();
      e2.setId(UUID.randomUUID());
      e2.setUrlImage("b.png");
      e2.setCreatedAt(LocalDateTime.now());
      e2.setResolution("300x300");
      e2.setOriginalFile("md5-b");

      // Act
      List<ImageModel> models = mapper.convertListEntityToDto(List.of(e1, e2));

      // Assert
      assertNotNull(models);
      assertEquals(2, models.size());
      assertEquals("a.png", models.get(0).getUrlImage());
      assertEquals("b.png", models.get(1).getUrlImage());
      assertEquals("200x200", models.get(0).getResolution());
      assertEquals("300x300", models.get(1).getResolution());
  }

  @Test
  void nullSafety() {
      assertNull(mapper.convertModelToEntity(null));
      assertNull(mapper.convertEntityToModel(null));
      assertNotNull(mapper.convertListEntityToDto(List.of())); // lista vacía → no null
      assertTrue(mapper.convertListEntityToDto(List.of()).isEmpty());
  }
  
}
