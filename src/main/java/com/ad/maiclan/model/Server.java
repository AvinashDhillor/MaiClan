package com.ad.maiclan.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString
public class Server {

    @Id
    private String id;

    private String serverName;

    @DocumentReference
    private List<Clan> clans;

    public Server(String serverId) {
        this.id = serverId;
    }
}
