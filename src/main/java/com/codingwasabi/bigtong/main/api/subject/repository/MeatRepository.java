package com.codingwasabi.bigtong.main.api.subject.repository;

import com.codingwasabi.bigtong.main.api.subject.entity.Meat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeatRepository extends JpaRepository<Meat,Long> {
}
