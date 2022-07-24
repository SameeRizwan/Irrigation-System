package com.example.plot.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "plot")
@AllArgsConstructor
@NoArgsConstructor
public class Plot
{
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "location", nullable = false)
  private String location;

  @Column(name = "area", nullable = false)
  private String area;

  @Column(name = "ownedBy", nullable = false)
  private String ownedBy;

  @OneToOne
  @JoinColumn(name = "plotData_id",referencedColumnName = "id")
  private PlotData plotData;

}
