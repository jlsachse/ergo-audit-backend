package com.ergoton.ergoaudit.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Schema(name = "User")
@EqualsAndHashCode(callSuper = true)
public class GetUser extends RepresentationModel<GetUser> {

    private String id;
    private String logo;

    private String name;
    private String walletAddress;
    private int experience;

    private String email;

    // Socials
    private String twitter;
    private String discord;

}
