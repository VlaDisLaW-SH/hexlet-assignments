package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
@RequestMapping(path = "/welcome")

public class WelcomeController {
    @Autowired
    private Daytime daytime;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public String index() {
        return "It is " + daytime.getName() + " now! Welcome to Spring!";
    }
}
// END
