package com.example.plot.Service;

import com.example.plot.Model.Configuration;
import com.example.plot.Model.IrrigationEvent;
import com.example.plot.Model.Sensor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SensorPlotIntegerationImpl implements SensorPlotIntegeration
{

  @Override
  public boolean sensorPlotIntegration(IrrigationEvent irrigationEvent)
  {
    List<Sensor> sensor = irrigationEvent.getPlot().getPlotData().getSensor();
//    log.info("Details of plot Irrigation: " + irrigationEvent.getPlot().toString());

    Configuration configuration = irrigationEvent.getPlot().getPlotData().getConfiguration();
//    log.info("Details of Configuration of plot Irrigation: " + configuration.toString());

    boolean flag = sensor.stream().anyMatch(e -> e.isStatus() == true);
    if (flag)
    {
      log.info("Succeeded for plot: " + irrigationEvent.getPlot().toString());
    }
    else
    {
      log.info("Failed for plot Sensor Not Available");
    }
    return flag;
  }
}
