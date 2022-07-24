package com.example.plot.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "alert")
@AllArgsConstructor
@NoArgsConstructor
public class Alert
{

  @Id
  @GeneratedValue
  private int id;

  @Column(name = "dateTime", nullable = false)
  private LocalDateTime dateTime;

  @OneToOne
  private Plot plot;

  @ManyToOne
  private IrrigationEvent event;

}
