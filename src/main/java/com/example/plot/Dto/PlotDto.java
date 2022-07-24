package com.example.plot.Dto;

import lombok.Data;

@Data
public class PlotDto
{
  private int id;
  private String location;
  private String area;
  private String ownedBy;
  private PlotDataDto plotDataDto;
}
