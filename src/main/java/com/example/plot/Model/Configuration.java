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
@Table(name = "configuration")
@AllArgsConstructor
@NoArgsConstructor
public class Configuration
{
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "temperature", nullable = false)
  private int temperature;

  @Column(name = "irrigationTimeInterval", nullable = false)
  private int irrigationTimeInterval;

  @Column(name = "humidity", nullable = false)
  private int humidity;

  @Column(name = "waterThreshold", nullable = false)
  private int waterThreshold;

}
