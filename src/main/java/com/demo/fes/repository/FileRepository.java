package com.demo.fes.repository;

import com.demo.fes.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    void deleteById(Long id);
}
