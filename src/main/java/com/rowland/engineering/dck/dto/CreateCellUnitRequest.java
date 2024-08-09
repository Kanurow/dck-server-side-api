package com.rowland.engineering.dck.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCellUnitRequest {
    private String cellName;
    private int cellLongitude;
    private int cellLatitude;
    private UUID cellLeaderId;
    private String cellAddress;
}
