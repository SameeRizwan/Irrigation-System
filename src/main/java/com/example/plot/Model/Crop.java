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
@Table(name = "crop")
@AllArgsConstructor
@NoArgsConstructor
public class Crop
{
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "water_required", nullable = false)
  private int waterRequired;

  public Crop(String name, int waterRequired)
  {
    this.name = name;
    this.waterRequired = waterRequired;
  }
}
