package com.example.plot.Repository;

import com.example.plot.Model.PlotData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotDataRepository extends JpaRepository<PlotData, Integer>
{

}
