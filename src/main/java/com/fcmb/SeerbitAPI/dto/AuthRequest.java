package com.fcmb.SeerbitAPI.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest extends BaseDtoEntity{

    @NotEmpty(message = "country is grant_type!")
    private String grant_type;

    @NotEmpty(message = "country is client_id!")
    private String client_id;

    @NotEmpty(message = "country is client_secret!")
    private String client_secret;
}
