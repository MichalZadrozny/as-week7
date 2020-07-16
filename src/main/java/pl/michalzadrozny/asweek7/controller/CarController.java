package pl.michalzadrozny.asweek7.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.michalzadrozny.asweek7.dao.CarDao;
import pl.michalzadrozny.asweek7.model.Car;

import java.util.List;
import java.util.Optional;

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
        List<Car> carList = carDao.findAll();
        log.info(carList.toString());
        model.addAttribute("carList", carList);
        return "cars";
    }

    @GetMapping("/{id}")
    public String getCarByID(@PathVariable long id, Model model) {
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
        Car foundCar = carDao.geCarById(id);

        if (!foundCar.equals(null)) {

            model.addAttribute("car", foundCar);
            log.info("Car to edit: "+foundCar.toString());

            return "car-form";
        } else {
            return "not-found";
        }

    }

    @PostMapping("/{id}/save")
    public String saveCar(@PathVariable long id, String mark, String model, String color) {

        Car oldCar = carDao.geCarById(id);

        if (!oldCar.equals(null)) {
            oldCar.setMark(mark);
            oldCar.setModel(model);
            oldCar.setColor(color);
            carDao.updateCar(oldCar);
        }else{
            carDao.saveCar(new Car(id,mark,model,color));
        }

        return "redirect:/cars";
    }

    @GetMapping("/addCar")
    public String addCar(Model model) {
        Car car = new Car();
        model.addAttribute("car",car);
        return "car-form";
    }

    @GetMapping("/{id}/delete")
    public String deleteCar(@PathVariable long id){
        Car foundCar = carDao.geCarById(id);

        if (!foundCar.equals(null)) {
            log.info("Car to delete: "+foundCar.toString());
            carDao.deleteCar(id);
            return "redirect:/cars";
        }
        return "not-found";
    }
}
