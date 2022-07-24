package com.example.plot.Service;

import com.example.plot.Dto.PlotDto;
import com.example.plot.Dto.SensorDto;
import com.example.plot.Execption.PlotNotfoundException;
import com.example.plot.Execption.SensorNotfoundException;
import com.example.plot.Model.Configuration;
import com.example.plot.Model.IrrigationData;
import com.example.plot.Model.Plot;
import com.example.plot.Model.PlotData;
import com.example.plot.Model.Sensor;
import com.example.plot.Enums.SensorFunction;
import com.example.plot.Repository.ConfigurationRepository;
import com.example.plot.Repository.CropRepository;
import com.example.plot.Repository.IrrigationDataRepository;
import com.example.plot.Repository.PlotDataRepository;
import com.example.plot.Repository.PlotRepository;
import com.example.plot.Repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlotServiceImpl implements PlotService
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

  @Override
  public List<Plot> getAllPlots()
  {
    return plotRepository.findAll();
  }

  @Override
  public Plot getPlotById(int id)
  {
    Optional<Plot> byId = plotRepository.findById(id);
    if(byId.isPresent()){
      Plot plot = byId.get();
      return plot;
    }
    else{
      throw new PlotNotfoundException();
    }
  }

  @Override
  public Plot addPlot(PlotDto newPlot)
  {
    Plot plot = new Plot();
    plot.setLocation(newPlot.getLocation());
    plot.setArea(newPlot.getArea());
    plot.setOwnedBy(newPlot.getOwnedBy());

    PlotData plotData = new PlotData();
    plotData.setCrop(cropRepository.findById(newPlot.getPlotDataDto().getCropId()).get());

    Configuration configuration = new Configuration();
    configuration.setHumidity(newPlot.getPlotDataDto().getConfiguration().getHumidity());
    configuration.setTemperature(newPlot.getPlotDataDto().getConfiguration().getTemperature());
    configuration.setWaterThreshold(newPlot.getPlotDataDto().getConfiguration().getWaterThreshold());
    configuration.setIrrigationTimeInterval(newPlot.getPlotDataDto().getConfiguration().getIrrigationTimeInterval());
    plotData.setConfiguration(configuration);

    IrrigationData irrigationData = new IrrigationData();
    irrigationData.setNextIrrigationTime(LocalDateTime.now().plusMinutes(configuration.getIrrigationTimeInterval()));
    irrigationData.setPreviousIrrigationTime(LocalDateTime.now());
    plotData.setIrrigationData(irrigationData);

    List<Sensor> sensorlist = newPlot.getPlotDataDto().getSensor().stream().map(e -> {
      Sensor sensor = new Sensor();
      sensor.setLocation(e.getLocation());
      sensor.setSensorFunction(checkSensorFunctions(e));
      sensor.setStatus(true);
      return sensor;
    }).collect(Collectors.toList());

    plotData.setSensor(sensorlist);
    plot.setPlotData(plotData);

    configurationRepository.save(configuration);
    irrigationDataRepository.save(irrigationData);
    sensorRepository.saveAll(sensorlist);
    plotDataRepository.save(plotData);
    plotRepository.save(plot);
    return plot;
  }

  @Override
  public Plot editPlot(PlotDto plot)
  {
    Plot editedPlot = plotRepository.findById(plot.getId()).get();
    Configuration configuration = editedPlot.getPlotData().getConfiguration();
    PlotData plotData = editedPlot.getPlotData();

    if (Objects.nonNull(plot.getLocation()))
    {
      editedPlot.setLocation(plot.getLocation());
    }
    if (Objects.nonNull(plot.getArea()))
    {
      editedPlot.setArea(plot.getArea());
    }
    if (Objects.nonNull(plot.getOwnedBy()))
    {
      editedPlot.setOwnedBy(plot.getOwnedBy());
    }

    if (Objects.nonNull(plot.getPlotDataDto().getCropId()))
    {
      plotData.setCrop(cropRepository.findById(plot.getPlotDataDto().getCropId()).get());
    }

    if (Objects.nonNull(plot.getPlotDataDto().getConfiguration().getHumidity()))
    {
      configuration.setHumidity(plot.getPlotDataDto().getConfiguration().getHumidity());
    }
    if (Objects.nonNull(plot.getPlotDataDto().getConfiguration().getTemperature()))
    {
      configuration.setTemperature(plot.getPlotDataDto().getConfiguration().getTemperature());
    }
    if (Objects.nonNull(plot.getPlotDataDto().getConfiguration().getWaterThreshold()))
    {
      configuration.setWaterThreshold(plot.getPlotDataDto().getConfiguration().getWaterThreshold());
    }
    if (Objects.nonNull(plot.getPlotDataDto().getConfiguration().getIrrigationTimeInterval()))
    {
      configuration.setIrrigationTimeInterval(plot.getPlotDataDto().getConfiguration().getIrrigationTimeInterval());
    }

    IrrigationData irrigationData = plotData.getIrrigationData();
    if (Objects.nonNull(plot.getPlotDataDto().getIrrigationData().getNextIrrigationTime()))
    {
      irrigationData.setPreviousIrrigationTime(plotData.getIrrigationData().getNextIrrigationTime());
      irrigationData.setNextIrrigationTime(plot.getPlotDataDto().getIrrigationData().getNextIrrigationTime());
    }
    else{
      irrigationData.setPreviousIrrigationTime(plotData.getIrrigationData().getNextIrrigationTime());
      irrigationData.setNextIrrigationTime(LocalDateTime.now().plusMinutes(configuration.getIrrigationTimeInterval()));
    }

    plotData.setConfiguration(configuration);
    plotData.setIrrigationData(irrigationData);

    List<SensorDto> updatedSensor = plot.getPlotDataDto().getSensor();
    List<Sensor> sensor = plotData.getSensor();

    for (int i = 0; i < sensor.size(); i++)
    {
      if (sensor.get(i).getId() == updatedSensor.get(i).getId())
      {
        Sensor sensor1 = sensor.get(i);
        sensor1.setLocation(updatedSensor.get(i).getLocation());
        sensor1.setSensorFunction(checkSensorFunctions(updatedSensor.get(i)));
        sensor.set(i, sensor1);
      }
    }
    plotData.setSensor(sensor);
    editedPlot.setPlotData(plotData);

    configurationRepository.save(configuration);
    irrigationDataRepository.save(irrigationData);
    sensorRepository.saveAll(sensor);
    plotDataRepository.save(plotData);
    plotRepository.save(editedPlot);

    return editedPlot;
  }

  @Override
  public Plot configurePlot(PlotDto plot) throws Exception
  {
    Plot editedPlot = null;
    try{
      Optional<Plot> byId = plotRepository.findById(plot.getId());

      if(byId.isPresent()){
        editedPlot = byId.get();
      }

      Configuration configuration = editedPlot.getPlotData().getConfiguration();

      configuration.setHumidity(plot.getPlotDataDto().getConfiguration().getHumidity());
      configuration.setTemperature(plot.getPlotDataDto().getConfiguration().getTemperature());
      configuration.setWaterThreshold(plot.getPlotDataDto().getConfiguration().getWaterThreshold());
      configuration.setIrrigationTimeInterval(plot.getPlotDataDto().getConfiguration().getIrrigationTimeInterval());

      PlotData plotData = new PlotData();
      plotData.setConfiguration(configuration);
      editedPlot.setPlotData(plotData);

      plotDataRepository.save(plotData);
      configurationRepository.save(configuration);
      plotRepository.save(editedPlot);
    }
    catch (Exception e)
    {
      throw new PlotNotfoundException();
    }
    return editedPlot;
  }

  public static String checkSensorFunctions(SensorDto sensor)
  {
    if (!(SensorFunction.TEMPERATURE.equals(SensorFunction.valueOf(sensor.getSensorFunction())) ||
                  SensorFunction.HUMIDITY.equals(SensorFunction.valueOf(sensor.getSensorFunction())) ||
                  SensorFunction.WATER.equals(SensorFunction.valueOf(sensor.getSensorFunction()))))
    {
      throw new SensorNotfoundException();
    }
    return sensor.getSensorFunction();
  }
}
