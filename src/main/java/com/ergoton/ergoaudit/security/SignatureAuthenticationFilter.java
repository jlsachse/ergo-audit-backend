package com.ergoton.ergoaudit.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignatureAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SignatureAuthenticationFilter(AuthenticationManager authenticationManager) {
        super("/**");
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) {

        String stringWalletAddress = request.getHeader("walletAddress");
        String stringSignedMessage = request.getHeader("signedMessage");
        String stringSignature = request.getHeader("signature");

        System.out.println("walletAddress: " + stringWalletAddress);
        System.out.println("signedMessage: " + stringSignedMessage);
        System.out.println("signature: " + stringSignature);

        if (stringWalletAddress == null || stringSignedMessage == null || stringSignature == null) {
            throw new BadCredentialsException("Not all authentication data provided");
        }

        SignatureAuthenticationToken signatureAuthenticationToken = new SignatureAuthenticationToken(
                stringWalletAddress, stringSignedMessage, stringSignature);

        return getAuthenticationManager().authenticate(signatureAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

}
