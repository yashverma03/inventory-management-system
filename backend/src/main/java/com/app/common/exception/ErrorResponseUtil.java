package com.app.common.exception;

import com.app.common.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class ErrorResponseUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void writeErrorResponse(
      HttpServletResponse response,
      HttpStatus status,
      String message) throws IOException {
    ErrorResponse errorResponse = new ErrorResponse(message, status.getReasonPhrase());
    response.setStatus(status.value());
    response.setContentType("application/json");
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }

  public static void writeErrorResponse(
      HttpServletResponse response,
      ResponseStatusException ex) throws IOException {
    String message = ex.getReason() != null ? ex.getReason() : ex.getMessage();
    HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
    String reasonPhrase = status != null ? status.getReasonPhrase() : "Error";
    ErrorResponse errorResponse = new ErrorResponse(message, reasonPhrase);
    response.setStatus(ex.getStatusCode().value());
    response.setContentType("application/json");
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
