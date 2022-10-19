package com.ergoton.ergoaudit.security;

import com.ergoton.ergoaudit.model.AddressNonce;
import com.ergoton.ergoaudit.services.AddressNonceService;
import org.ergoplatform.appkit.Address;
import org.ergoplatform.appkit.ErgoAuthUtils;
import org.ergoplatform.appkit.SigmaProp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SignatureAuthenticationProvider implements AuthenticationProvider {

    @Value("${authentication.max-nonce-age}" )
    private Duration creationTimeout;

    private final AddressNonceService addressNonceService;

    public SignatureAuthenticationProvider(AddressNonceService addressNonceService) {
        this.addressNonceService = addressNonceService;
    }

    private Boolean isValidUser(String stringWalletAddress, String signedMessage, String stringSignature) {


        String[] signedMessageComponents = signedMessage.split(";");

        if (signedMessageComponents.length != 4) {
            throw new BadCredentialsException("Authentication needs 4 components in the signed message. Found" +
                    " only " + signedMessageComponents.length);
        }


        try {

            AddressNonce addressNonce = addressNonceService.getNonceByWalletAddress(stringWalletAddress);

            LocalDateTime nonceCreationTime = addressNonce.getCreatedAt();
            addressNonceService.deleteNonceByWallet(addressNonce);

            Address sigmaWalletAddress = Address.create(stringWalletAddress);
            SigmaProp sigmaProp = SigmaProp.createFromAddress(sigmaWalletAddress);

            byte[] hexBytesSignature = hexStringToByteArray(stringSignature);

            if (LocalDateTime.now().isAfter(nonceCreationTime.plus(creationTimeout))) {
                System.out.println("Nonce is too old");
                throw new BadCredentialsException("Nonce is too old. Please create a new one. Allowed nonce age " +
                        creationTimeout);
            }

            return ErgoAuthUtils.verifyResponse(sigmaProp, addressNonce.getNonce(), signedMessage, hexBytesSignature);

        } catch (Exception e) {
            System.out.println("No nonce found for wallet address: " + stringWalletAddress);
            throw new BadCredentialsException("No nonce found for wallet address " + stringWalletAddress);
        }


    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        Object walletAddress = authentication.getPrincipal();
        Object signedMessage = authentication.getDetails();
        Object signature = authentication.getCredentials();

        String stringWalletAddress = walletAddress.toString();
        String stringSignedMessage = signedMessage.toString();
        String hexBytesSignature = signature.toString();

        if (!isValidUser(stringWalletAddress, stringSignedMessage, hexBytesSignature)) {
            throw new BadCredentialsException("Incorrect signature for wallet address: " + stringWalletAddress);
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new SignatureAuthenticationToken(stringWalletAddress, stringSignedMessage, hexBytesSignature,
                true, authorities);

    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType
                .equals(SignatureAuthenticationToken.class);
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}