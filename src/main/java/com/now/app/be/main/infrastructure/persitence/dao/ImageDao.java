package com.now.app.be.main.infrastructure.persitence.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.now.app.be.main.infrastructure.persitence.entity.ImageEntity;

public interface ImageDao extends JpaRepository<ImageEntity, UUID> {

  public ImageEntity findByOriginalFile(String nameOriginal);
  
}
