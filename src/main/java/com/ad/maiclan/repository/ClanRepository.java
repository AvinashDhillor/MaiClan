package com.ad.maiclan.repository;

import com.ad.maiclan.model.Clan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanRepository extends MongoRepository<Clan, String> {

    @Query(value = "{'clanName' : ?0, 'server' : ?1}")
    Clan findByClanNameAndServerId(final String clanName, final String serverId);

}
