package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;


// BEGIN
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @Bean
    public Daytime getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        LocalTime timeOfDay = LocalTime.of(6, 0);
        LocalTime timeOfNight = LocalTime.of(22, 0);

        if (currentTime.isAfter(timeOfDay) && currentTime.isBefore(timeOfNight)) {
            return new Day();
        } else {
            return new Night();
        }
    }
    // END
}
