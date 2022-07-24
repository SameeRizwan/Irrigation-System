package com.example.plot.Simulation;

import com.example.plot.Enums.IrrigationEventStatus;
import com.example.plot.Model.Configuration;
import com.example.plot.Model.IrrigationData;
import com.example.plot.Model.IrrigationEvent;
import com.example.plot.Model.Plot;
import com.example.plot.Repository.IrrigationEventRepository;
import com.example.plot.Repository.PlotRepository;
import com.example.plot.Service.SensorPlotIntegeration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.plot.Properties.IRRIGATION_FAILED_RETRY;
import static com.example.plot.Properties.IRRIGATION_FAILED_SCHEDULER_RATE;

@Component
@Transactional
@Slf4j
public class IrrigationFailedEventSimulation
{

  @Autowired
  PlotRepository plotRepository;

  @Autowired
  SensorPlotIntegeration sensorPlotIntegeration;

  @Autowired
  IrrigationEventRepository irrigationEventRepository;

  @Scheduled(fixedRate = IRRIGATION_FAILED_SCHEDULER_RATE)
  public void ExecuteIrrigationTransactions()
  {
    List<IrrigationEvent> irrigationEvent = irrigationEventRepository.findAll().stream().filter(event -> event.getRetry() < IRRIGATION_FAILED_RETRY).collect(Collectors.toList());

    if (irrigationEvent.size() == 0)
    {
      log.info("There are no Failed Irrigation Events");
    }
    else
    {
      irrigationEvent.stream().forEach(event -> {

        Configuration configuration = event.getPlot().getPlotData().getConfiguration();
        IrrigationData irrigationData = event.getPlot().getPlotData().getIrrigationData();
        boolean irrigationExecutionResult = sensorPlotIntegeration.sensorPlotIntegration(event);

        if (irrigationExecutionResult)
        {
          event.setStatus(IrrigationEventStatus.SUCCEDED);
          event.setRetry(0);
          irrigationData.setPreviousIrrigationTime(LocalDateTime.now());
          irrigationData.setNextIrrigationTime(LocalDateTime.now().plusMinutes(configuration.getIrrigationTimeInterval()));
          log.info("The Irrigation is SUCCEDED " + event.getPlot().getPlotData().toString());
        }
        else
        {
          event.setRetry(event.getRetry() + 1);
          event.setStatus(IrrigationEventStatus.FAILED);
          log.info("The Irrigation is FAILED " + event.getPlot().getPlotData().toString());
        }
        irrigationEventRepository.save(event);
      });
    }
  }
}
