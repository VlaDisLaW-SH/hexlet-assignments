package exercise.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// BEGIN
@Getter
@Setter
public class ProductUpdateDTO {
    private Long id;
    private String title;
    private Integer price;
    private LocalDate updatedAt;
    private LocalDate createdAt;
}
// END
