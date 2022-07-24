package com.example.plot;


import com.example.plot.Dto.PlotDto;
import com.example.plot.Model.Configuration;
import com.example.plot.Model.Crop;
import com.example.plot.Model.IrrigationData;
import com.example.plot.Model.Plot;
import com.example.plot.Model.PlotData;
import com.example.plot.Model.Sensor;
import com.example.plot.Repository.PlotRepository;
import com.example.plot.Service.PlotServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
public class PlotControllerIntegerationTest
{

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @MockBean
  PlotServiceImpl plotServiceImpl;

  @MockBean
  PlotRepository plotRepository;

  private static ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  public void setup()
  {
    mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void givenPlot_whenGetPlot_thenStatus200()
          throws Exception
  {
    ArrayList<Plot> list = new ArrayList<>();
    list.add(buildTestData());
    when(plotServiceImpl.getAllPlots()).thenReturn(list);

    mvc.perform(MockMvcRequestBuilders.get("/plot/all")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  public void givenPlot_whenAddPlotIsCalled_thenStatus200()
          throws Exception
  {
    when(plotServiceImpl.addPlot(ArgumentMatchers.any())).thenReturn(buildTestData());
    String json = mapper.writeValueAsString(buildTestData());

    mvc.perform(post("/plot/add").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
            .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.location", Matchers.equalTo("Karachi")));
  }

  @Test
  public void givenPlot_whenAddConfigIsCalled_thenStatus200()
          throws Exception
  {
    when(plotServiceImpl.configurePlot(ArgumentMatchers.any())).thenReturn(buildTestData());
    String json = mapper.writeValueAsString(buildTestData());
    mvc.perform(post("/plot/configure").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
            .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.location", Matchers.equalTo("Karachi")));
  }

  @Test
  public void givenPlot_whenEditPlotIsCalled_thenPlotIsUpdated() throws Exception {
    when(plotServiceImpl.editPlot(ArgumentMatchers.any())).thenReturn(buildTestData());
    String json = mapper.writeValueAsString(buildTestData());
    mvc.perform(post("/plot/edit").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
            .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(jsonPath("$.location", Matchers.equalTo("Karachi")));
  }

  private Plot buildTestData()
  {
    Plot plot = new Plot();
    plot.setLocation("Karachi");
    plot.setOwnedBy("Ali");
    plot.setArea("100");

    PlotData plotData = new PlotData();
    plotData.setCrop(new Crop("Rice", 25));

    Configuration configuration = new Configuration();
    configuration.setHumidity(5);
    configuration.setTemperature(30);
    configuration.setWaterThreshold((plotData.getCrop().getWaterRequired() * Integer.valueOf(plot.getArea())) + 5);
    configuration.setIrrigationTimeInterval(5);


    ArrayList<Sensor> list = new ArrayList<>();
    list.add(new Sensor("Lahore", "WATER", true));

    plotData.setConfiguration(configuration);
    plotData.setSensor(list);
    plot.setPlotData(plotData);

    return plot;
  }

}

