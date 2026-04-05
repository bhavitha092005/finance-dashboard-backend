package com.financeapp.service;

import com.financeapp.model.Role;
import com.financeapp.model.Transaction;
import com.financeapp.model.TransactionType;
import com.financeapp.model.User;
import com.financeapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    // CREATE
    public Transaction create(Transaction transaction) {

        if (transaction.getAmount() <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        User currentUser = userService.getCurrentUser();
        transaction.setUser(currentUser);

        return transactionRepository.save(transaction);
    }

    // GET ALL
    public List<Transaction> getAll() {

        User currentUser = userService.getCurrentUser();

        if (currentUser.getRole() == Role.ROLE_ADMIN) {
            return transactionRepository.findAll();
        }

        return transactionRepository.findByUser(currentUser);
    }

    // GET BY ID
    public Transaction getById(Long id) {

        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        User currentUser = userService.getCurrentUser();

        if (currentUser.getRole() != Role.ROLE_ADMIN &&
                !t.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access denied");
        }

        return t;
    }

    // UPDATE
    public Transaction update(Long id, Transaction updated) {

        Transaction existing = getById(id);

        existing.setAmount(updated.getAmount());
        existing.setType(updated.getType());
        existing.setCategory(updated.getCategory());
        existing.setDate(updated.getDate());
        existing.setDescription(updated.getDescription());

        return transactionRepository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        Transaction t = getById(id);
        transactionRepository.delete(t);
    }

    // FILTER
    public List<Transaction> filter(TransactionType type,
                                    String category,
                                    LocalDate startDate,
                                    LocalDate endDate) {

        List<Transaction> list = getAll(); // 🔥 important (user-based)

        if (type != null) {
            list = list.stream()
                    .filter(t -> t.getType() == type)
                    .toList();
        }

        if (category != null) {
            list = list.stream()
                    .filter(t -> t.getCategory().equalsIgnoreCase(category))
                    .toList();
        }

        if (startDate != null && endDate != null) {
            list = list.stream()
                    .filter(t -> !t.getDate().isBefore(startDate)
                            && !t.getDate().isAfter(endDate))
                    .toList();
        }

        return list;
    }
}