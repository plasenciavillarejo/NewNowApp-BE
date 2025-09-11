package com.now.app.be.main.infrastructure.web.response.dto.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.now.app.be.main.infrastructure.web.response.dto.ImageResponseDto;

@ExtendWith(MockitoExtension.class)
class ImageResponseDtoTest {

  @Test
  void testSetAndGetFields() {
      UUID id = UUID.randomUUID();
      LocalDateTime now = LocalDateTime.now();
      String originalFile = "hash-md5";
      String resolution = "800x600";
      String urlImage = "http://localhost/img.png";

      ImageResponseDto dto = new ImageResponseDto();
      dto.setId(id);
      dto.setCreatedAt(now);
      dto.setOriginalFile(originalFile);
      dto.setResolution(resolution);
      dto.setUrlImage(urlImage);

      assertEquals(id, dto.getId());
      assertEquals(now, dto.getCreatedAt());
      assertEquals(originalFile, dto.getOriginalFile());
      assertEquals(resolution, dto.getResolution());
      assertEquals(urlImage, dto.getUrlImage());
  }

  @Test
  void testDefaultValues() {
      ImageResponseDto dto = new ImageResponseDto();

      assertNull(dto.getId(), "Por defecto id debe ser null");
      assertNull(dto.getCreatedAt(), "Por defecto createdAt debe ser null");
      assertNull(dto.getOriginalFile(), "Por defecto originalFile debe ser null");
      assertNull(dto.getResolution(), "Por defecto resolution debe ser null");
      assertNull(dto.getUrlImage(), "Por defecto urlImage debe ser null");
  }

  @Test
  void testSerializable() throws Exception {
      ImageResponseDto dto = new ImageResponseDto();
      dto.setId(UUID.randomUUID());
      dto.setCreatedAt(LocalDateTime.of(2025, 9, 11, 15, 0));
      dto.setOriginalFile("file-md5");
      dto.setResolution("1024x768");
      dto.setUrlImage("http://localhost/test.png");

      // Serializar
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(dto);
      oos.close();

      // Deserializar
      ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
      ImageResponseDto deserialized = (ImageResponseDto) ois.readObject();

      assertNotNull(deserialized);
      assertEquals(dto.getId(), deserialized.getId());
      assertEquals(dto.getCreatedAt(), deserialized.getCreatedAt());
      assertEquals(dto.getOriginalFile(), deserialized.getOriginalFile());
      assertEquals(dto.getResolution(), deserialized.getResolution());
      assertEquals(dto.getUrlImage(), deserialized.getUrlImage());
  }
  
}
