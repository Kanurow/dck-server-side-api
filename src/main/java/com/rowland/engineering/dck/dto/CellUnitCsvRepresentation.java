package com.rowland.engineering.dck.dto;

import com.opencsv.bean.CsvBindByName;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CellUnitCsvRepresentation {

    @CsvBindByName(column = "id")
    private UUID cellId;
    @CsvBindByName(column = "name")
    private String cellName;
    @CsvBindByName(column = "longitude")
    private String cellLongitude;
    @CsvBindByName(column = "latitude")
    private String cellLatitude;


    @CsvBindByName(column = "leader_id")
    private UUID cellLeaderId;
    @CsvBindByName(column = "leaders_phone_number")
    private String cellLeadersPhoneNumber;
    @CsvBindByName(column = "address")
    private String cellAddress;
}
