package com.ergoton.ergoaudit.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "AddressNonce")
public class GetAddressNonce {

    private String walletAddress;
    private String nonce;

    private LocalDateTime createdAt;

}
