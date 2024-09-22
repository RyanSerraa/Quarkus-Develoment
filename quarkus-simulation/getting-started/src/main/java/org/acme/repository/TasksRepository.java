package org.acme.repository;

import org.acme.entity.Tasks;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class TasksRepository implements PanacheRepository<Tasks> {
    
}
