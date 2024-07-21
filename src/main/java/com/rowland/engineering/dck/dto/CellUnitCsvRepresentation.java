package com.rowland.engineering.dck.dto;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CellUnitCsvRepresentation {
    @CsvBindByName(column = "id")
    private UUID cellId;
    @CsvBindByName(column = "name")
    private String cellName;
    @CsvBindByName(column = "longitude")
    private String cellLongitude;
    @CsvBindByName(column = "latitude")
    private String cellLatitude;


    @CsvBindByName(column = "leaderId")
    private UUID cellLeaderId;
    @CsvBindByName(column = "leadersPhoneNumber")
    private String cellLeadersPhoneNumber;
    @CsvBindByName(column = "address")
    private String cellAddress;
}
