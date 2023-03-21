package com.fcmb.SeerbitAPI.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcmb.SeerbitAPI.dto.AuthCredential;
import com.fcmb.SeerbitAPI.dto.BaseDtoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:env.properties")
@Service
public class SeerbitAPIServiceImpl implements SeerbitAPIService {

    @Value("${seerbit.baseUrl}")
    private String BASE_URL;

    @Value("${seerbit.grantType}")
    private String GRANT_TYPE;

    @Value("${seerbit.clientId}")
    private String CLIENT_ID;

    @Value("${seerbit.clientSecret}")
    private String CLIENT_SECRETE;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private AuthCredential authCredential;

    @Override
    public synchronized String getValidToken() {
        try {
            if (authCredential == null) {
                log.info("Requesting token");
                authCredential = objectMapper.readValue(authenticate().getBody().toString(), AuthCredential.class);
                authCredential.setRequested_at(System.currentTimeMillis());
            }
            long expTime = (System.currentTimeMillis() - authCredential.getRequested_at());
            if (expTime >= authCredential.getExpires_in()) {
                log.info("Token expired, requesting new token");
                authCredential = objectMapper.readValue(authenticate().getBody().toString(), AuthCredential.class);
                authCredential.setRequested_at(System.currentTimeMillis());
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
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

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRETE);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpEntity =  httpEntity = new HttpEntity<>(params, httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<?> payout(BaseDtoEntity requestBody) {

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

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
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

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
    }
}
