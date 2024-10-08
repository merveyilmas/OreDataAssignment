package com.BankingTransactionService.BankingTransactionService.service.entityService;

import com.BankingTransactionService.BankingTransactionService.dao.AccountRepository;
import com.BankingTransactionService.BankingTransactionService.entity.Account;
import com.BankingTransactionService.BankingTransactionService.entity.User;
import com.BankingTransactionService.BankingTransactionService.exceptions.ItemNotFoundException;
import com.BankingTransactionService.BankingTransactionService.general.BaseEntityService;
import com.BankingTransactionService.BankingTransactionService.general.GeneralErrorMessage;
import com.BankingTransactionService.BankingTransactionService.request.MoneyTransferRequest;
import com.BankingTransactionService.BankingTransactionService.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class AccountEntityService extends BaseEntityService<Account, UUID, AccountRepository> {
    private final AccountRepository accountRepository;
    private final AuthService authService;

    protected AccountEntityService(AccountRepository accountRepository, AuthService authService) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.authService = authService;
    }

    public Account createAccount() {

        User user = this.authService.getUserInfo();

        // Generate unique account number
        String uniqueNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        while (accountRepository.existsByNumber(uniqueNumber)) {
            uniqueNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 12); // Yeniden oluştur
        }

        // Create a unique account name
        String uniqueName = user.getUsername() + "-" + new Random().nextInt(9999);

        while (accountRepository.existsByName(uniqueName)) {
            uniqueName = user.getUsername() + "-" + new Random().nextInt(9999); // Yeniden oluştur
        }

        Account account = new Account();
        account.setName(uniqueName);
        account.setNumber(uniqueNumber);
        account.setBalance(BigDecimal.ZERO);
        account.setUser(user);

        return accountRepository.save(account);
    }

    public List<Account> searchAccount(String nameOrNumber) {

        User user = this.authService.getUserInfo();
        List<Account> accounts = accountRepository.findByUserAndNameContaining(user, nameOrNumber);

        if (accounts.isEmpty()) {
            accounts = accountRepository.findByUserAndNumberContaining(user, nameOrNumber);
        }

        return accounts;
    }

    public List<Account> getAllAccountsByUser() {

        User user = this.authService.getUserInfo();
        List<Account> accounts = accountRepository.findByUser(user);

        return accounts;
    }

    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Transactional
    public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {

        if(toAccount == null || fromAccount == null){
            throw new ItemNotFoundException(GeneralErrorMessage.ITEM_NOT_FOUND);
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Yetersiz bakiye!");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
