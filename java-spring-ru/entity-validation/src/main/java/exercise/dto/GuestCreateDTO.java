package exercise.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// BEGIN
@Getter
@Setter
public class GuestCreateDTO {
    @NotBlank
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^((\\+)+([0-9]){11,13})$")
    private String phoneNumber;

    @NotNull
    @Size(min = 4, max = 4)
    private String clubCard;

    @Future
    private LocalDate cardValidUntil;
}
// END
