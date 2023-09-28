package com.ykhan.lolmatchup.repository;

import com.ykhan.lolmatchup.model.LeagueSummoner;
import jakarta.transaction.Transactional;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LeagueSummonerRepository extends JpaRepository<LeagueSummoner, Long> {

    Optional<LeagueSummoner> findByUniqueId(String uniqueId);
    Optional<LeagueSummoner> findBySummonerId(String summonerId);
    Optional<LeagueSummoner> findByRegionAndName(LeagueShard region, String name);
}
