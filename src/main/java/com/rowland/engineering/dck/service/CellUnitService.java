package com.rowland.engineering.dck.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.rowland.engineering.dck.dto.CellUnitCsvRepresentation;
import com.rowland.engineering.dck.dto.CreateCellUnitRequest;
import com.rowland.engineering.dck.dto.GetAllCellUnitsResponse;
import com.rowland.engineering.dck.dto.UserSummary;
import com.rowland.engineering.dck.exception.CellUnitNotFoundException;
import com.rowland.engineering.dck.exception.UserNotFoundException;
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
    public List<GetAllCellUnitsResponse> getAllCellUnits() {
        List<CellUnit> cellUnits = cellUnitRepository.findAll();
        return cellUnits.stream().map(cellUnit -> {
            GetAllCellUnitsResponse cellUnitsResponse = new GetAllCellUnitsResponse();
            cellUnitsResponse.setId(cellUnit.getId());
            cellUnitsResponse.setName(cellUnit.getName());
            cellUnitsResponse.setAddress(cellUnit.getAddress());
            cellUnitsResponse.setLeaderId(cellUnit.getLeaderId());
            cellUnitsResponse.setLatitude(cellUnit.getLatitude());
            cellUnitsResponse.setLongitude(cellUnit.getLongitude());
            cellUnitsResponse.setLeadersPhoneNumber(cellUnit.getLeadersPhoneNumber());
            cellUnitsResponse.setMembers(cellUnit.getMembers().stream().map(
                    member -> {
                        UserSummary userSummary = new UserSummary();
                        userSummary.setEmail(member.getEmail());
                        userSummary.setFirstName(member.getFirstName());
                        userSummary.setLastName(member.getLastName());
                        userSummary.setPhoneNumber(member.getPhoneNumber());
                        return userSummary;
                    }
            ).collect(Collectors.toSet()));
            return cellUnitsResponse;
        }).collect(Collectors.toList());

    }

    @Transactional
    @Override
    public void addCellMember(UUID cellUnitId, UUID userId) {
        CellUnit cellUnit = cellUnitRepository.findById(cellUnitId).orElseThrow(() -> new CellUnitNotFoundException("CellUnit not found! cellId = "+ cellUnitId));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found! userId = "+userId));
        if (cellUnit.getMembers().size() >= MAX_CELL_MEMBERS) {
            throw new IllegalStateException("Cell unit is full! 30 maximum members in a cell.");
        }
        cellUnit.getMembers().add(user);
        user.setCellUnitId(cellUnit.getId());
        userRepository.save(user);
        cellUnitRepository.save(cellUnit);
    }
    @Transactional
    @Override
    public void removeCellMember(UUID cellUnitId, UUID userId) {
        CellUnit cellUnit = cellUnitRepository.findById(cellUnitId)
                .orElseThrow(() -> new CellUnitNotFoundException("CellUnit not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

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
                .orElseThrow(() -> new UserNotFoundException("Cell leader not found"));

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
