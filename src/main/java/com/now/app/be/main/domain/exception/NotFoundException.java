package com.now.app.be.main.domain.exception;

import java.io.Serializable;

public class NotFoundException extends Exception implements Serializable {

  public NotFoundException() {
    super();
  }

  public NotFoundException(String mensaje) {
    super(mensaje);
  }

  public NotFoundException(Throwable t) {
    super(t);
  }

  public NotFoundException(String mensaje, Throwable t) {
    super(mensaje, t);
  }

  private static final long serialVersionUID = 4287579900953491004L;

}
