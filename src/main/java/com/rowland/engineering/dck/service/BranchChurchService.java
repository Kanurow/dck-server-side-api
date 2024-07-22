package com.rowland.engineering.dck.service;

import com.rowland.engineering.dck.model.BranchChurch;
import com.rowland.engineering.dck.repository.BranchChurchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchChurchService implements IBranchChurchService{
    private final BranchChurchRepository branchChurchRepository;
    @Override
    public List<BranchChurch> getAllChurchBranch() {
        return branchChurchRepository.findAll();
    }
}
