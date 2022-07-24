package com.example.plot.Controller;

import com.example.plot.Execption.PlotNotfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlotNotfoundExceptionController
{
  @ExceptionHandler(value = PlotNotfoundException.class)
  public ResponseEntity<Object> exception(PlotNotfoundException exception) {
    return new ResponseEntity<>("Plot not found Invalid Id", HttpStatus.NOT_FOUND);
  }
}