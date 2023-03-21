package com.fcmb.SeerbitAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCredential{


    private String access_token;

    private Long expires_in;

    private Long requested_at;
}
