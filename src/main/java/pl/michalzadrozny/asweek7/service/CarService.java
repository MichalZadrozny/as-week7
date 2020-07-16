package pl.michalzadrozny.asweek7.service;

import pl.michalzadrozny.asweek7.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

//    List<Car> findCarsByColor(String color);
    Car findCarById(long id);
//    Optional<Car> findEqualCar(Car car);
    void add(Car car);
    void delete(Car car);
}
