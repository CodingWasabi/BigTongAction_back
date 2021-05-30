package com.codingwasabi.bigtong.main.api.subject.repository;

import com.codingwasabi.bigtong.main.api.subject.entity.Meat;
import com.codingwasabi.bigtong.main.api.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeatRepository extends JpaRepository<Meat,Long> {
    List<Subject> findTop5ByOrderByBidtimeDesc();

    Meat findTop1ByOrderByBidtimeDesc();
}
