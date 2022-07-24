package com.example.plot.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PlotDataDto
{
  private int cropId;
  private ConfigurationDto configuration;
  private IrrigationDataDto irrigationData;
  private List<SensorDto> sensor;
}
