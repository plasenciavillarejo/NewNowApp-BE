package com.now.app.be.main.application.port.in;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.now.app.be.main.application.port.out.ImageRepositoryPort;
import com.now.app.be.main.application.port.service.impl.ImageServiceImpl;
import com.now.app.be.main.domain.exception.NotFoundException;
import com.now.app.be.main.domain.model.ImageModel;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

  @Mock
  ImageRepositoryPort imageRepositoryPort;

  ImageService imageService; // SUT

  @BeforeEach
  void setup() {
    imageService = new ImageServiceImpl(imageRepositoryPort);
  }

  @Test
  void getListMono_returnsList() {
    var img1 = ImageModel.builder().id(UUID.randomUUID()).urlImage("a.png").createdAt(LocalDateTime.now()).build();
    var img2 = ImageModel.builder().id(UUID.randomUUID()).urlImage("b.png").createdAt(LocalDateTime.now()).build();
    when(imageRepositoryPort.findAll()).thenReturn(List.of(img1, img2));

    Mono<List<ImageModel>> mono = imageService.getListMono();

    StepVerifier.create(mono).expectNextMatches(list -> list.size() == 2 && list.containsAll(List.of(img1, img2)))
        .verifyComplete();

    verify(imageRepositoryPort, times(1)).findAll();
  }

  @Test
  void getImageModelMono_found() {
    UUID id = UUID.randomUUID();
    var img = ImageModel.builder().id(id).urlImage("x.png").createdAt(LocalDateTime.now()).build();
    when(imageRepositoryPort.findById(id)).thenReturn(img); // o Optional.of(img) según tu firma

    Mono<ImageModel> mono = imageService.getImageModelMono(id);

    StepVerifier.create(mono).expectNext(img).verifyComplete();

    verify(imageRepositoryPort, times(1)).findById(id);
  }

  @Test
  void getImageModelMono_notFound_emitsError() {
    UUID id = UUID.randomUUID();
    when(imageRepositoryPort.findById(id)).thenReturn(null); // o Optional.empty()

    Mono<ImageModel> mono = imageService.getImageModelMono(id);

    StepVerifier.create(mono).expectErrorSatisfies(ex -> {
      if (!(ex instanceof NotFoundException)) {
        throw new AssertionError("Expected NotFoundException but was: " + ex.getClass());
      }
    }).verify();

    verify(imageRepositoryPort, times(1)).findById(id);
  }

}
