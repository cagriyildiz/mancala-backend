package com.bol.mancala.web.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionInfo {

  private OffsetDateTime timestamp;

  private int status;

  private String error;

  private String message;

  private String path;

}
