package com.now.app.be.main.infrastructure.persitence.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.now.app.be.main.application.port.out.ImageRepositoryPort;
import com.now.app.be.main.domain.model.ImageModel;
import com.now.app.be.main.infrastructure.persitence.convert.mapper.ImageConvertMapper;
import com.now.app.be.main.infrastructure.persitence.dao.ImageDao;

@Service
public class ImageRepositoryAdapter implements ImageRepositoryPort {

  private final ImageDao imageDao;

  public ImageRepositoryAdapter(ImageDao imageDao) {
    this.imageDao = imageDao;
  }

  @Override
  @Transactional(readOnly = true)
  public ImageModel findById(UUID idTask) {
    return ImageConvertMapper.mapper.convertEntityToModel(imageDao.findById(idTask).orElse(null));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ImageModel> findAll() {
    return ImageConvertMapper.mapper.convertListEntityToDto(imageDao.findAll());
  }

  @Override
  @Transactional
  public void save(ImageModel imageModel) {
    imageDao.save(ImageConvertMapper.mapper.convertModelToEntity(imageModel));
  }

}
