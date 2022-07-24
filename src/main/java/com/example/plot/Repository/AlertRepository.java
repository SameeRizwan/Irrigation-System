package com.example.plot.Repository;

import com.example.plot.Model.Alert;
import com.example.plot.Model.IrrigationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer>
{
  @Query("SELECT i FROM Alert i WHERE i.event.id = :id")
  Alert findByEventId(@Param("id") int id);
}
