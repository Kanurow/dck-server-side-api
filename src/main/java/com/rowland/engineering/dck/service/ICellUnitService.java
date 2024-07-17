package com.rowland.engineering.dck.service;

import com.rowland.engineering.dck.dto.CreateCellUnitRequest;
import com.rowland.engineering.dck.model.CellUnit;

import java.util.List;

public interface ICellUnitService {

    List<CellUnit> getAllCellUnits();

    void addCellMember(long cellUnitId, long userId);
    void removeCellMember(long cellUnitId, long userId);

    void createCellUnit(CreateCellUnitRequest cellUnitRequest);
}
