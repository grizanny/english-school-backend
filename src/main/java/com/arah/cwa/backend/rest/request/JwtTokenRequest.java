package com.arah.cwa.backend.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenRequest {

    @NotEmpty
    private String token;
}
