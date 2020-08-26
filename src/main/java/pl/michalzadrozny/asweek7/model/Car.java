package pl.michalzadrozny.asweek7.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@Slf4j
@Getter
@Setter
@ToString
public class Car {

    @NotNull(message = "Field cannot be empty")
    private long carId;
    @NotNull(message = "Field cannot be empty")
    private String mark;
    @NotNull(message = "Field cannot be empty")
    private String model;
    @NotNull(message = "Field cannot be empty")
    private String color;
    @NotNull(message = "Field cannot be empty")
    private long productionYear;

    public Car(@NotNull(message = "Field cannot be empty") long carId, @NotNull(message = "Field cannot be empty") String mark,
               @NotNull(message = "Field cannot be empty") String model, @NotNull(message = "Field cannot be empty") String color,
               @NotNull(message = "Field cannot be empty") long productionYear) {
        this.carId = carId;
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.productionYear = productionYear;
    }

    public Car() {
    }
}
