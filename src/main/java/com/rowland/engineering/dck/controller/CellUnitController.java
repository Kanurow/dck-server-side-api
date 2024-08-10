package com.rowland.engineering.dck.controller;

import com.rowland.engineering.dck.dto.CreateCellUnitRequest;
import com.rowland.engineering.dck.dto.GetAllCellUnitsResponse;
import com.rowland.engineering.dck.service.ICellUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/cell")
@Tag(name = "Cell Unit")
@RequiredArgsConstructor
public class CellUnitController {

    @Autowired
    private final ICellUnitService cellService;

    @Operation(
            description = "Returns list of all cell units - Must have the role of an admin, pastor or cell leader",
            summary = "Returns all cells"
    )
    @GetMapping("/all-cells")
    @Secured({"PASTOR", "ADMIN", "CELL_LEADER"})
    public List<GetAllCellUnitsResponse> getAllCellUnits() {
            return cellService.getAllCellUnits();
    }

    @Operation(
            description = "Adds a member to a cell unit by accepting cell unit id and users id - Must have the role of an admin, pastor or cell leader",
            summary = "Adds a user to a cell unit"
    )
    @Secured({"PASTOR", "ADMIN", "CELL_LEADER"})
    @PostMapping("/add-cell-member/{cellUnitId}/{userId}")
    public ResponseEntity<String> addCellMember(
            @PathVariable UUID cellUnitId,
            @PathVariable UUID userId) {
        try{
            cellService.addCellMember(cellUnitId, userId);
            return ResponseEntity.ok("New member added successfully!");
        }catch(Exception re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());
        }
    }

    @Operation(
            description = "Removes a cell member from cell unit - Must have the role of an admin, pastor or cell leader",
            summary = "Removes a cell member from cell"
    )
    @DeleteMapping("/remove-cell-member/{cellUnitId}/{userId}")
    @Secured({"PASTOR", "ADMIN", "CELL_LEADER"})
    public ResponseEntity<String> removeCellMember(
            @PathVariable UUID cellUnitId,
            @PathVariable UUID userId) {
        try{
            cellService.removeCellMember(cellUnitId, userId);
            return ResponseEntity.ok("Removed successfully!");
        }catch(Exception re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());
        }
    }


    @Operation(
            description = "Creates a new cell unit - Must have the role of an admin or pastor",
            summary = "Creates cell unit"
    )
    @PostMapping("/create-cell-unit")
    @Secured({"PASTOR", "ADMIN"})
    public ResponseEntity<String> createCellUnit(@RequestBody CreateCellUnitRequest cellUnitRequest) {
        try{
            cellService.createCellUnit(cellUnitRequest);
            return ResponseEntity.ok("Cell unit created successfully!");
        }catch(Exception re){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(re.getMessage());
        }
    }

    @Operation(
            description = "Allows upload of csv file to cell unit table through a csv file - Must have the role of an admin",
            summary = "Allows cell unit csv data upload"
    )
    @Secured({"ADMIN"})
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadCellUnitCoOrdinates(
            @RequestPart("file") MultipartFile file
    ) throws IOException {
       return ResponseEntity.ok(cellService.uploadCellUnitCoOrdinates(file));
    }

}
