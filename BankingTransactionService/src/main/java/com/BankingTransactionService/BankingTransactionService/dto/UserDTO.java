package com.BankingTransactionService.BankingTransactionService.dto;


import java.util.UUID;

public record UserDTO (
       UUID id,
       String username,
       String email,
       String password
) {}
