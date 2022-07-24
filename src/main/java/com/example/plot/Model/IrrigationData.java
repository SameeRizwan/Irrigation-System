package com.example.plot.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "irrigation_data")
@AllArgsConstructor
@NoArgsConstructor
public class IrrigationData
{
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "previous_irrigation_time", nullable = false)
  private LocalDateTime previousIrrigationTime;

  @Column(name = "next_irrigation_time", nullable = false)
  private LocalDateTime nextIrrigationTime;

}
