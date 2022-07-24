package com.example.plot.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "plot_data")
@AllArgsConstructor
@NoArgsConstructor
public class PlotData
{
  @Id
  @GeneratedValue
  private int id;

  @OneToOne
  @JoinColumn(name = "crop_id")
  private Crop crop;

  @OneToOne
  @JoinColumn(name = "configuration_id")
  private Configuration configuration;

  @OneToOne
  @JoinColumn(name = "irrigationData_id")
  private IrrigationData irrigationData;

  @OneToMany
  @JoinColumn(name = "sensor_id")
  private List<Sensor> sensor;

}
