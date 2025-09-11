package com.now.app.be.main.infrastructure.web.response.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailErrorDto implements Serializable {
  
  private LocalDateTime localDateTime;
  
  private String message;
  
  private int code;
    
  private static final long serialVersionUID = -2173524353641498594L;

}
