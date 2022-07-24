package com.example.plot.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "sensor")
@AllArgsConstructor
@NoArgsConstructor
public class Sensor
{
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "location", nullable = false)
  private String location;

  @Column(name = "sensorFunction", nullable = false)
  private String sensorFunction;

  @Column(name = "status", nullable = false)
  private boolean status;

  public Sensor(String location, String sensorFunction, boolean status)
  {
    this.location = location;
    this.sensorFunction = sensorFunction;
    this.status = status;
  }
}

