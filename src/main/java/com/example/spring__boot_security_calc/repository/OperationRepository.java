package com.example.spring__boot_security_calc.repository;

import com.example.spring__boot_security_calc.entity.Operation;
import com.example.spring__boot_security_calc.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findAllByUser(User user, PageRequest pageRequest);
    int countAllByUser(User user);
}
