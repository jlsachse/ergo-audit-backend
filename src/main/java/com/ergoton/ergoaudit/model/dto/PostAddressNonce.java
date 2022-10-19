package com.ergoton.ergoaudit.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(name = "New AddressNonce")
public class PostAddressNonce {

    @NotBlank(message = "You need to provide a wallet address.")
    private String walletAddress;

}
