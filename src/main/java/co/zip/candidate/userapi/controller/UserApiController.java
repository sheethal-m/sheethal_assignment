package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserRepository userRepository;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @PostMapping
    public ResponseEntity create(@RequestBody User user) {
        String errors = validateCreateUser(user);
        if(errors.length() != 0)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        try {
            User existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null)
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("User with email " + user.getEmail() + " is already registered with us.");

            User userResponse = userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED).body(userResponse);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllUsers() {
        try {
            List<User> userList = userRepository.findAll();
            if (userList.isEmpty() || userList.size() == 0) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @GetMapping("/{id}")
    public ResponseEntity getSingleUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    private String validateCreateUser(User user){
        StringBuilder error = new StringBuilder();
        if(user == null) {
            error.append("Input cannot be null. ");
            return error.toString();
        }
        if(user.getEmail() == null || user.getEmail().trim().length() == 0 || !validateEmail(user.getEmail()))
            error.append("Email is either null, empty or in invalid format. ");
        if(user.getName() == null || user.getName().trim().length() == 0)
            error.append("Name cannot be null or empty. ");
        if(user.getMonthly_expense() == null || user.getMonthly_expense() < 0)
           error.append("Monthly expense cannot be null or negative. ");
        if(user.getMonthly_salary() == null || user.getMonthly_salary() < 0)
            error.append("Monthly salary cannot be null or negative. ");
        return error.toString();
    }

}
