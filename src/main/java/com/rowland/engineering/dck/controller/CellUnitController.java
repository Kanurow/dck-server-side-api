package com.rowland.engineering.dck.controller;

import com.rowland.engineering.dck.dto.CreateCellUnitRequest;
import com.rowland.engineering.dck.model.CellUnit;
import com.rowland.engineering.dck.service.ICellUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/cell")
@RequiredArgsConstructor
@Tag(name = "Cell Unit")
public class CellUnitController {

    private final ICellUnitService cellService;


    @Operation(
            description = "Returns list of all cell units",
            summary = "Returns all cells"
    )
    @GetMapping("/all-cells")
    public List<CellUnit> getAllCellUnits() {
            return cellService.getAllCellUnits();
    }

    @Operation(
            description = "Adds a member to a cell unit",
            summary = "Adds a user to a cell unit"
    )
    @PostMapping("/add-cell-member/{cellUnitId}/{userId}")
    public ResponseEntity<String> addCellMember(
            @PathVariable long cellUnitId,
            @PathVariable long userId) {
        try{
            cellService.addCellMember(cellUnitId, userId);
            return ResponseEntity.ok("New member added successfully!");
        }catch(Exception re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());
        }
    }

    @Operation(
            description = "Removes a cell member from cell",
            summary = "Removes a cell member from cell"
    )
    @DeleteMapping("/remove-cell-member/{cellUnitId}/{userId}")
    public ResponseEntity<String> removeCellMember(
            @PathVariable long cellUnitId,
            @PathVariable long userId) {
        try{
            cellService.removeCellMember(cellUnitId, userId);
            return ResponseEntity.ok("Removed successfully!");
        }catch(Exception re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());
        }
    }


    @Operation(
            description = "Creates a new cell unit",
            summary = "Creates cell unit"
    )
    @PostMapping("/create-cell-unit")
    public ResponseEntity<String> createCellUnit(@RequestBody CreateCellUnitRequest cellUnitRequest) {
        try{
            cellService.createCellUnit(cellUnitRequest);
            return ResponseEntity.ok("Cell unit created successfully!");
        }catch(Exception re){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(re.getMessage());
        }
    }

}
