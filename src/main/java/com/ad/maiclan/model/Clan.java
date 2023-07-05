package com.ad.maiclan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("clan")
public class Clan {

    @Id
    private String id;

    private String clanName;

    @DocumentReference(lazy = true)
    @JsonIgnore
    private Server server;

    @DocumentReference(lazy = true)
    @JsonIgnore
    private List<User> users;

    public Clan(String clanName, Server server) {
        this.clanName = clanName;
        this.server = server;
    }
}
