package com.example.plot.Controller;

import com.example.plot.Execption.SensorNotfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SensorNotfoundExceptionController
{
  @ExceptionHandler(value = SensorNotfoundException.class)
  public ResponseEntity<Object> exception(SensorNotfoundException exception) {
    return new ResponseEntity<>("Sensor not found", HttpStatus.NOT_FOUND);
  }
}