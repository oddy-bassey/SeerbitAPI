package com.fcmb.SeerbitAPI.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @NotNull(message = "amount is required!")
    private BigDecimal amount;
    private String description;
    private String reason;
    @NotNull(message = "currency is required!")
    private Currency currency;
    @NotEmpty(message = "country is required!")
    private String country;
    private String secretquestion;
    private String secretanswer;
}
