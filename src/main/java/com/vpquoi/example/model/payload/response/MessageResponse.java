package com.vpquoi.example.model.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MessageResponse {

  @Builder.Default
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date timestamp = new Date();

  private String message;

  private String error;

  private Integer status;

}
