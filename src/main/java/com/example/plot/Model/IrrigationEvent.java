package com.example.plot.Model;

import com.example.plot.Enums.IrrigationEventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IrrigationEvent
{
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "dateTime", nullable = false)
  private LocalDateTime dateTime;

  @Column(name = "irrigationEventStatus", nullable = false)
  private IrrigationEventStatus status;

  @Column(name = "retry", nullable = false)
  private int retry;

  @OneToOne
  private Plot plot;

  public IrrigationEvent(LocalDateTime dateTime, Plot plot, int retry)
  {
    this.dateTime = dateTime;
    this.plot = plot;
    this.retry = retry;
  }
}
