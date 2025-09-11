package com.now.app.be.main.domain.exception;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotFoundExceptionTest {

  @Test
  void testDefaultConstructor() {
    NotFoundException ex = new NotFoundException();
    assertNull(ex.getMessage());
    assertNull(ex.getCause());
  }

  @Test
  void testConstructorWithMessage() {
    NotFoundException ex = new NotFoundException("Elemento no encontrado");
    assertEquals("Elemento no encontrado", ex.getMessage());
    assertNull(ex.getCause());
  }

  @Test
  void testConstructorWithCause() {
    Throwable cause = new RuntimeException("Causa original");
    NotFoundException ex = new NotFoundException(cause);
    assertEquals("java.lang.RuntimeException: Causa original", ex.getMessage());
    assertSame(cause, ex.getCause());
  }

  @Test
  void testConstructorWithMessageAndCause() {
    Throwable cause = new RuntimeException("Error en DB");
    NotFoundException ex = new NotFoundException("No encontrado", cause);

    assertEquals("No encontrado", ex.getMessage());
    assertSame(cause, ex.getCause());
  }

  @Test
  void testSerializable() throws Exception {
    NotFoundException original = new NotFoundException("Serial test", new RuntimeException("inner"));

    // Serializar
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(original);
    oos.close();

    // Deserializar
    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
    NotFoundException deserialized = (NotFoundException) ois.readObject();

    assertNotNull(deserialized);
    assertEquals(original.getMessage(), deserialized.getMessage());
  }

}
