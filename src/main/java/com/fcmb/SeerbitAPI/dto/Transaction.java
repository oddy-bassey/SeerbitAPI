package com.fcmb.SeerbitAPI.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transaction {
    @NotBlank(message = "reference must be provided!")
    private String reference;
    private String linkingreference;
}
