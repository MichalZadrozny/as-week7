package pl.michalzadrozny.asweek7.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.michalzadrozny.asweek7.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class CarDaoImpl implements CarDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
//        saveCar(new Car(1, "Fiat", "126p", "Yellow", 1995));
//        saveCar(new Car(2, "Citroen", "Saxo", "Silver", 2001));
//        saveCar(new Car(3, "Renaut", "Megane", "Blue", 2015));

//        updateCar(new Car(4L,"Ford", "Musgang", "Red"));
//        deleteCar(1);
//        findAll().forEach(System.out::println);
//        System.out.println(geCarById(2));
    }

    @Override
    public void saveCar(Car car) {
        String sql = "INSERT INTO cars VALUES(?, ?, ?, ?,?)";
        log.info("Saving car to the database");
        jdbcTemplate.update(sql, car.getCarId(), car.getMark(), car.getModel(), car.getColor(), car.getProductionYear());
        log.info("Saving car completed");
    }

    @Override
    public List<Car> findAll() {
        List<Car> carList = new ArrayList<>();

        String sql = "SELECT * FROM cars";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        return maps.stream().map(element -> new Car(
                Long.parseLong(String.valueOf(element.get("car_id"))),
                String.valueOf(element.get("mark")),
                String.valueOf(element.get("model")),
                String.valueOf(element.get("color")),
                Long.parseLong(String.valueOf(element.get("production_year")))
        )).collect(Collectors.toList());

//        maps.forEach(element -> carList.add(new Car(
//                Long.parseLong(String.valueOf(element.get("car_id"))),
//                String.valueOf(element.get("mark")),
//                String.valueOf(element.get("model")),
//                String.valueOf(element.get("color")),
//                Long.parseLong(String.valueOf(element.get("production_year")))
//        )));
//
//        return carList;
    }

    @Override
    public void updateCar(Car newCar) {
        String sql = "UPDATE cars SET cars.mark=?, cars.model=?, cars.color=?, cars.production_year=? WHERE cars.car_id=?";
        jdbcTemplate.update(sql, newCar.getMark(), newCar.getModel(), newCar.getColor(), newCar.getProductionYear(), newCar.getCarId());
    }

    @Override
    public void deleteCar(long id) {
        String sql = "DELETE FROM cars WHERE car_id=?";
        jdbcTemplate.update(sql, id);
    }

//    @Override
//    public Car geCarById(long id) {
//        String sql = "SELECT * FROM cars WHERE car_id=?";
//        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> new Car(resultSet.getLong("car_id"), resultSet.getString("mark"), resultSet.getString("model"), resultSet.getString("color"), resultSet.getLong("production_year")), id);
//    }

    @Override
    public Car geCarById(long id) {
        String sql = "SELECT * FROM cars WHERE car_id=?";
        List<Car> carList = jdbcTemplate.query(sql, (resultSet, i) -> new Car(resultSet.getLong("car_id"), resultSet.getString("mark"), resultSet.getString("model"), resultSet.getString("color"), resultSet.getLong("production_year")), id);

        if(carList.isEmpty()){
            return null;
        }else if(carList.size() == 1){
            return carList.get(0);
        }else {
            return carList.get(0);
        }
    }
}
