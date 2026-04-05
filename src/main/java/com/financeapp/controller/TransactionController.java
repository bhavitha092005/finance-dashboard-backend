package com.financeapp.controller;

import com.financeapp.dto.TransactionResponse;
import com.financeapp.model.Transaction;
import com.financeapp.model.TransactionType;
import com.financeapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    // CREATE
    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return service.create(transaction);
    }

    // GET ALL (SAFE RESPONSE)
    @GetMapping
    public List<TransactionResponse> getAll() {
        return service.getAll().stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getAmount(),
                        t.getType(),
                        t.getCategory(),
                        t.getDate(),
                        t.getDescription(),
                        t.getUser().getName(),
                        t.getUser().getEmail()
                ))
                .collect(Collectors.toList());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public TransactionResponse getById(@PathVariable Long id) {

        Transaction t = service.getById(id);

        return new TransactionResponse(
                t.getId(),
                t.getAmount(),
                t.getType(),
                t.getCategory(),
                t.getDate(),
                t.getDescription(),
                t.getUser().getName(),
                t.getUser().getEmail()
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id,
                             @RequestBody Transaction transaction) {
        return service.update(id, transaction);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Transaction deleted";
    }

    // FILTER
    @GetMapping("/filter")
    public List<TransactionResponse> filter(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return service.filter(type, category, startDate, endDate)
                .stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getAmount(),
                        t.getType(),
                        t.getCategory(),
                        t.getDate(),
                        t.getDescription(),
                        t.getUser().getName(),
                        t.getUser().getEmail()
                ))
                .collect(Collectors.toList());
    }
}