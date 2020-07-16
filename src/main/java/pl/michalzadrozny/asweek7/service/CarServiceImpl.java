package pl.michalzadrozny.asweek7.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.michalzadrozny.asweek7.dao.CarDaoImpl;
import pl.michalzadrozny.asweek7.model.Car;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
public class CarServiceImpl implements CarService {

    CarDaoImpl carDao;

    public CarServiceImpl(CarDaoImpl carDao) {
        this.carDao = carDao;
    }

//    @Override
//    public List<Car> findCarsByColor(String color) {
//        return listOfCars.stream().filter(car -> car.getColor().equalsIgnoreCase(color)).collect(Collectors.toList());
//    }

    @Override
    public Car findCarById(long id) {
        return carDao.geCarById(id);
    }

//    @Override
//    public Optional<Car> findEqualCar(Car newCar) {
//        return listOfCars.stream().filter(car -> car.getCarId() == newCar.getCarId()).findFirst();
//    }

    @Override
    public void add(Car car) {
        carDao.saveCar(car);
    }

    @Override
    public void delete(Car car) {
        carDao.deleteCar(car.getCarId());
    }
}
