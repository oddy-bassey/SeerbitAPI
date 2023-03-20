package com.fcmb.SeerbitAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Source {

    private AccountPerson sender;
    private AccountPerson recipient;
    private Operation operation;
}
