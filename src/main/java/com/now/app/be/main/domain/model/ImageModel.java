package com.now.app.be.main.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageModel implements Serializable {

  private UUID id;

  private LocalDateTime createdAt;

  private String originalFile;

  private String resolution;

  private String urlImage;

  private static final long serialVersionUID = 2501656526271812732L;

}
