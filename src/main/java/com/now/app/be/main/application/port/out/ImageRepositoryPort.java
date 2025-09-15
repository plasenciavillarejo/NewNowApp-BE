package com.now.app.be.main.application.port.out;

import java.util.List;
import java.util.UUID;

import com.now.app.be.main.domain.model.ImageModel;

public interface ImageRepositoryPort {

  public List<ImageModel> findAll();
  
  public ImageModel findById(UUID idTask);
  
  public void save(ImageModel imageModel);
  
  public ImageModel findByOriginalFile(String nameOriginal);
  
}
