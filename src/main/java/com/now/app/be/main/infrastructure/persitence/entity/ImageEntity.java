package com.now.app.be.main.infrastructure.persitence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "images")
public class ImageEntity implements Serializable  {
  
  @Id
  @GeneratedValue(generator = "UUID")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;
  
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  
  @Column(name = "original_file")
  private String originalFile;
  
  @Column(name = "resolution")
  private String resolution;
  
  @Column(name = "url_image")
  private String urlImage;
  
  @PrePersist
  public void prePresist() {
    if (this.id == null) {
      this.id = UUID.randomUUID();
    }
  }
  
  private static final long serialVersionUID = 5708264823471606770L;

}
