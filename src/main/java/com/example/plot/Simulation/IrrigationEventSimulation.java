package com.example.plot.Simulation;

import com.example.plot.Enums.IrrigationEventStatus;
import com.example.plot.Model.Configuration;
import com.example.plot.Model.IrrigationData;
import com.example.plot.Model.IrrigationEvent;
import com.example.plot.Model.Plot;
import com.example.plot.Repository.IrrigationEventRepository;
import com.example.plot.Repository.PlotRepository;
import com.example.plot.Service.SensorPlotIntegeration;
import com.example.plot.Service.SensorPlotIntegerationImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.plot.Properties.IRRIGATION_FAILED_RETRY;
import static com.example.plot.Properties.IRRIGATION_SCHEDULER_RATE;

@Component
@Transactional
@Slf4j
public class IrrigationEventSimulation
{

  @Autowired
  PlotRepository plotRepository;

  @Autowired
  SensorPlotIntegeration sensorPlotIntegeration;

  @Autowired
  IrrigationEventRepository irrigationEventRepository;

  @Scheduled(fixedRate = IRRIGATION_SCHEDULER_RATE)
  public void ExecuteIrrigationTransactions()
  {
    List<Plot> allPlots = plotRepository.findAll();
    List<Plot> plotsIrrigation = new ArrayList<>();
    LocalDateTime upperLimit = LocalDateTime.now().plusMinutes(1);
    LocalDateTime lowerLimit = LocalDateTime.now().minusMinutes(2);

    for (int i = 0; i < allPlots.size(); i++)
    {
      LocalDateTime nextIrrigationTime = allPlots.get(i).getPlotData().getIrrigationData().getNextIrrigationTime();
      if (nextIrrigationTime.compareTo(lowerLimit) > 0 && nextIrrigationTime.compareTo(upperLimit) < 0)
      {
        plotsIrrigation.add(allPlots.get(i));
      }
      log.info("Plots Info " + allPlots.get(i).getPlotData().toString());
    }

    if (plotsIrrigation.size() == 0)
    {
      log.info("There are no Plots Right Now that need Irrigation");
    }
    else
    {
      plotsIrrigation.stream().forEach(plot -> {
        IrrigationEvent irrigationTransaction = new IrrigationEvent(LocalDateTime.now(), plot, 0);
        Configuration configuration = plot.getPlotData().getConfiguration();
        IrrigationData irrigationData = plot.getPlotData().getIrrigationData();
        boolean irrigationExecutionResult = sensorPlotIntegeration.sensorPlotIntegration(irrigationTransaction);

        if (irrigationExecutionResult)
        {
          irrigationTransaction.setStatus(IrrigationEventStatus.SUCCEDED);
          irrigationData.setPreviousIrrigationTime(LocalDateTime.now());
          irrigationData.setNextIrrigationTime(LocalDateTime.now().plusMinutes(configuration.getIrrigationTimeInterval()));
          log.info("The Irrigation is SUCCEDED " + plot.getPlotData().toString());
        }
        else
        {
          irrigationTransaction.setRetry(1);
          irrigationTransaction.setStatus(IrrigationEventStatus.FAILED);
          log.info("The Irrigation is FAILED " + plot.getPlotData().toString());
        }
        irrigationEventRepository.save(irrigationTransaction);
      });
    }
  }
}
