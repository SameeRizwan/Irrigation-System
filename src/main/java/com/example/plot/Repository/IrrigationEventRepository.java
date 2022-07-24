package com.example.plot.Repository;

import com.example.plot.Model.IrrigationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IrrigationEventRepository extends JpaRepository<IrrigationEvent, Integer>
{
  @Query("SELECT i FROM IrrigationEvent i WHERE i.plot.id = :id")
  IrrigationEvent findByPlotId(@Param("id") int id);

}
