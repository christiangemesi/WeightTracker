package com.example.demo.repositories;

import com.example.demo.model.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightEntryRepository extends JpaRepository<WeightEntry,Long> {


    /*
    @Query(
        "SELECT report"
            + " FROM "
            + "Report report"
            + " WHERE "
            + "report.incident.id = :#{#path.incidentId}"
            + " AND "
            + "report.id = :id"
    )
    @Override
    Optional<Report> findByPath(@Param("path") ReportPath path, @Param("id") Long id);
     */

}
