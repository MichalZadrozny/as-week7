package pl.michalzadrozny.asweek7.dao;

import pl.michalzadrozny.asweek7.model.Car;

import java.util.List;

public interface CarDao {
    void saveCar(Car car);
    List<Car> findAll();
    List<Car> findByYears(int year1, int year2);

    void updateCar(Car nawCar);
    void deleteCar(long id);
    Car geCarById(long id);
}
