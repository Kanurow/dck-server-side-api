package com.rowland.engineering.dck.controller;

import com.rowland.engineering.dck.model.BranchChurch;
import com.rowland.engineering.dck.service.IBranchChurchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/branch")
@Tag(name = "Branch Churches")
@RequiredArgsConstructor
public class BranchChurchController {
    private final IBranchChurchService branchChurchService;

    @Operation(
            description = "Returns list of all dck branch satellite churches",
            summary = "Returns all satellite churches"
    )
    @GetMapping("/all-branches")
    public List<BranchChurch> getAllChurchBranch() {
        return branchChurchService.getAllChurchBranch();
    }
}