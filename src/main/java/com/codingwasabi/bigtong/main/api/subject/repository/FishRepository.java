package com.codingwasabi.bigtong.main.api.subject.repository;

import com.codingwasabi.bigtong.main.api.subject.entity.Fish;
import com.codingwasabi.bigtong.main.api.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FishRepository extends JpaRepository<Fish,Long> {
    List<Subject> findTop5ByOrderByBidtimeDesc();

    Optional<Fish> findFirstByOrderByBidtimeDesc();
}
