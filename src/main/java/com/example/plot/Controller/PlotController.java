package com.example.plot.Controller;

import com.example.plot.Dto.PlotDto;
import com.example.plot.Model.Plot;
import com.example.plot.Service.PlotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plot")
public class PlotController
{

  @Autowired
  PlotServiceImpl plotServiceImpl;

  @GetMapping(value = "/all")
  public ResponseEntity<List<Plot>> allPlot() {
    List<Plot> allPlots = plotServiceImpl.getAllPlots();
    return ResponseEntity.ok(allPlots);
  }

  @PostMapping(value = "/add")
  public ResponseEntity<Plot> addPlot(@RequestBody PlotDto plotDto) {
    Plot plot = plotServiceImpl.addPlot(plotDto);
    return ResponseEntity.ok(plot);
  }

  @PostMapping(value = "/configure")
  public ResponseEntity<Plot> configurePlot(@RequestBody PlotDto plotDto) throws Exception
  {
    Plot plot = plotServiceImpl.configurePlot(plotDto);
    return ResponseEntity.ok(plot);
  }

  @PostMapping(value = "/edit")
  public ResponseEntity<Plot> editPlot(@RequestBody PlotDto plotDto) {
    Plot plot = plotServiceImpl.editPlot(plotDto);
    return ResponseEntity.ok(plot);
  }



}
