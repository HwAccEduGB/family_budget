package com.example.family_budget.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.jsonwebtoken.io.IOException;

import java.security.GeneralSecurityException;

public interface GoogleAuthService {
    GoogleIdToken.Payload verifyToken(String idToken) throws GeneralSecurityException, IOException, java.io.IOException;
}
