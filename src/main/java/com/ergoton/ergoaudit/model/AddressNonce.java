package com.ergoton.ergoaudit.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "addressNonce")
public class AddressNonce {

    @Id
    private String walletAddress;
    private String nonce;

    private LocalDateTime createdAt;

}
