package com.ergoton.ergoaudit.controllers;

import com.ergoton.ergoaudit.model.AddressNonce;
import com.ergoton.ergoaudit.model.dto.GetAddressNonce;
import com.ergoton.ergoaudit.model.dto.PostAddressNonce;
import com.ergoton.ergoaudit.services.AddressNonceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Tag(name = "Nonce Controller", description = "This controller provides interfaces to create random nonces for wallet verification.")
public class AddressNonceController {

    private final AddressNonceService addressNonceService;
    private final ModelMapper modelMapper;

    @PostMapping(value = "/addressNonce", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a secure random nonce for a wallet"
            , description = "This interface is used to create a new secure random nonce for a wallet.")
    public GetAddressNonce createRandomNonce(@Validated @RequestBody PostAddressNonce postAddressNonce) {

        AddressNonce newAddressNonce = modelMapper.map(postAddressNonce, AddressNonce.class);

        AddressNonce createdAddressNonce = addressNonceService.createRandomAddressNonce(newAddressNonce);

        return modelMapper.map(createdAddressNonce, GetAddressNonce.class);
    }

}
