package com.fcmb.SeerbitAPI.service;

import com.fcmb.SeerbitAPI.dto.AuthCredential;
import com.fcmb.SeerbitAPI.dto.AuthRequest;
import com.fcmb.SeerbitAPI.dto.BaseDtoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:env.properties")
@Service
public class SeerbitAPIServiceImpl implements SeerbitAPIService {

    @Value("${seerbit.baseUrl}")
    private final String BASE_URL;

    @Value("${seerbit.grantType}")
    private final String GRANT_TYPE;

    @Value("${seerbit.clientId}")
    private final String CLIENT_ID;

    @Value("${seerbit.clientSecret}")
    private final String CLIENT_SECRETE;

    private final RestTemplate restTemplate;

    private AuthCredential authCredential;

    @Override
    public synchronized String getValidToken() {
        if (authCredential == null) {
            authCredential = (AuthCredential) authenticate().getBody();
        }
        if ((System.currentTimeMillis()-authCredential.getRequested_at()) >= authCredential.getExpires_in()) {
            authCredential = (AuthCredential) authenticate().getBody();
        }
        return authCredential.getAccess_token();
    }

    @Override
    public ResponseEntity<?> authenticate() {

        URI uri = null;
        try {
            uri = new URI(BASE_URL.concat("/auth"));
        } catch (URISyntaxException exception) {
            log.error(exception.getMessage(), exception);
            throw new RuntimeException(exception.getMessage());
        }
        AuthRequest requestBody = AuthRequest.builder()
                .grant_type(GRANT_TYPE)
                .client_id(CLIENT_ID)
                .client_secret(CLIENT_SECRETE)
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<BaseDtoEntity> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, AuthCredential.class);
    }

    @Override
    public ResponseEntity<?> accountPayout(BaseDtoEntity requestBody) {

        URI uri = null;
        try {
            uri = new URI(BASE_URL.concat("/account/payout"));
        } catch (URISyntaxException exception) {
            log.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + getValidToken());

        HttpEntity<BaseDtoEntity> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Object.class);
    }

    @Override
    public ResponseEntity<?> walletPayout(BaseDtoEntity requestBody) {

        URI uri = null;
        try {
            uri = new URI(BASE_URL.concat("/account/payout"));
        } catch (URISyntaxException exception) {
            log.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + getValidToken());

        HttpEntity<BaseDtoEntity> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Object.class);
    }

    @Override
    public ResponseEntity<?> cashPickUp(BaseDtoEntity requestBody) {

        URI uri = null;
        try {
            uri = new URI(BASE_URL.concat("/payout/create"));
        } catch (URISyntaxException exception) {
            log.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + getValidToken());

        HttpEntity<BaseDtoEntity> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Object.class);
    }
}
