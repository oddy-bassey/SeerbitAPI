package com.fcmb.SeerbitAPI.service;

import com.fcmb.SeerbitAPI.dto.BaseDtoEntity;
import org.springframework.http.ResponseEntity;

public interface SeerbitAPIService {

    String getValidToken();
    ResponseEntity<?> authenticate();
    ResponseEntity<?> accountPayout(BaseDtoEntity requestBody);
    ResponseEntity<?> walletPayout(BaseDtoEntity requestBody);
    ResponseEntity<?> cashPickUp(BaseDtoEntity requestBody);
}
