package com.fcmb.SeerbitAPI.service;

import com.fcmb.SeerbitAPI.dto.AuthCredential;
import com.fcmb.SeerbitAPI.dto.BaseDtoEntity;
import com.fcmb.SeerbitAPI.dto.Response;
import org.springframework.http.ResponseEntity;

public interface SeerbitAPIService {

    String getValidToken();
    ResponseEntity<AuthCredential> authenticate();
    ResponseEntity<Response> payout(BaseDtoEntity requestBody);
    ResponseEntity<Response> cashPickUp(BaseDtoEntity requestBody);
}
