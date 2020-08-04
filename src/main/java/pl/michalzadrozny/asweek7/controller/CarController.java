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
        log.info("Inside method getCars()");
        List<Car> carList = carDao.findAll();
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
            log.info("Car to edit: "+foundCar.toString());

            return "car-form";
        } else {
            return "not-found";
        }

    }

    @PostMapping("/save")
    public String saveAddedCar(long carId, String mark, String model, String color, long productionYear){
        log.info("Inside method saveAddedCar()");

        Car oldCar = carDao.geCarById(carId);

        if (oldCar != null) {
            oldCar.setMark(mark);
            oldCar.setModel(model);
            oldCar.setColor(color);
            oldCar.setProductionYear(productionYear);
            carDao.updateCar(oldCar);
        }else{
            carDao.saveCar(new Car(carId,mark,model,color,productionYear));
        }

        return "redirect:/cars";
    }



//    @PostMapping("/{id}/save")
//    public String saveCar(@PathVariable long id, String mark, String model, String color, long productionYear) {
//
//        Car oldCar = carDao.geCarById(id);
//
//        if (!oldCar.equals(null)) {
//            oldCar.setMark(mark);
//            oldCar.setModel(model);
//            oldCar.setColor(color);
//            oldCar.setProductionYear(productionYear);
//            carDao.updateCar(oldCar);
//        }else{
//            carDao.saveCar(new Car(id,mark,model,color,productionYear));
//        }
//
//        return "redirect:/cars";
//    }

    @GetMapping("/addCar")
    public String addCar(Model model) {
        log.info("Inside method addCar()");

        Car car = new Car();
        model.addAttribute("car",car);
        return "car-form";
    }

    @GetMapping("/{id}/delete")
    public String deleteCar(@PathVariable long id){
        log.info("Inside method deleteCar()");

        Car foundCar = carDao.geCarById(id);

        if (!foundCar.equals(null)) {
            log.info("Car to delete: "+foundCar.toString());
            carDao.deleteCar(id);
            return "redirect:/cars";
        }
        return "not-found";
    }
}
