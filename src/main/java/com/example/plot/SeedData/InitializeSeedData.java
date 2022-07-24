package com.example.plot.SeedData;

import com.example.plot.Enums.IrrigationEventStatus;
import com.example.plot.Model.Configuration;
import com.example.plot.Model.Crop;
import com.example.plot.Model.IrrigationData;
import com.example.plot.Model.IrrigationEvent;
import com.example.plot.Model.Plot;
import com.example.plot.Model.PlotData;
import com.example.plot.Model.Sensor;
import com.example.plot.Repository.ConfigurationRepository;
import com.example.plot.Repository.CropRepository;
import com.example.plot.Repository.IrrigationDataRepository;
import com.example.plot.Repository.IrrigationEventRepository;
import com.example.plot.Repository.PlotDataRepository;
import com.example.plot.Repository.PlotRepository;
import com.example.plot.Repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@Slf4j
public class InitializeSeedData implements CommandLineRunner
{

  @Autowired
  PlotRepository plotRepository;

  @Autowired
  CropRepository cropRepository;

  @Autowired
  ConfigurationRepository configurationRepository;

  @Autowired
  SensorRepository sensorRepository;

  @Autowired
  IrrigationDataRepository irrigationDataRepository;

  @Autowired
  PlotDataRepository plotDataRepository;

  @Autowired
  IrrigationEventRepository irrigationEventRepository;

  @Override
  public void run(String... args) throws Exception
  {
    cropData();
    PlotData();
    PlotData1();
    PlotData2();
    PlotData3();
  }

  private boolean cropData()
  {
    boolean flag = false;
    try
    {
      ArrayList<Crop> list = new ArrayList<>();
      list.add(new Crop("Rice", 25));
      list.add(new Crop("Chilli", 5));
      list.add(new Crop("Corn", 20));
      list.add(new Crop("Wheat", 15));
      list.add(new Crop("Cotton", 10));
      cropRepository.saveAll(list);
      flag = true;
      log.info("Crops Data hase been Initialized ");
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
    }
    return flag;
  }

  private boolean PlotData()
  {
    boolean flag = false;
    try
    {
      Plot plot = new Plot();
      plot.setLocation("Karachi");
      plot.setOwnedBy("Ali");
      plot.setArea("100");

      PlotData plotData = new PlotData();
      plotData.setCrop(cropRepository.findAll().get(1));

      Configuration configuration = new Configuration();
      configuration.setHumidity(5);
      configuration.setTemperature(30);
      configuration.setWaterThreshold((plotData.getCrop().getWaterRequired() * Integer.valueOf(plot.getArea())) + 5);
      configuration.setIrrigationTimeInterval(4);

      IrrigationData irrigationData = new IrrigationData();
      irrigationData.setNextIrrigationTime(LocalDateTime.now().plusMinutes(configuration.getIrrigationTimeInterval()));
      irrigationData.setPreviousIrrigationTime(LocalDateTime.now());

      ArrayList<Sensor> list = new ArrayList<>();
      list.add(new Sensor("Karachi","WATER",true));


      plotData.setConfiguration(configuration);
      plotData.setIrrigationData(irrigationData);
      plotData.setSensor(list);
      plot.setPlotData(plotData);

      configurationRepository.save(configuration);
      irrigationDataRepository.save(irrigationData);
      sensorRepository.saveAll(list);
      plotDataRepository.save(plotData);
      plotRepository.save(plot);

      flag = true;
      log.info("Plot Data hase been Initialized ");
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
    }
    return flag;
  }


