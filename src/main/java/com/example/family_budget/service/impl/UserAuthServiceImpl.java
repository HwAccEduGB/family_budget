package com.example.family_budget.service.impl;

import com.example.family_budget.dto.ResponseModel;
import com.example.family_budget.entity.User;
import com.example.family_budget.repository.UserRepository;
import com.example.family_budget.service.GoogleAuthService;
import com.example.family_budget.service.UserAuthService;
import com.example.family_budget.util.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleAuthService googleAuthService;

    @Override
    public ResponseModel authenticateWithGoogle(String idToken) throws GeneralSecurityException, IOException {
        // Проверка токена
        GoogleIdToken.Payload payload = googleAuthService.verifyToken(idToken);

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        // Поиск или создание пользователя
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name != null ? name : email);
            return userRepository.save(newUser);
        });

        // Генерируем JWT
        String jwtToken = JwtUtil.generateToken(user.getId());

        // Возвращаем ответ с токеном
        return new ResponseModel("success", user.getId().intValue(), jwtToken);
    }
}
