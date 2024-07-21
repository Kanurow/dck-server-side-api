package com.rowland.engineering.dck.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.rowland.engineering.dck.dto.CellUnitCsvRepresentation;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


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

    @Transactional
    @Override
    public void addCellMember(UUID cellUnitId, UUID userId) {
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
    public void removeCellMember(UUID cellUnitId, UUID userId) {
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

    @Override
    @Transactional
    public String uploadCellUnitCoOrdinates(MultipartFile file) throws IOException {
        Set<CellUnit> cellUnits = parseCSV(file);
        cellUnitRepository.saveAll(cellUnits);
        return "Cell unit csv uploaded Successfully";
    }

    private Set<CellUnit> parseCSV(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<CellUnitCsvRepresentation> headerMappingStrategy =
                    new HeaderColumnNameMappingStrategy<>();

            headerMappingStrategy.setType(CellUnitCsvRepresentation.class);
            CsvToBean<CellUnitCsvRepresentation> csvToBean =
                    new CsvToBeanBuilder<CellUnitCsvRepresentation>(reader)
                            .withMappingStrategy(headerMappingStrategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> CellUnit.builder()
                            .id(csvLine.getCellId())
                            .name(csvLine.getCellName())
                            .leadersPhoneNumber(csvLine.getCellLeadersPhoneNumber())
                            .leaderId(csvLine.getCellLeaderId())
                            .address(csvLine.getCellAddress())
                            .latitude(csvLine.getCellLatitude())
                            .longitude(csvLine.getCellLongitude())
                            .build()
                    )
                    .collect(Collectors.toSet());

        }
    }

}
