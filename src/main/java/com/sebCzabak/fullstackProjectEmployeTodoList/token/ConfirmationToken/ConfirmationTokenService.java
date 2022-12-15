package com.sebCzabak.fullstackProjectEmployeTodoList.token.ConfirmationToken;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {
    public ConfirmationTokenService(ConfirmationTokenRepo confirmationTokenRepo) {
        this.confirmationTokenRepo = confirmationTokenRepo;
    }

    private final ConfirmationTokenRepo confirmationTokenRepo;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepo.save(token);
    }

    public Optional<ConfirmationToken>getToken(String token){
        return confirmationTokenRepo.findByToken(token);
    }
    public int setConfirmedAt(String token){
        return confirmationTokenRepo.updateConfirmedAt(
                token, LocalDateTime.now());

    }

    public void deleteToken(Long id) {
        confirmationTokenRepo.deleteById(id);
    }
}
