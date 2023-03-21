package com.fcmb.SeerbitAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcmb.SeerbitAPI.dto.Operation;
import com.fcmb.SeerbitAPI.dto.RequestDtoEntity;
import com.fcmb.SeerbitAPI.sample_data.SampleData;
import com.fcmb.SeerbitAPI.service.SeerbitAPIService;
import com.github.javafaker.Faker;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class SeerbitControllerIntegerationTests {

    private ObjectMapper objectMapper;
    private RequestDtoEntity requestBody;
    private Faker faker;
    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setup () {
        objectMapper = new ObjectMapper();
        faker = new Faker(new Locale("en-NG"));
        try {
            requestBody = objectMapper.readValue(SampleData.jsonData, RequestDtoEntity.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Cash payout controller test")
    @Test
    void cashPayout() {
        try {
            requestBody.getTransaction().setReference(faker.bothify("########??", true));
            requestBody.getOrder().setAmount(BigDecimal.valueOf(40.00));
            requestBody.getSource().setOperation(Operation.acct_payout);

            mockMvc.perform(post("/seerbit/api/v1/account/payout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(requestBody)))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code", Matchers.is("00")))
                    .andExpect(jsonPath("$.message", Matchers.is("Successful")))
                    .andExpect(jsonPath("$.transaction.reference", Matchers.is(requestBody.getTransaction().getReference())));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @DisplayName("Wallet payout controller test")
    @Test
    void walletPayout() {
        try {
            requestBody.getTransaction().setReference(faker.bothify("########??", true));
            requestBody.getOrder().setAmount(BigDecimal.valueOf(80.00));
            requestBody.getSource().setOperation(Operation.wallet_payout);

            mockMvc.perform(post("/seerbit/api/v1/account/payout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(requestBody)))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code", Matchers.is("00")))
                    .andExpect(jsonPath("$.message", Matchers.is("Successful")))
                    .andExpect(jsonPath("$.transaction.reference", Matchers.is(requestBody.getTransaction().getReference())));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @DisplayName("Wallet payout controller test (with already existing reference)")
    @Test
    void walletPayoutWithExistingReference() {
        try {
            requestBody.getOrder().setAmount(BigDecimal.valueOf(80.00));
            requestBody.getSource().setOperation(Operation.wallet_payout);

            mockMvc.perform(post("/seerbit/api/v1/account/payout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(requestBody)))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code", Matchers.is("S14")))
                    .andExpect(jsonPath("$.message", Matchers.is("Transaction reference must be unique")));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @DisplayName("Cash pickup controller test")
    @Test
    void cashPickUp() {
        try {
            requestBody.getTransaction().setReference(faker.bothify("########??", true));
            requestBody.getOrder().setAmount(BigDecimal.valueOf(10.00));
            requestBody.getSource().setOperation(Operation.wallet_payout);

            mockMvc.perform(post("/seerbit/api/v1/payout/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(requestBody)))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code", Matchers.is("00")))
                    .andExpect(jsonPath("$.message", Matchers.is("Successful")))
                    .andExpect(jsonPath("$.transaction.reference", Matchers.is(requestBody.getTransaction().getReference())));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
