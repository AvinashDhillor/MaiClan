package com.ad.maiclan.repository;

import com.ad.maiclan.model.Server;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends MongoRepository<Server, String> {
    default Server getOrCreateServer(String serverId) {
        Server server = findById(serverId).orElse(null);
        if (server == null) {
            server = new Server(serverId);
            server = save(server);
        }
        return server;
    }
}
