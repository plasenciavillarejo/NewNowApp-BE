package com.now.app.be.main.application.port.in;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.now.app.be.main.domain.model.ImageModel;

import reactor.core.publisher.Mono;

public interface ImageService {

  public Mono<List<ImageModel>> getListMono();

  public Mono<ImageModel> getImageModelMono(UUID idTask);

  public Mono<byte []> resizeImage(MultipartFile file, Integer width, Integer height);

}
