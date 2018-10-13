package com.demo.fes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericRepository<E, K extends Serializable> extends JpaRepository<E, K> {
}
