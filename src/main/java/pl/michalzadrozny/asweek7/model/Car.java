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

    private static long counter = 0;

    @NotNull
    private long carId;
    @NotNull
    private String mark;
    @NotNull
    private String model;
    @NotNull
    private String color;

    public Car(@NotNull long carId, @NotNull String mark, @NotNull String model, @NotNull String color) {
        this.carId = carId;
        this.mark = mark;
        this.model = model;
        this.color = color;
    }

    public Car() {
    }
}
