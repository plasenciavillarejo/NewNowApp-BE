package com.now.app.be.main.infrastructure.web.convert.mapper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import com.now.app.be.main.domain.model.ImageModel;
import com.now.app.be.main.infrastructure.web.convert.mapper.ImageDtoConvertMapper;
import com.now.app.be.main.infrastructure.web.response.dto.ImageResponseDto;

@ExtendWith(MockitoExtension.class)
public class ImageDtoConvertMapperTest {

  private static ImageDtoConvertMapper mapper;

  @BeforeAll
  static void init() {
      mapper = Mappers.getMapper(ImageDtoConvertMapper.class);
      assertNotNull(mapper, "El mapper de MapStruct no se generó. Revisa la configuración del annotation processor.");
  }

  @Test
  void convertImageModelToResponseDto_ok() {
      // given
      UUID id = UUID.randomUUID();
      LocalDateTime now = LocalDateTime.now();

      ImageModel model = new ImageModel();
      model.setId(id);
      model.setUrlImage("http://localhost/images/test.png");
      model.setCreatedAt(now);
      model.setResolution("640x480");
      model.setOriginalFile("md5-xyz");

      // when
      ImageResponseDto dto = mapper.convertImageModelToResponseDto(model);

      // then
      assertNotNull(dto);
      assertEquals("http://localhost/images/test.png", dto.getUrlImage());
      assertEquals("640x480", dto.getResolution());
      assertEquals("md5-xyz", dto.getOriginalFile()); 
      // Ajusta estos asserts a los campos reales de tu DTO
  }

  @Test
  void convertListImageModelToResponseDto_ok() {
      ImageModel m1 = new ImageModel();
      m1.setId(UUID.randomUUID());
      m1.setUrlImage("a.png");
      m1.setResolution("100x100");
      m1.setOriginalFile("md5-a");

      ImageModel m2 = new ImageModel();
      m2.setId(UUID.randomUUID());
      m2.setUrlImage("b.png");
      m2.setResolution("200x200");
      m2.setOriginalFile("md5-b");

      List<ImageResponseDto> dtos = mapper.convertListImageModelToReponseDto(List.of(m1, m2));

      assertNotNull(dtos);
      assertEquals(2, dtos.size());
      assertEquals("a.png", dtos.get(0).getUrlImage());
      assertEquals("b.png", dtos.get(1).getUrlImage());
      assertEquals("100x100", dtos.get(0).getResolution());
      assertEquals("200x200", dtos.get(1).getResolution());
  }

  @Test
  void nullSafety() {
      assertNull(mapper.convertImageModelToResponseDto(null));
      assertNotNull(mapper.convertListImageModelToReponseDto(List.of()));
      assertTrue(mapper.convertListImageModelToReponseDto(List.of()).isEmpty());
  }
  
}
