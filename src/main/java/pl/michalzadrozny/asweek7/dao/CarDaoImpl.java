package pl.michalzadrozny.asweek7.dao;

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

@Repository
public class CarDaoImpl implements CarDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
//        saveCar(1,"Fiat", "126p", "Yellow");
//        saveCar(2,"Citroen", "Saxo", "Silver");
//        saveCar(3,"Renaut", "Megane", "Blue");

//        updateCar(new Car(4L,"Ford", "Musgang", "Red"));
//        deleteCar(1);
//        findAll().forEach(System.out::println);
        System.out.println(geCarById(2));
    }

    @Override
    public void saveCar(Car car) {
        String sql = "INSERT INTO cars VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql,car.getCarId(), car.getMark(), car.getModel(), car.getColor());

    }

    @Override
    public List<Car> findAll() {
        List<Car> carList = new ArrayList<>();

        String sql = "SELECT * FROM cars";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        maps.stream().map(element -> new Car(
                Long.parseLong(String.valueOf(element.get("car_id"))),
                String.valueOf(element.get("mark")),
                String.valueOf(element.get("model")),
                String.valueOf(element.get("color"))
        ));

//        maps.forEach(element -> carList.add(new Car(
//                Long.parseLong(String.valueOf(element.get("car_id"))),
//                String.valueOf(element.get("mark")),
//                String.valueOf(element.get("model")),
//                String.valueOf(element.get("color"))
//                )));

        return carList;
    }

    @Override
    public void updateCar(Car newCar) {
        String sql = "UPDATE cars SET cars.mark=?, cars.model=?, cars.color=? WHERE cars.car_id=?";
        jdbcTemplate.update(sql, newCar.getMark(), newCar.getModel(), newCar.getColor(), newCar.getCarId());
    }

    @Override
    public void deleteCar(long id) {
        String sql = "DELETE FROM cars WHERE car_id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Car geCarById(long id) {
        String sql = "SELECT * FROM cars WHERE car_id=?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<Car>() {
            @Override
            public Car mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Car(resultSet.getLong("car_id"), resultSet.getString("mark"), resultSet.getString("model"), resultSet.getString("color"));
            }
        }, id);
    }
}
