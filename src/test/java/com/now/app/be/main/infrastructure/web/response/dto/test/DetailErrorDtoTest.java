package com.now.app.be.main.infrastructure.web.response.dto.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.now.app.be.main.infrastructure.web.response.dto.DetailErrorDto;

@ExtendWith(MockitoExtension.class)
class DetailErrorDtoTest {

  @Test
  void testSetAndGetFields() {
      LocalDateTime now = LocalDateTime.now();
      String msg = "Algo salió mal";
      int code = 400;

      DetailErrorDto dto = new DetailErrorDto();
      dto.setLocalDateTime(now);
      dto.setMessage(msg);
      dto.setCode(code);

      assertEquals(now, dto.getLocalDateTime());
      assertEquals(msg, dto.getMessage());
      assertEquals(code, dto.getCode());
  }

  @Test
  void testDefaultValues() {
      DetailErrorDto dto = new DetailErrorDto();
      assertNull(dto.getLocalDateTime(), "Por defecto localDateTime debe ser null");
      assertNull(dto.getMessage(), "Por defecto message debe ser null");
      assertEquals(0, dto.getCode(), "Por defecto code debe ser 0");
  }

  @Test
  void testSerializable() throws Exception {
      DetailErrorDto dto = new DetailErrorDto();
      dto.setLocalDateTime(LocalDateTime.of(2025, 9, 11, 10, 0));
      dto.setMessage("Error test");
      dto.setCode(500);

      // Serializar
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(dto);
      oos.close();

      // Deserializar
      ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
      DetailErrorDto deserialized = (DetailErrorDto) ois.readObject();

      assertNotNull(deserialized);
      assertEquals(dto.getLocalDateTime(), deserialized.getLocalDateTime());
      assertEquals(dto.getMessage(), deserialized.getMessage());
      assertEquals(dto.getCode(), deserialized.getCode());
  }
  
  
  
}
