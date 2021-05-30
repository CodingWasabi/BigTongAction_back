package com.codingwasabi.bigtong.main.api.subject.repository;

import com.codingwasabi.bigtong.main.api.subject.entity.Meat;
import com.codingwasabi.bigtong.main.api.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeatRepository extends JpaRepository<Meat,Long> {
    List<Subject> findTop5ByOrderByBidtimeDesc();

    Optional<Meat> findFirstByOrderByBidtimeDesc();
}
