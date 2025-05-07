package com.example.family_budget.service.impl;

import com.example.family_budget.service.GoogleAuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleAuthServiceImpl implements  GoogleAuthService {

    private static final String CLIENT_ID = "ВАШ_CLIENT_ID";

    public GoogleIdToken.Payload verifyToken(String idToken) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idTokenObj = verifier.verify(idToken);
        if (idTokenObj != null) {
            return idTokenObj.getPayload();
        } else {
            throw new RuntimeException("Invalid ID token");
        }
    }
}
