package com.example.plot;

import com.example.plot.Model.Plot;
import com.example.plot.Repository.PlotRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlotRepositoryUnitTest extends PlotApplicationTests
{

  @Autowired
  PlotRepository plotRepository;

  private Plot buildTestData()
  {
    Plot plot = new Plot();
    plot.setLocation("Karachi");
    plot.setOwnedBy("Ali");
    plot.setArea("100");
    return plot;
  }

  @Test
  @Order(1)
  @Rollback(value = false)
  public void savePlotTest()
  {
    Plot plot = buildTestData();
    plotRepository.save(plot);
    Assertions.assertThat(plot.getId()).isGreaterThan(0);
  }

  @Test
  @Order(2)
  public void getPlotTest()
  {
    List<Plot> plots = plotRepository.findAll();
    Plot plot = plotRepository.findById(plots.get(0).getId()).get();
    Assertions.assertThat(plot.getId()).isEqualTo(plots.get(0).getId());
  }

  @Test
  @Order(3)
  public void getListOfPlotsTest()
  {
    List<Plot> plots = plotRepository.findAll();
    Assertions.assertThat(plots.size()).isGreaterThan(0);

  }

  @Test
  @Order(4)
  @Rollback(value = false)
  public void updatePlotTest()
  {
    List<Plot> plots = plotRepository.findAll();
    Plot plot = plotRepository.findById(plots.get(0).getId()).get();
    plot.setLocation("Ahmad Pur");
    Plot plotUpdated = plotRepository.save(plot);
    Assertions.assertThat(plotUpdated.getLocation()).isEqualTo("Ahmad Pur");

  }
}

