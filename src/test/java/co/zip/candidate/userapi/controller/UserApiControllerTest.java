package co.zip.candidate.userapi.controller;


import co.zip.candidate.userapi.model.User;
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
class UserApiControllerTest {

    @InjectMocks
    private UserApiController userApiController;

    @Mock
    private UserRepository userRepository;



    @Test
    public void test_createNullRequest(){
        User user = null;
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Input cannot be null. ",responseEntity.getBody());
    }

    @Test
    public void test_createEmptyEmail(){
        User user = new User();
        user.setEmail("  ");
        user.setName("sheethal");
        user.setMonthly_salary(new Double(2000));
        user.setMonthly_expense(new Double(200));
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Email is either null, empty or in invalid format. ",responseEntity.getBody());
    }

    @Test
    public void test_createNullEmail(){
        User user = new User();
        user.setEmail(null);
        user.setName("sheethal");
        user.setMonthly_salary(new Double(2000));
        user.setMonthly_expense(new Double(200));
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Email is either null, empty or in invalid format. ",responseEntity.getBody());
    }

    @Test
    public void test_createInvalidEmailFormat(){
        User user = new User();
        user.setEmail("thisisstring");
        user.setName("sheethal");
        user.setMonthly_salary(new Double(2000));
        user.setMonthly_expense(new Double(200));
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Email is either null, empty or in invalid format. ",responseEntity.getBody());
    }

    @Test
    public void test_createNullName(){
        User user = new User();
        user.setEmail("sheethal@gmail.com");
        user.setName(null);
        user.setMonthly_salary(new Double(2000));
        user.setMonthly_expense(new Double(200));
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Name cannot be null or empty. ",responseEntity.getBody());
    }

    @Test
    public void test_createEmptyName(){
        User user = new User();
        user.setEmail("sheethal@gmail.com");
        user.setName("  ");
        user.setMonthly_salary(new Double(2000));
        user.setMonthly_expense(new Double(200));
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Name cannot be null or empty. ",responseEntity.getBody());
    }

    @Test
    public void test_createNullMonthlySalary(){
        User user = new User();
        user.setEmail("sheethal@gmail.com");
        user.setName("sheethal");
        user.setMonthly_salary(null);
        user.setMonthly_expense(new Double(200));
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Monthly salary cannot be null or negative. ",responseEntity.getBody());
    }

    @Test
    public void test_createNegativeMonthlySalary(){
        User user = new User();
        user.setEmail("sheethal@gmail.com");
        user.setName("sheethal");
        user.setMonthly_salary(new Double(-200));
        user.setMonthly_expense(new Double(200));
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Monthly salary cannot be null or negative. ",responseEntity.getBody());
    }

    @Test
    public void test_createNullMonthlyExpense(){
        User user = new User();
        user.setEmail("sheethal@gmail.com");
        user.setName("sheethal");
        user.setMonthly_expense(null);
        user.setMonthly_salary(new Double(200));
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Monthly expense cannot be null or negative. ",responseEntity.getBody());
    }

    @Test
    public void test_createNegativeMonthlyExpense(){
        User user = new User();
        user.setEmail("sheethal@gmail.com");
        user.setName("sheethal");
        user.setMonthly_expense(new Double(-200));
        user.setMonthly_salary(new Double(200));
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Monthly expense cannot be null or negative. ",responseEntity.getBody());
    }

    @Test
    public void test_createErrorsAreAppended(){
        User user = new User();
        user.setEmail("sheethal");
        user.setName("  ");
        user.setMonthly_expense(new Double(-200));
        user.setMonthly_salary(null);
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Email is either null, empty or in invalid format. Name cannot be null or empty. Monthly expense cannot be null or negative. Monthly salary cannot be null or negative. ",responseEntity.getBody());
    }

    @Test
    public void test_createEmailAlreadyExists(){
        User user = new User();
        user.setEmail("sheethal@gmail.com");
        user.setName("sheethal");
        user.setMonthly_expense(new Double(100));
        user.setMonthly_salary(new Double(200));
        when(userRepository.findByEmail("sheethal@gmail.com")).thenReturn(user);
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(409, responseEntity.getStatusCodeValue());
        assertEquals("User with email "+user.getEmail()+" is already registered with us." ,responseEntity.getBody());
    }

    @Test
    public void testreateSuccess(){
        User user = new User();
        user.setEmail("sheethal@gmail.com");
        user.setName("sheethal");
        user.setMonthly_expense(new Double(100));
        user.setMonthly_salary(new Double(200));
        when(userRepository.findByEmail("sheethal@gmail.com")).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);
        ResponseEntity responseEntity = userApiController.create(user);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(user,responseEntity.getBody());
    }

    @Test
    public void test_getAllUsersEmptyList(){
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity responseEntity = userApiController.getAllUsers();
        assertEquals(204, responseEntity.getStatusCodeValue());
    }

    @Test
    public void test_getAllUsersSuccess(){
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(new Long("1"));
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity responseEntity = userApiController.getAllUsers();
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(userList,responseEntity.getBody());
    }

    @Test
    public void test_getSingleUserNotFound(){
        when(userRepository.findById(new Long(1))).thenReturn(Optional.empty());
        ResponseEntity responseEntity = userApiController.getSingleUser(new Long(1));
        assertEquals(404,responseEntity.getStatusCodeValue());
    }

    @Test
    public void test_getSingleUserSuccess(){
        User user = new User();
        user.setId(new Long(1));
        user.setEmail("test@gmail.com");
        when(userRepository.findById(new Long(1))).thenReturn(Optional.of(user));
        ResponseEntity responseEntity = userApiController.getSingleUser(new Long(1));
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

}