package com.ergoton.ergoaudit.services;

import com.ergoton.ergoaudit.exceptions.EntityNotFoundException;
import com.ergoton.ergoaudit.model.AddressNonce;
import com.ergoton.ergoaudit.repositories.AddressNonceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressNonceService {

    private final AddressNonceRepository addressNonceRepository;

    public AddressNonce createRandomAddressNonce(AddressNonce newAddressNonce) {

        String newSecureNonce = nonceGenerator();
        newAddressNonce.setNonce(newSecureNonce);

        LocalDateTime currentTime = LocalDateTime.now();
        newAddressNonce.setCreatedAt(currentTime);


        return addressNonceRepository.save(newAddressNonce);
    }

    public AddressNonce getNonceByWalletAddress(String walletAddress) {
        Optional<AddressNonce> addressNonce = addressNonceRepository.findById(walletAddress);

        addressNonce.orElseThrow(() -> new EntityNotFoundException("No nonce found for wallet address: " + walletAddress));

        return addressNonce.get();
    }

    public static String nonceGenerator(){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            stringBuilder.append(secureRandom.nextInt(10));
        }
        return stringBuilder.toString();
    }

    public void deleteNonceByWallet(AddressNonce addressNonce) {
        addressNonceRepository.delete(addressNonce);
    }
}
