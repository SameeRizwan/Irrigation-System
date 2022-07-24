package com.example.plot.Dto;

import lombok.Data;

@Data
public class ConfigurationDto
{
  int id;
  int temperature;
  int IrrigationTimeInterval;
  int humidity;
  int waterThreshold;
}
