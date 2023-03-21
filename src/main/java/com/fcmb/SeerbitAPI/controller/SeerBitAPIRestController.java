package com.fcmb.SeerbitAPI.controller;

import com.fcmb.SeerbitAPI.dto.Operation;
import com.fcmb.SeerbitAPI.dto.RequestDtoEntity;
import com.fcmb.SeerbitAPI.dto.Response;
import com.fcmb.SeerbitAPI.service.SeerbitAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/seerbit/api/v1")
@RestController
public class SeerBitAPIRestController {

    private final SeerbitAPIService seerbitAPIService;

    @GetMapping
    public ResponseEntity<String> welcome () {
        return new ResponseEntity<>("SEERBIT Remittance Payout API Integration.", HttpStatus.OK);
    }

//    @ApiOperation(value = "Enables funds transfer to bank accounts or wallets in Nigeria")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
//            @ApiResponse(code = 500, message = "Internal Server error") })
    @PostMapping("/account/payout")
    public ResponseEntity<Response> payout (@RequestBody RequestDtoEntity body) {
        Optional<Operation> optional = Optional.ofNullable(body.getSource().getOperation());
        optional.orElseThrow(RuntimeException::new);

        return seerbitAPIService.payout(body);
    }

//    @ApiOperation(value = "Enables creating payout transaction for cash pickup")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
//            @ApiResponse(code = 500, message = "Internal Server error") })
    @PostMapping("/payout/create")
    public ResponseEntity<Response> cashPickUp (@RequestBody RequestDtoEntity body) {

        return seerbitAPIService.cashPickUp(body);
    }
}
