package com.ergoton.ergoaudit.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class User {

    @Id private String id;

    private String logo;

    private String name;

    @Indexed(unique = true)
    private String walletAddress;
    private int experience;

    private String email;

    // Socials
    private String twitter;
    private String discord;

    public void addExperience(Integer experience) {
        this.experience += experience;
    }

}
