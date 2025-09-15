package com.now.app.be.main.application.port.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.now.app.be.main.application.port.in.ImageService;
import com.now.app.be.main.application.port.out.ImageRepositoryPort;
import com.now.app.be.main.domain.exception.NotFoundException;
import com.now.app.be.main.domain.model.ImageModel;

import net.coobird.thumbnailator.Thumbnails;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ImageServiceImpl implements ImageService {

  private final ImageRepositoryPort imageRepositoryPort;

  @Value("${execute.container}")
  private boolean isEnvironmentCloud;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

  public ImageServiceImpl(ImageRepositoryPort imageRepositoryPort) {
    this.imageRepositoryPort = imageRepositoryPort;
  }

  @Override
  public Mono<List<ImageModel>> getListMono() {
    return Mono.fromCallable(() -> 
      imageRepositoryPort.findAll()
    ).subscribeOn(Schedulers.boundedElastic());
  }

  @Override
  public Mono<ImageModel> getImageModelMono(UUID idTask) {
    LOGGER.info("The image with the task id is searched for: {}", idTask);
    return Mono.justOrEmpty(imageRepositoryPort.findById(idTask))
        .switchIfEmpty(Mono.error(new NotFoundException(
            "The idTask: '" + idTask + "' does not exist, please enter another valid idTask again.")))
        .subscribeOn(Schedulers.boundedElastic());
  }

  @Override
  public Mono<byte[]> resizeImage(MultipartFile file, Integer width, Integer height) {
    return Mono.fromCallable(() -> {
      String nameFileMdFive = md5OfFile(file);
      
      Path outputPath = !isEnvironmentCloud
          ? Paths.get(System.getProperty("user.home").concat("/Desktop"), nameFileMdFive)
          : Paths.get("/repo-app", nameFileMdFive);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Thumbnails.of(file.getInputStream()).size(width, height).toOutputStream(baos);
      
      LOGGER.info("The representation of the new image is now stored");
      saveImageResize(file, outputPath.toString(), width, height, nameFileMdFive);
      LOGGER.info("Image saved successfully");

      saveImageNewInPc(outputPath, baos, file);

      return baos.toByteArray();
    }).subscribeOn(Schedulers.boundedElastic());
  }

  private void saveImageResize(MultipartFile file, String imageEnd, Integer width, Integer height,
      String nameFileMdFive) throws IOException {
    imageRepositoryPort.save(ImageModel.builder().urlImage(imageEnd).createdAt(LocalDateTime.now())
        .originalFile(nameFileMdFive).resolution(String.valueOf(width) + "x" + String.valueOf(height))
        .urlImage(imageEnd.concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename()))).build());
  }

  private String md5OfFile(MultipartFile file) throws IOException {
    try (InputStream is = file.getInputStream()) {
      return DigestUtils.md5DigestAsHex(is);
    }
  }
  
  private void saveImageNewInPc(Path outputPath, ByteArrayOutputStream baos,MultipartFile file) throws IOException {
    Files.write(outputPath, baos.toByteArray());
  }
 
}
