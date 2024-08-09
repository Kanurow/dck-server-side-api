package com.rowland.engineering.dck.repository;

import com.rowland.engineering.dck.model.CellUnit;
import com.rowland.engineering.dck.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CellUnitRepository extends JpaRepository<CellUnit, UUID> {

}