  private boolean PlotData1()
  {
    boolean flag = false;
    try
    {
      Plot plot = new Plot();
      plot.setLocation("Multan");
      plot.setOwnedBy("Ahmad");
      plot.setArea("50");

      PlotData plotData = new PlotData();
      plotData.setCrop(cropRepository.findAll().get(0));

      Configuration configuration = new Configuration();
      configuration.setHumidity(15);
      configuration.setTemperature(25);
      configuration.setWaterThreshold((plotData.getCrop().getWaterRequired() * Integer.valueOf(plot.getArea())) + 5);
      configuration.setIrrigationTimeInterval(3);

      IrrigationData irrigationData = new IrrigationData();
      irrigationData.setNextIrrigationTime(LocalDateTime.now().plusMinutes(configuration.getIrrigationTimeInterval()));
      irrigationData.setPreviousIrrigationTime(LocalDateTime.now());

      ArrayList<Sensor> list = new ArrayList<>();
      list.add(new Sensor("Multan","WATER",false));


      plotData.setConfiguration(configuration);
      plotData.setIrrigationData(irrigationData);
      plotData.setSensor(list);
      plot.setPlotData(plotData);

      configurationRepository.save(configuration);
      irrigationDataRepository.save(irrigationData);
      sensorRepository.saveAll(list);
      plotDataRepository.save(plotData);
      plotRepository.save(plot);

      flag = true;
      log.info("Plot Data hase been Initialized ");
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
    }
    return flag;
  }

  private boolean PlotData2()
  {
    boolean flag = false;
    try
    {
      Plot plot = new Plot();
      plot.setLocation("LQP");
      plot.setOwnedBy("Waqas");
      plot.setArea("70");

      PlotData plotData = new PlotData();
      plotData.setCrop(cropRepository.findAll().get(0));

      Configuration configuration = new Configuration();
      configuration.setHumidity(15);
      configuration.setTemperature(25);
      configuration.setWaterThreshold((plotData.getCrop().getWaterRequired() * Integer.valueOf(plot.getArea())) + 5);
      configuration.setIrrigationTimeInterval(3);

      IrrigationData irrigationData = new IrrigationData();
      irrigationData.setNextIrrigationTime(LocalDateTime.now().plusMinutes(configuration.getIrrigationTimeInterval()));
      irrigationData.setPreviousIrrigationTime(LocalDateTime.now());

      ArrayList<Sensor> list = new ArrayList<>();
      list.add(new Sensor("LQP","WATER",false));


      plotData.setConfiguration(configuration);
      plotData.setIrrigationData(irrigationData);
      plotData.setSensor(list);
      plot.setPlotData(plotData);

      IrrigationEvent irrigationTransaction = new IrrigationEvent(LocalDateTime.now(), plot, 3);
      irrigationTransaction.setStatus(IrrigationEventStatus.FAILED);

      configurationRepository.save(configuration);
      irrigationDataRepository.save(irrigationData);
      sensorRepository.saveAll(list);
      plotDataRepository.save(plotData);
      plotRepository.save(plot);
      irrigationEventRepository.save(irrigationTransaction);

      flag = true;
      log.info("Plot Data hase been Initialized ");
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
    }
    return flag;
  }

  private boolean PlotData3()
  {
    boolean flag = false;
    try
    {
      Plot plot = new Plot();
      plot.setLocation("Bahawalpur");
      plot.setOwnedBy("haris");
      plot.setArea("80");

      PlotData plotData = new PlotData();
      plotData.setCrop(cropRepository.findAll().get(0));

      Configuration configuration = new Configuration();
      configuration.setHumidity(10);
      configuration.setTemperature(20);
      configuration.setWaterThreshold((plotData.getCrop().getWaterRequired() * Integer.valueOf(plot.getArea())) + 5);
      configuration.setIrrigationTimeInterval(4);

      IrrigationData irrigationData = new IrrigationData();
      irrigationData.setNextIrrigationTime(LocalDateTime.now().plusMinutes(configuration.getIrrigationTimeInterval()));
      irrigationData.setPreviousIrrigationTime(LocalDateTime.now());

      ArrayList<Sensor> list = new ArrayList<>();
      list.add(new Sensor("Bahawalpur","WATER",true));


      plotData.setConfiguration(configuration);
      plotData.setIrrigationData(irrigationData);
      plotData.setSensor(list);
      plot.setPlotData(plotData);

      IrrigationEvent irrigationTransaction = new IrrigationEvent(LocalDateTime.now(), plot, 3);
      irrigationTransaction.setStatus(IrrigationEventStatus.SUCCEDED);

      configurationRepository.save(configuration);
      irrigationDataRepository.save(irrigationData);
      sensorRepository.saveAll(list);
      plotDataRepository.save(plotData);
      plotRepository.save(plot);
      irrigationEventRepository.save(irrigationTransaction);

      flag = true;
      log.info("Plot Data hase been Initialized ");
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
    }
    return flag;
  }
}
