package com.rowland.engineering.dck.repository;

import com.rowland.engineering.dck.model.CellUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellUnitRepository extends JpaRepository<CellUnit, Long> {
}
