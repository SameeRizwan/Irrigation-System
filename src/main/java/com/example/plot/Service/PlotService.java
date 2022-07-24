package com.example.plot.Service;

import com.example.plot.Dto.ConfigurationDto;
import com.example.plot.Dto.PlotDto;
import com.example.plot.Model.Plot;
import com.example.plot.Repository.PlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PlotService
{
  List<Plot> getAllPlots();
  Plot getPlotById(int id);
  Plot addPlot(PlotDto newPlot);
  Plot editPlot(PlotDto plot);

  Plot configurePlot(PlotDto plot) throws Exception;
}
