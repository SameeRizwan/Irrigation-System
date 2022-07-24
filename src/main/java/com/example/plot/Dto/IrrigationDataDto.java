package com.example.plot.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IrrigationDataDto
{
  int id;
  LocalDateTime previousIrrigationTime;
  LocalDateTime nextIrrigationTime;
}
