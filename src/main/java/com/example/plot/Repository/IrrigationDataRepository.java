package com.example.plot.Repository;

import com.example.plot.Model.IrrigationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrrigationDataRepository extends JpaRepository<IrrigationData, Integer>
{

}
