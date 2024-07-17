package com.rowland.engineering.dck.dto;

import lombok.Data;

@Data
public class CreateCellUnitRequest {
    private String cellName;
    private String cellLongitude;
    private String cellLatitude;
    private Long cellLeaderId;
    private String cellAddress;
}
