package com.arah.cwa.backend.rest.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Setter
@EqualsAndHashCode
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JwtTokenResponse {

    private String token;

    private JwtTokenResponse() {

    }

    public static JwtTokenResponse empty() {
        return new JwtTokenResponse();
    }
}
