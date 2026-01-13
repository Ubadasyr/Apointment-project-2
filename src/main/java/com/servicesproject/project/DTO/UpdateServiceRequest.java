package com.servicesproject.project.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateServiceRequest {


    @NotBlank
    private String name;
    @NotNull
    @Min(1) private Integer durationMinutes;
    @NotNull
    @Positive
    private Double price;

    @NotNull
    private List<Long> staffIds;



}




