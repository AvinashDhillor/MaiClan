package com.ad.maiclan.service;

import com.ad.maiclan.model.User;
import com.ad.maiclan.repository.ClanRepository;
import com.ad.maiclan.repository.ServerRepository;
import com.ad.maiclan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SessionService {

    private final ServerRepository serverRepository;
    private final ClanRepository clanRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public SessionService(final ServerRepository serverRepository,
                          final ClanRepository clanRepository,
                          final UserRepository userRepository,
                          final MongoTemplate mongoTemplate) {
        this.serverRepository = serverRepository;
        this.clanRepository = clanRepository;
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void startUserSession (final String serverId, final String userId) {
        User user = userRepository.findByServerIdAndUserId(serverId, userId);

        if(user == null) return;

        if(user.isActive()) {
            this.stopUserSession(serverId, userId);
            user = userRepository.findByServerIdAndUserId(serverId, userId);
        }

        user.setActive(true);
        user.setSessionStartTime(LocalDateTime.now());
        userRepository.save(user);
    }

    public void stopUserSession(final String serverId, final String userId) {
        LocalDateTime sessionEndTime = LocalDateTime.now();

        User user = userRepository.findByServerIdAndUserId(serverId, userId);

        if(user == null || !user.isActive()) return;

        LocalDateTime sessionStartTime = user.getSessionStartTime();

        Duration duration = Duration.between(sessionStartTime, sessionEndTime);

        long minutes = duration.toMinutes();

        user.setActive(false);
        user.setSessionStartTime(null);
        user.setTotalScore(user.getTotalScore() + (double) minutes / 60.0);
        userRepository.save(user);
    }
}
