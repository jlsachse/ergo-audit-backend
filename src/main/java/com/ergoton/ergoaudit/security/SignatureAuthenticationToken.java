package com.ergoton.ergoaudit.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

@Transient
public class SignatureAuthenticationToken extends AbstractAuthenticationToken {

    private String stringWalletAddress;
    private String stringSignature;
    private String stringSignedMessage;

    @Override
    public Object getCredentials() {
        return this.stringSignature;
    }

    @Override
    public Object getPrincipal() {
        return this.stringWalletAddress;
    }

    public Object getDetails() {
        return this.stringSignedMessage;
    }


    public SignatureAuthenticationToken(String stringWalletAddress, String signedMessage, String signature,
                                        boolean authenticated, List<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.stringWalletAddress = stringWalletAddress;
        this.stringSignedMessage = signedMessage;
        this.stringSignature = signature;
        setAuthenticated(authenticated);
    }

    public SignatureAuthenticationToken(String stringWalletAddress, String signedMessage, String signature) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.stringWalletAddress = stringWalletAddress;
        this.stringSignedMessage = signedMessage;
        this.stringSignature = signature;
        setAuthenticated(false);
    }

    public SignatureAuthenticationToken() {
        super(AuthorityUtils.NO_AUTHORITIES);
        setAuthenticated(false);
    }


}
