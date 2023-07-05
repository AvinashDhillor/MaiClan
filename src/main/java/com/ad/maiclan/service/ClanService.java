package com.ad.maiclan.service;

import com.ad.maiclan.error.ClanDatabaseException;
import com.ad.maiclan.model.Clan;
import com.ad.maiclan.model.Server;
import com.ad.maiclan.model.User;
import com.ad.maiclan.repository.ClanRepository;
import com.ad.maiclan.repository.ServerRepository;
import com.ad.maiclan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClanService {

    private final ServerRepository serverRepository;
    private final ClanRepository clanRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ClanService(final ServerRepository serverRepository,
                       final ClanRepository clanRepository,
                       final UserRepository userRepository,
                       final MongoTemplate mongoTemplate) {
        this.serverRepository = serverRepository;
        this.clanRepository = clanRepository;
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void addClan(final String serverId, final String clanName) {
        Server server = serverRepository.getOrCreateServer(serverId);

        Clan clan = clanRepository.findByClanNameAndServerId(clanName, server.getId());
        if(clan != null) {
            throw new ClanDatabaseException("Clan already exists.");
        }

        Clan insertedClan = clanRepository.save(new Clan(clanName, server));

        Query query = new Query(Criteria.where("_id").is(server.getId()));
        Update update = new Update().push("clans").value(insertedClan.getId());
        mongoTemplate.updateFirst(query, update, Server.class);
    }

    public double clanScore(final String serverId, final String clanName) {
        Clan clan = clanRepository.findByClanNameAndServerId(clanName, serverId);
        List<User> users = userRepository.findByServerIdAndClan(serverId, clan);
        double score = 0.0;
        for(User user : users) {
            score += user.getTotalScore();
        }
        return score;
    }

    public List<Clan> getAllClan(final String serverId) {
        Optional<Server> server = serverRepository.findById(serverId);
        if(server.isPresent()) {
            return server.get().getClans();
        }
        System.out.println("Coming");
        return new ArrayList<>();
    }

    public void deleteClan(final String serverId, final String clanName) {
        Clan clan = clanRepository.findByClanNameAndServerId(clanName, serverId);
        clanRepository.delete(clan);
    }

}
