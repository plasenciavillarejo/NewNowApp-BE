package com.now.app.be.main.infrastructure.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.now.app.be.main.application.port.in.ImageService;
import com.now.app.be.main.domain.exception.NotFoundException;
import com.now.app.be.main.infrastructure.web.convert.mapper.ImageDtoConvertMapper;
import com.now.app.be.main.infrastructure.web.response.dto.ImageResponseDto;

import jakarta.validation.constraints.Min;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping(value = "/task")
public class TaskController {

  private final ImageService imageService;

  public TaskController(ImageService imageService) {
    this.imageService = imageService;
  }

  @GetMapping(value = "/all")
  public Mono<ResponseEntity<List<ImageResponseDto>>> allTask() {
    return imageService.getListMono()
        .map(response -> new ResponseEntity<>(ImageDtoConvertMapper.mapper.convertListImageModelToReponseDto(response),
            HttpStatus.OK));
  }

  @GetMapping(value = "/{taskId}")
  public Mono<ResponseEntity<ImageResponseDto>> allTaskMono(@PathVariable("taskId") UUID taskId) {
    return imageService.getImageModelMono(taskId)
        .map(response -> new ResponseEntity<>(ImageDtoConvertMapper.mapper.convertImageModelToResponseDto(response),
            HttpStatus.OK))
        .onErrorMap(NotFoundException.class, e -> e);
  }

  @PostMapping
  public Mono<ResponseEntity<byte[]>> resizeImage(@RequestParam("file") MultipartFile file,
      @RequestParam("width") @Min(value = 1, message = " must be greater than 0") Integer width,
      @RequestParam("height") @Min(value = 1, message = " must be greater than 0") Integer height) {
    return imageService.resizeImage(file, width, height)
        .map(bytes -> ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"resized_" + file.getOriginalFilename() + "\"")
            .contentType(MediaType.parseMediaType(file.getContentType())).body(bytes));
  }
  
 
}
