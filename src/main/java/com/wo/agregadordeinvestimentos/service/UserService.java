package com.wo.agregadordeinvestimentos.service;

import com.wo.agregadordeinvestimentos.controller.dto.*;
import com.wo.agregadordeinvestimentos.entity.Account;
import com.wo.agregadordeinvestimentos.entity.BillingAddress;
import com.wo.agregadordeinvestimentos.entity.User;
import com.wo.agregadordeinvestimentos.repository.AccountRepository;
import com.wo.agregadordeinvestimentos.repository.BillingAddressRepository;
import com.wo.agregadordeinvestimentos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BillingAddressRepository billingAddressRepository;

    public UUID crateUser(CreateUserDto createUserDto) {
        var user = new User(UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(user);

        return userSaved.getUserId();
    }


    public Optional<UserResponseIdDto> getUserById(String userId) {
        var user = userRepository.findById(UUID.fromString(userId));

        return user.map(u -> new UserResponseIdDto(
                user.get().getUserId(),
                user.get().getUsername(),
                user.get().getEmail()));
    }

    public List<UserResponseDto> listAllUsers() {

        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail()
                )).toList();
    }


    public void removeUser(String userId) {
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {

        var userEntity = userRepository.findById(UUID.fromString(userId));

        if (userEntity.isPresent()) {
            var user = userEntity.get();
            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }

    public void createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
                UUID.randomUUID(),
                createAccountDto.description(),
                user,
                null,
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                createAccountDto.street(),
                createAccountDto.number()
        );

        billingAddressRepository.save(billingAddress);


    }


    public List<AccountResponseDto> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts().stream()
                .map(acc ->
                        new AccountResponseDto(
                                acc.getAccountId().toString(),
                                acc.getDescription()))
                .toList();
    }
}
