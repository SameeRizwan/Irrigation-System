package com.example.plot.Repository;

import com.example.plot.Model.Alert;
import com.example.plot.Model.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Integer>
{
  @Query("SELECT i FROM Plot i WHERE i.ownedBy = :owner")
  Plot findByOwnerBy(@Param("owner") String owner);
}
