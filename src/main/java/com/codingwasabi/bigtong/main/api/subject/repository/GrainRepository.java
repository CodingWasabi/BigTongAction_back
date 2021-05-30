package com.codingwasabi.bigtong.main.api.subject.repository;

import com.codingwasabi.bigtong.main.api.subject.entity.Grain;
import com.codingwasabi.bigtong.main.api.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GrainRepository extends JpaRepository<Grain,Long> {

    Optional<Grain> findFirstByOrderByBidtimeDesc();

    List<Subject> findTop5ByOrderByBidtimeDesc();
}
