package com.assignment.name_project.repository;

import com.assignment.name_project.model.JsonModelEntity;
import com.assignment.name_project.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<JsonModelEntity,
        Long> {
}
