package com.fcmb.SeerbitAPI.controller;

import com.fcmb.SeerbitAPI.dto.Operation;
import com.fcmb.SeerbitAPI.dto.RequestDtoEntity;
import com.fcmb.SeerbitAPI.service.SeerbitAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/seerbit/api/v1")
@RestController
public class SeerBitAPIRestController {

    private final SeerbitAPIService seerbitAPIService;

    @PostMapping("/account/payout")
    public ResponseEntity<?> payout (@RequestBody RequestDtoEntity body) {
        Optional<Operation> optional = Optional.ofNullable(body.getSource().getOperation());
        optional.orElseThrow(RuntimeException::new);

        return seerbitAPIService.payout(body);
    }

    @PostMapping("/payout/create")
    public ResponseEntity<?> cashPickUp (@RequestBody RequestDtoEntity body) {

        return seerbitAPIService.cashPickUp(body);
    }
}
