package com.ironhack.week9_lab.Dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {
    private String name;

    private LocalDate dateOfBirth;

    private Integer admittedBy; // Employee ID
}
