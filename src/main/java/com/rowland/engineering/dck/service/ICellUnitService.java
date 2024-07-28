package com.rowland.engineering.dck.service;

import com.rowland.engineering.dck.dto.CreateCellUnitRequest;
import com.rowland.engineering.dck.dto.GetAllCellUnitsResponse;
import com.rowland.engineering.dck.model.CellUnit;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ICellUnitService {

    List<GetAllCellUnitsResponse> getAllCellUnits();

    void addCellMember(UUID cellUnitId, UUID userId);
    void removeCellMember(UUID cellUnitId, UUID userId);

    void createCellUnit(CreateCellUnitRequest cellUnitRequest);

    String uploadCellUnitCoOrdinates(MultipartFile file) throws IOException;
}
