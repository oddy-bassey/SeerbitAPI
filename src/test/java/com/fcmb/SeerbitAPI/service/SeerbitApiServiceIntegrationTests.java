package com.fcmb.SeerbitAPI.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcmb.SeerbitAPI.dto.AuthCredential;
import com.fcmb.SeerbitAPI.dto.Operation;
import com.fcmb.SeerbitAPI.dto.RequestDtoEntity;
import com.fcmb.SeerbitAPI.dto.Response;
import com.fcmb.SeerbitAPI.sample_data.SampleData;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SeerbitApiServiceIntegrationTests {
    private ObjectMapper objectMapper;
	private RequestDtoEntity requestBody;
	private Faker faker;
	@Autowired
	private SeerbitAPIServiceImpl seerbitAPIService;

	@BeforeEach
	void setup () {
		objectMapper = new ObjectMapper();
		faker = new Faker(new Locale("en-NG"));
		try {
			requestBody = objectMapper.readValue(SampleData.jsonData, RequestDtoEntity.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		ReflectionTestUtils.setField(seerbitAPIService, "BASE_URL", "https://staging.seerbitapigateway.com/fcmb/api/v1");
		ReflectionTestUtils.setField(seerbitAPIService, "GRANT_TYPE", "client_credentials");
		ReflectionTestUtils.setField(seerbitAPIService, "CLIENT_ID", "lpk_xxhyx6ta5JwJNgAw2MYzvhd7D");
		ReflectionTestUtils.setField(seerbitAPIService, "CLIENT_SECRETE", "spk_BdE5ovCPtVFoLQKL5Bnsotr2P");
	}

	@DisplayName("authenticate service method test")
	@Test
	void authenticate() {
		try {
			ResponseEntity<?> responseEntity = seerbitAPIService.authenticate();
			AuthCredential credential = objectMapper.readValue(responseEntity.getBody().toString(), AuthCredential.class);

			assertEquals(200, responseEntity.getStatusCode().value());
			assertNotNull(credential.getAccess_token());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DisplayName("Cash payout service test")
	@Test
	void cashPayout() {
		try {
			requestBody.getTransaction().setReference(faker.bothify("########??", true));
 			requestBody.getOrder().setAmount(BigDecimal.valueOf(40.00));
			requestBody.getSource().setOperation(Operation.acct_payout);
			ResponseEntity<?> responseEntity = seerbitAPIService.payout(requestBody);

			assertEquals(200, responseEntity.getStatusCode().value());
			Response response = objectMapper.readValue(responseEntity.getBody().toString(), Response.class);

			assertNotNull(response);
			assertEquals("00", response.getCode());
			assertEquals("Successful", response.getMessage());
			assertEquals(requestBody.getTransaction().getReference(),
					response.getTransaction().getReference());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DisplayName("Wallet payout service test")
	@Test
	void walletPayout() {
		try {
			requestBody.getTransaction().setReference(faker.bothify("########??", true));
			requestBody.getOrder().setAmount(BigDecimal.valueOf(100.00));
			requestBody.getSource().setOperation(Operation.wallet_payout);
			ResponseEntity<?> responseEntity = seerbitAPIService.payout(requestBody);

			assertEquals(200, responseEntity.getStatusCode().value());
			Response response = objectMapper.readValue(responseEntity.getBody().toString(), Response.class);

			assertNotNull(response);
			assertEquals("00", response.getCode());
			assertEquals("Successful", response.getMessage());
			assertEquals(requestBody.getTransaction().getReference(),
					response.getTransaction().getReference());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DisplayName("Wallet payout service test (with already existing reference)")
	@Test
	void walletPayoutWithExistingReference() {
		try {
			requestBody.getOrder().setAmount(BigDecimal.valueOf(100.00));
			requestBody.getSource().setOperation(Operation.wallet_payout);
			ResponseEntity<?> responseEntity = seerbitAPIService.payout(requestBody);

			assertEquals(200, responseEntity.getStatusCode().value());
			Response response = objectMapper.readValue(responseEntity.getBody().toString(), Response.class);

			assertNotNull(response);
			assertEquals("S14", response.getCode());
			assertEquals("Transaction reference must be unique", response.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DisplayName("Cash pickup service test")
	@Test
	void cashPickUp() {
		try {
			requestBody.getTransaction().setReference(faker.bothify("########??", true));
			requestBody.getOrder().setAmount(BigDecimal.valueOf(30.00));
			requestBody.getSource().setOperation(Operation.acct_payout);
			ResponseEntity<?> responseEntity = seerbitAPIService.cashPickUp(requestBody);

			assertEquals(200, responseEntity.getStatusCode().value());
			Response response = objectMapper.readValue(responseEntity.getBody().toString(), Response.class);

			assertNotNull(response);
			assertEquals("00", response.getCode());
			assertEquals("Successful", response.getMessage());
			assertNotNull(requestBody.getTransaction());
			assertEquals(requestBody.getTransaction().getReference(),
					response.getTransaction().getReference());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
