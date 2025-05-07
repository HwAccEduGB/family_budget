package com.example.family_budget.service;

import com.example.family_budget.dto.ResponseModel;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface UserAuthService {
    ResponseModel authenticateWithGoogle(String idToken) throws GeneralSecurityException, IOException;
}
