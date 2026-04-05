package com.financeapp.service;

import com.financeapp.model.Role;
import com.financeapp.model.Transaction;
import com.financeapp.model.TransactionType;
import com.financeapp.model.User;
import com.financeapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public Map<String, Object> getSummary() {

        User currentUser = userService.getCurrentUser();

        List<Transaction> transactions;

        if (currentUser.getRole() == Role.ROLE_ADMIN) {
            transactions = transactionRepository.findAll();
        } else {
            transactions = transactionRepository.findByUser(currentUser);
        }

        double totalIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpenses = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double netBalance = totalIncome - totalExpenses;

        Map<String, Double> categoryTotals = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        List<Transaction> recent = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .limit(5)
                .toList();

        Map<String, Object> result = new HashMap<>();
        result.put("totalIncome", totalIncome);
        result.put("totalExpenses", totalExpenses);
        result.put("netBalance", netBalance);
        result.put("categoryTotals", categoryTotals);
        result.put("recentTransactions", recent);

        return result;
    }
}