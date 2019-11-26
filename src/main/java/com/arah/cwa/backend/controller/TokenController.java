package com.arah.cwa.backend.controller;

import com.arah.cwa.backend.rest.request.JwtTokenRequest;
import com.arah.cwa.backend.rest.request.UserCredentials;
import com.arah.cwa.backend.rest.response.JwtTokenResponse;
import com.arah.cwa.backend.security.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/token")
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<JwtTokenResponse> generateToken(@RequestBody UserCredentials userCredentials) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userCredentials.getUsername(),
                            userCredentials.getPassword()));

            final String token = JwtTokenUtils.generateToken(authentication);
            return ResponseEntity.ok(new JwtTokenResponse(token));
        } catch (Exception e) {
            return ResponseEntity.ok(JwtTokenResponse.empty());
        }
    }

    @PutMapping
    public ResponseEntity<Boolean> checkIfExpired(@RequestBody @Valid JwtTokenRequest jwtTokenRequest) {
        Boolean isExpired = null;
        if (jwtTokenRequest != null) {
            try {
                isExpired = JwtTokenUtils.isExpired(jwtTokenRequest.getToken());
            } catch (Exception e) {
                isExpired = false;
            }
        }
        return ResponseEntity.ok(isExpired);
    }
}
