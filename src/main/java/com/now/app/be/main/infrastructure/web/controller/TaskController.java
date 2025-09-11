package com.now.app.be.main.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.now.app.be.main.infrastructure.web.response.dto.ImageResponseDto;

import reactor.core.publisher.Mono;

@RestController(value = "/task")
public class TaskController {
  
  
  public TaskController () {
    
  }
  
  @GetMapping(value = "/{taskId}")
  public Mono<ResponseEntity<ImageResponseDto>> task(@PathVariable("taskId") Long taskId){
    return null; 
  }
  
 
  
  
}
