package pl.michalzadrozny.asweek7.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.michalzadrozny.asweek7.dao.CarDao;
import pl.michalzadrozny.asweek7.model.Car;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cars")
public class CarController {

    CarDao carDao;

    @Autowired
    public CarController(CarDao carDao) {
        this.carDao = carDao;
    }

    @GetMapping
    public String getCars(Model model) {
        log.info("Inside method getCars()");
        List<Car> carList = carDao.findAll();
        log.info(carList.toString());
        model.addAttribute("carList", carList);
        return "cars";
    }

    @PostMapping("/year")
    public String getCarsByYear(Model model, @RequestParam int year1, @RequestParam int year2) {
        log.info("Inside method getCarsByYear()");

        List<Car> carList = carDao.findByYears(year1, year2);

        log.info(carList.toString());
        model.addAttribute("carList", carList);
        return "cars";
    }


    @GetMapping("/{id}")
    public String getCarByID(@PathVariable long id, Model model) {
        log.info("Inside method getCarByID()");

        Car foundCar = carDao.geCarById(id);

        if (!foundCar.equals(null)) {

            model.addAttribute("car", foundCar);

            return "car-info";
        } else {
            return "not-found";
        }

    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable long id, Model model) {
        log.info("Inside method editCar()");

        Car foundCar = carDao.geCarById(id);

        if (!foundCar.equals(null)) {

            model.addAttribute("car", foundCar);
            model.addAttribute("edit", "/cars/save/edit");
            log.info("Car to edit: " + foundCar.toString());

            return "car-form";
        } else {
            return "not-found";
        }

    }

    @PostMapping("/save/{task}")
    public String saveCar(@PathVariable String task, long carId, String mark, String model, String color, long productionYear) {
        log.info("Inside method saveAddedCar()");
        log.info(task);
        Car oldCar = carDao.geCarById(carId);

        if (task.equals("new")) {
            if (oldCar != null) {
                return "car-form";
            } else {
                carDao.saveCar(new Car(carId, mark, model, color, productionYear));
            }
        } else if (task.equals("edit")) {
            if (oldCar != null) {
                oldCar.setMark(mark);
                oldCar.setModel(model);
                oldCar.setColor(color);
                oldCar.setProductionYear(productionYear);
                carDao.updateCar(oldCar);
            } else {
                carDao.saveCar(new Car(carId, mark, model, color, productionYear));
            }
        } else {
            log.warn("Undefined task!!!");
        }

        return "redirect:/cars";
    }

    @GetMapping("/addCar")
    public String addCar(Model model) {
        log.info("Inside method addCar()");

        Car car = new Car();
        model.addAttribute("car", car);
        model.addAttribute("edit", "/cars/save/new");
        return "car-form";
    }

    @GetMapping("/{id}/delete")
    public String deleteCar(@PathVariable long id) {
        log.info("Inside method deleteCar()");

        Car foundCar = carDao.geCarById(id);

        if (!foundCar.equals(null)) {
            log.info("Car to delete: " + foundCar.toString());
            carDao.deleteCar(id);
            return "redirect:/cars";
        }
        return "not-found";
    }
}
