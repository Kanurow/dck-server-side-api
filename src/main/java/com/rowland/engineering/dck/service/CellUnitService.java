package com.rowland.engineering.dck.service;

import com.rowland.engineering.dck.dto.CreateCellUnitRequest;
import com.rowland.engineering.dck.exception.EntityNotFoundException;
import com.rowland.engineering.dck.model.CellUnit;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.repository.CellUnitRepository;
import com.rowland.engineering.dck.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CellUnitService implements ICellUnitService {
    private final CellUnitRepository cellUnitRepository;
    private final UserRepository userRepository;


    @Value("${app.max-cell-size}")
    private int MAX_CELL_MEMBERS;

    @Override
    public List<CellUnit> getAllCellUnits() {
        return cellUnitRepository.findAll();
    }

    @Override
    public void addCellMember(long cellUnitId, long userId) {
        CellUnit cellUnit = cellUnitRepository.findById(cellUnitId).orElseThrow(() -> new EntityNotFoundException("CellUnit not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (cellUnit.getMembers().size() >= MAX_CELL_MEMBERS) {
            throw new IllegalStateException("Cell unit is full! 30 maximum members in a cell.");
        }
        cellUnit.getMembers().add(user);
        user.setCellUnitId(cellUnit.getId());
        userRepository.save(user);
        cellUnitRepository.save(cellUnit);

    }
    @Override
    public void removeCellMember(long cellUnitId, long userId) {
        CellUnit cellUnit = cellUnitRepository.findById(cellUnitId)
                .orElseThrow(() -> new EntityNotFoundException("CellUnit not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        boolean removed = cellUnit.getMembers().remove(user);
        if (!removed) {
            throw new IllegalArgumentException("User not found in cell unit");
        }
        user.setCellUnitId(null);
        cellUnitRepository.save(cellUnit);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void createCellUnit(CreateCellUnitRequest cellUnitDto) {
        User cellLeader = userRepository.findById(cellUnitDto.getCellLeaderId())
                .orElseThrow(() -> new EntityNotFoundException("Cell leader not found"));

        CellUnit cellUnit = CellUnit.builder()
                .name(cellUnitDto.getCellName())
                .longitude(cellUnitDto.getCellLongitude())
                .latitude(cellUnitDto.getCellLatitude())
                .leaderId(cellLeader.getId())
                .address(cellUnitDto.getCellAddress())
                .leadersPhoneNumber(cellLeader.getPhoneNumber())
                .build();
        cellUnitRepository.save(cellUnit);
    }

}
