package com.sebCzabak.fullstackProjectEmployeTodoList.token.ConfirmationToken;

import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {
    public ConfirmationTokenService(ConfirmationTokenRepo confirmationTokenRepo) {
        this.confirmationTokenRepo = confirmationTokenRepo;
    }

    private final ConfirmationTokenRepo confirmationTokenRepo;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepo.save(token);
    }
}
