package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.Account;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.repository.AccountRepository;
import co.zip.candidate.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody Account account) {
        if(account == null || account.getUser_id() == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User id is missing.");
        try {
            Optional<User> user = userRepository.findById(account.getUser_id());
            if (!user.isPresent())
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid user id.");
            if(user.get().getMonthly_salary() -  user.get().getMonthly_expense() < 1000)
                return ResponseEntity
                        .status(HttpStatus.PRECONDITION_FAILED)
                        .body("Difference between monthly salary and monthly expense is less than 1000");
            Account accountResponse = accountRepository.save(account);

            return ResponseEntity
                    .status(HttpStatus.CREATED).body(accountResponse);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAccount () {
        try {
            List<Account> accountList = accountRepository.findAll();
            if (accountList.isEmpty() || accountList.size() == 0) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(accountList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
