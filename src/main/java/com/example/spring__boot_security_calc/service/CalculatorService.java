package com.example.spring__boot_security_calc.service;

import com.example.spring__boot_security_calc.entity.Operation;
import com.example.spring__boot_security_calc.entity.User;
import com.example.spring__boot_security_calc.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CalculatorService {

    @Autowired
    private OperationRepository operationRepository;

    public List<Operation> findAll(User user, int page, int size){
        return operationRepository.findAllByUser(user, PageRequest.of(page, size, Sort.Direction.ASC, "time"));
    }

    public int getCountOfPages(User user, int countPerPage){
        int i = operationRepository.countAllByUser(user);
        return (int) Math.ceil(i / countPerPage);
    }

    public Optional<Operation> calculate(Operation operation, User user){

        operation.setUser(user);
        operation.setTime(LocalDateTime.now());

        switch (operation.getType()) {
            case SUM -> {
                operation.setResult(operation.getNum1() + operation.getNum2());
                operationRepository.save(operation);
                return Optional.of(operation);
            }
            case SUB -> {
                operation.setResult(operation.getNum1() - operation.getNum2());
                operationRepository.save(operation);
                return Optional.of(operation);
            }
            case MUL -> {
                operation.setResult(operation.getNum1() * operation.getNum2());
                operationRepository.save(operation);
                return Optional.of(operation);
            }
            case DIV -> {
                operation.setResult(operation.getNum1() / operation.getNum2());
                operationRepository.save(operation);
                return Optional.of(operation);
            }
        }
        return Optional.empty();
    }
}
