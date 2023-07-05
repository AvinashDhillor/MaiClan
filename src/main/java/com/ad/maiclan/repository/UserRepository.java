package com.ad.maiclan.repository;

import com.ad.maiclan.model.Clan;
import com.ad.maiclan.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByClan(final Clan clan);

    @Query(value = "{'server' : ?0, 'userId': ?1}")
    User findByServerIdAndUserId(String serverId, String userId);

    List<User> findByServerIdAndClan(final String serverId, final Clan clan);

    User findByUserId(final String userId);
}
