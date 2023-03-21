package com.fcmb.SeerbitAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestDtoEntity extends BaseDtoEntity {

    private String publickey;
    private Transaction transaction;
    private Order order;
    private Source source;
}
