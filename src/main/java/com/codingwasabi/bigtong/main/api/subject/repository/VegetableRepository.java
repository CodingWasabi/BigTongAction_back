package com.codingwasabi.bigtong.main.api.subject.repository;

import com.codingwasabi.bigtong.main.api.subject.entity.Subject;
import com.codingwasabi.bigtong.main.api.subject.entity.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VegetableRepository extends JpaRepository<Vegetable,Long> {
    List<Subject> findTop5ByOrderByBidtimeDesc();

    Vegetable findTop1ByOrderByBidtimeDesc();
}
