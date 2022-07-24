package com.example.plot.Simulation;

import com.example.plot.Model.Alert;
import com.example.plot.Model.IrrigationEvent;
import com.example.plot.Repository.AlertRepository;
import com.example.plot.Repository.IrrigationEventRepository;
import com.example.plot.Service.SensorPlotIntegeration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.plot.Properties.IRRIGATION_FAILED_ALERT_SCHEDULER_RATE;
import static com.example.plot.Properties.IRRIGATION_FAILED_RETRY;

@Component
@Transactional
@Slf4j
public class IrrigationEventAlertSimulation
{

  @Autowired
  AlertRepository alertRepository;

  @Autowired
  SensorPlotIntegeration sensorPlotIntegeration;

  @Autowired
  IrrigationEventRepository irrigationEventRepository;

  @Scheduled(fixedRate = IRRIGATION_FAILED_ALERT_SCHEDULER_RATE)
  public void ExecuteIrrigationTransactions()
  {
    List<IrrigationEvent> irrigationEvent = irrigationEventRepository.findAll().stream().filter(event -> event.getRetry() == IRRIGATION_FAILED_RETRY).collect(Collectors.toList());
    if (irrigationEvent.size() == 0)
    {
      log.info("There are no Alert Events");
    }
    else
    {
      irrigationEvent.stream().forEach(event -> {
        Alert alert = alertRepository.findByEventId(event.getId());

        if (event.getRetry() == IRRIGATION_FAILED_RETRY && alert == null)
        {
          alert = new Alert();
          alert.setDateTime(LocalDateTime.now());
          alert.setPlot(event.getPlot());
          alert.setEvent(event);
          alertRepository.save(alert);
          log.info("Alert Send for Plot " + event.getPlot().toString());
        }
      });
    }
  }
}
