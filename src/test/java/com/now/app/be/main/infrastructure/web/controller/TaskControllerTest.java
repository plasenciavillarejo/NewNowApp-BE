package com.now.app.be.main.infrastructure.web.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.multipart.MultipartFile;

import com.now.app.be.main.application.port.in.ImageService;
import com.now.app.be.main.domain.exception.NotFoundException;
import com.now.app.be.main.domain.model.ImageModel;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = TaskController.class)
class TaskControllerTest {


  @Autowired
  private WebTestClient webTestClient;

  @MockitoBean
  private ImageService imageService;

  @Test
  void getAll_returns200AndList() {
      ImageModel m1 = ImageModel.builder()
              .id(UUID.randomUUID())
              .urlImage("http://localhost/img/a.png")
              .createdAt(LocalDateTime.now())
              .resolution("100x100")
              .originalFile("md5-a")
              .build();

      ImageModel m2 = ImageModel.builder()
              .id(UUID.randomUUID())
              .urlImage("http://localhost/img/b.png")
              .createdAt(LocalDateTime.now())
              .resolution("200x200")
              .originalFile("md5-b")
              .build();

      Mockito.when(imageService.getListMono()).thenReturn(Mono.just(List.of(m1, m2)));

      webTestClient.get()
              .uri("/task/all")
              .exchange()
              .expectStatus().isOk()
              .expectHeader().contentType(MediaType.APPLICATION_JSON)
              .expectBody()
              .jsonPath("$[0].urlImage").isEqualTo("http://localhost/img/a.png")
              .jsonPath("$[1].urlImage").isEqualTo("http://localhost/img/b.png");
  }

  @Test
  void getById_found_returns200() {
      UUID id = UUID.randomUUID();
      ImageModel model = ImageModel.builder()
              .id(id)
              .urlImage("http://localhost/img/x.png")
              .createdAt(LocalDateTime.now())
              .resolution("640x480")
              .originalFile("md5-x")
              .build();

      Mockito.when(imageService.getImageModelMono(eq(id))).thenReturn(Mono.just(model));

      webTestClient.get()
              .uri("/task/{id}", id)
              .exchange()
              .expectStatus().isOk()
              .expectBody()
              .jsonPath("$.urlImage").isEqualTo("http://localhost/img/x.png")
              .jsonPath("$.resolution").isEqualTo("640x480");
  }

  @Test
  void getById_notFound_returns500ByDefault() {
      UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
      Mockito.when(imageService.getImageModelMono(eq(id)))
             .thenReturn(Mono.error(new NotFoundException("not found")));

      webTestClient.get()
              .uri("/task/{id}", id)
              .exchange()
              .expectStatus().is4xxClientError();
  }
  
}
