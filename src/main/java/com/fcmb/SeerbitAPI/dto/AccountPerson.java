package com.fcmb.SeerbitAPI.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountPerson {

    @NotBlank(message = "name cannot be empty!")
    private String name;
    @NotBlank(message = "address cannot be empty!")
    private String address;
    private String accountnumber;
    private String mobile;
    private String country;
    private String idtype;
    private String idnumber;
    private String idexpiry;
}
