package edu.agh.sharedshoppinglist.controller;

import edu.agh.sharedshoppinglist.dto.request.LoginForm;
import edu.agh.sharedshoppinglist.dto.request.RegisterForm;
import edu.agh.sharedshoppinglist.dto.response.LoginResponse;
import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController extends AbstractExceptionHandler {

    UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerUser(@RequestBody RegisterForm form) throws ApplicationException {
        userService.registerUser(form.getLogin(), form.getPassword(), form.getEmail(), form.getPhone());
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginForm form) throws ApplicationException {
        return new LoginResponse(userService.loginUser(form.getLogin(), form.getPassword()));
    }
}
