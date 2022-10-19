package com.ergoton.ergoaudit.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(name = "New User")
public class PostUser {

    private String logo;

    @Size(message = "You need to submit a name that is between 3 and 30 characters long.", min = 3, max = 30)
    private String name;

    @NotBlank(message = "You need to submit your wallet address.")
    private String walletAddress;

    private String email;

    // Socials
    private String twitter;
    private String discord;

}
