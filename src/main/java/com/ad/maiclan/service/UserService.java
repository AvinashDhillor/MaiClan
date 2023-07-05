package com.ad.maiclan.service;

import com.ad.maiclan.error.ClanDatabaseException;
import com.ad.maiclan.model.Clan;
import com.ad.maiclan.model.User;
import com.ad.maiclan.repository.ClanRepository;
import com.ad.maiclan.repository.ServerRepository;
import com.ad.maiclan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final ServerRepository serverRepository;
    private final ClanRepository clanRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserService(final ServerRepository serverRepository,
                       final ClanRepository clanRepository,
                       final UserRepository userRepository,
                       final MongoTemplate mongoTemplate) {
        this.serverRepository = serverRepository;
        this.clanRepository = clanRepository;
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }


    public void addUserToClan(final String serverId, final String userId, final String clanName) {
        // check if clan exists
        Clan clan = clanRepository.findByClanNameAndServerId(clanName, serverId);
        if(clan == null) {
            throw new ClanDatabaseException("Clan doesn't exists with this name.");
        }
        // check if user already in same clan.
        User userInSameClan = userRepository.findByUserId(userId);
        if(userInSameClan != null && userInSameClan.getClan().getClanName().equals(clanName)) {
            throw new ClanDatabaseException("User is already added to same clan.");
        }

        // check if user is part of some other clan.
        User userInAnotherClan = userRepository.findByServerIdAndUserId(serverId, userId);
        if(userInAnotherClan != null) {
            throw new ClanDatabaseException("User is part of different clan " + userInAnotherClan.getClan().getClanName());
        }

        User user = new User();
        user.setUserId(userId);
        user.setServer(serverRepository.getOrCreateServer(serverId));
        user.setClan(clan);

        userRepository.save(user);
    }

    public void removeUserFromClan(final String serverId, final String userId, final String clanName) {
        User user = userRepository.findByUserId(userId);
        // If user doesn't belong to any clan.
        if(user == null) {
            throw new ClanDatabaseException("User don't belong to any clan.");
        }

        // check if user is not part of clan
        if(!clanName.equals(user.getClan().getClanName())) {
            throw new ClanDatabaseException("User doesn't belong to this clan.");
        }

        userRepository.delete(user);
    }

    public List<User> getAllUser(final String serverId, final String clanName) {
        Clan clan = clanRepository.findByClanNameAndServerId(clanName, serverId);
        return userRepository.findByServerIdAndClan(serverId, clan);
    }
}
