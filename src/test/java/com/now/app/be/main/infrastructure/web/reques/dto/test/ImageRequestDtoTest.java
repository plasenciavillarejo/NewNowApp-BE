package com.now.app.be.main.infrastructure.web.reques.dto.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import com.now.app.be.main.infrastructure.web.reques.dto.ImageRequestDto;

@ExtendWith(MockitoExtension.class)
class ImageRequestDtoTest {

  @Test
  void testSetAndGetTaskId() {
      UUID id = UUID.randomUUID();
      ImageRequestDto dto = new ImageRequestDto();
      dto.setTaskId(id);

      assertEquals(id, dto.getTaskId());
  }

  @Test
  void testSerializable() throws Exception {
      UUID id = UUID.randomUUID();
      ImageRequestDto dto = new ImageRequestDto();
      dto.setTaskId(id);

      // Serialize
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(dto);
      oos.close();

      // Deserialize
      ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
      ImageRequestDto deserialized = (ImageRequestDto) ois.readObject();

      assertNotNull(deserialized);
      assertEquals(dto.getTaskId(), deserialized.getTaskId());
  }

  @Test
  void testDefaultValues() {
      ImageRequestDto dto = new ImageRequestDto();
      assertNull(dto.getTaskId(), "Por defecto el taskId debe ser null");
  }
  
}
