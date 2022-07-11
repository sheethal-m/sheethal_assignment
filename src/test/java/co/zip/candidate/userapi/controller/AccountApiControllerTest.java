package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.Account;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.repository.AccountRepository;
import co.zip.candidate.userapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountApiControllerTest {

    @InjectMocks
    private AccountApiController accountApiController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void test_createRequestBodyIsNull(){
        ResponseEntity responseEntity = accountApiController.create(null);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("User id is missing.",responseEntity.getBody());
    }

    @Test
    public void test_createUserNotRegistered(){
        Account account = new Account();
        account.setUser_id(new Long(1));
        when(userRepository.findById(account.getUser_id())).thenReturn(Optional.empty());
        ResponseEntity responseEntity = accountApiController.create(account);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Invalid user id.",responseEntity.getBody());
    }

    @Test
    public void test_createSuccessDifferenceLessThan1000(){
        Account account = new Account();
        account.setUser_id(new Long(1));
        User user = new User();
        user.setId(new Long(1));
        user.setMonthly_salary(new Double(1000));
        user.setMonthly_expense(new Double(500));
        when(userRepository.findById(account.getUser_id())).thenReturn(Optional.of(user));
        ResponseEntity responseEntity = accountApiController.create(account);
        assertEquals(412, responseEntity.getStatusCodeValue());
        assertEquals("Difference between monthly salary and monthly expense is less than 1000",responseEntity.getBody());
    }

    @Test
    public void test_createSuccess(){
        Account account = new Account();
        account.setUser_id(new Long(1));
        User user = new User();
        user.setId(new Long(1));
        user.setMonthly_salary(new Double(2000));
        user.setMonthly_expense(new Double(100));
        when(userRepository.findById(account.getUser_id())).thenReturn(Optional.of(user));
        when(accountRepository.save(account)).thenReturn(account);
        ResponseEntity responseEntity = accountApiController.create(account);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(account,responseEntity.getBody());
    }

    @Test
    public void test_getAccountEmptyList(){
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity responseEntity = accountApiController.getAccount();
        assertEquals(204,responseEntity.getStatusCodeValue());
    }

    @Test
    public void test_getAccountSuccess(){
        Account account =  new Account();
        account.setUser_id(new Long(1));
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        when(accountRepository.findAll()).thenReturn(accountList);
        ResponseEntity responseEntity = accountApiController.getAccount();
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(accountList,responseEntity.getBody());
    }

}