package com.now.app.be.main.infrastructure.web.reques.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequestDto implements Serializable {

  private Long taskId;

  private static final long serialVersionUID = 2578479750976195987L;

}
