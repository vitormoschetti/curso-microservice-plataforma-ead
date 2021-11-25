package com.ead.course.adapter.repository;

import com.ead.course.adapter.repository.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleEntity, UUID> {
}
