

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CarDao {

    private MySqlConnection mysqlConnection;

    public CarDao() throws IOException, SQLException {
        mysqlConnection = new MySqlConnection();

        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CarQueries.CREATE_TABLE_QUERY)) {
                statement.execute();
            }
        }
    }

    public void insertCar(Car car) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CarQueries.INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, car.getRegNr());
                statement.setLong(2, car.getMillage());
                statement.setString(3, car.getBrandModel());
                statement.setInt(4, car.getYearOfProduction());
                statement.setString(5, String.valueOf(car.getCarType()));
                statement.setString(6, car.getOwnerName());

                boolean success = statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) {

                    Long generatedId = resultSet.getLong(1);
                    System.out.println("The car has been insert. Id:" + generatedId);
                }
            }
        }
    }

    public boolean deleteById(Long id) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CarQueries.DELETE_BY_ID_QUERY)) {
                statement.setLong(1, id);

                int affectedRecords = statement.executeUpdate();

                if (affectedRecords > 0) {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean deleteByRegNr(String regNr) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CarQueries.DELETE_BY_REGNR_QUERY)) {
                statement.setString(1, regNr);

                int affectedRecords = statement.executeUpdate();

                if (affectedRecords > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Car> listAllCars() throws SQLException {
        List<Car> allCarsList = new ArrayList<>();
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CarQueries.SELECT_ALL_QUERY)) {

                ResultSet resultSet = statement.executeQuery();

                loadMultipleCarsFromResultSet(allCarsList, resultSet);

            }
        }
        return allCarsList;
    }

    public List<Car> selectByRegNr(String regNr) throws SQLException {
        return setStringAndExecuteQuery(regNr, CarQueries.SELECT_BY_REGNR_QUERY);
    }


    public List<Car> selectByName(String name) throws SQLException {
        return setStringAndExecuteQuery(name, CarQueries.SELECT_BY_NAME);
    }

    public List<Car> selectByBrand(String brand) throws SQLException {
        return setStringAndExecuteQuery(brand, CarQueries.SELECT_BY_BRAND);
    }

    public Optional<Car> selectById (Long id) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CarQueries.SELECT_BY_ID_QUERY)) {
                    statement.setLong(1, id);
                    ResultSet resultSet = statement.executeQuery();
                 if(resultSet.next()) {
                     return Optional.of(loadCarFromResultSet(resultSet));
                 }
            }
        }
        return Optional.empty();
    }

    private List<Car> setStringAndExecuteQuery(String data, String query) throws SQLException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, "%"+data+"%");
                ResultSet resultSet = statement.executeQuery();

                loadMultipleCarsFromResultSet(cars, resultSet);

            }
        }
        return cars;
    }

    private void loadMultipleCarsFromResultSet(List<Car> allCarsList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Car car = loadCarFromResultSet(resultSet);
            allCarsList.add(car);
        }
    }

    private Car loadCarFromResultSet(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getLong(1));
        car.setRegNr(resultSet.getString(2));
        car.setMillage(resultSet.getLong(3));
        car.setBrandModel(resultSet.getString(4));
        car.setYearOfProduction(resultSet.getInt(5));
        car.setCarType(CarType.valueOf(resultSet.getString(6)));
        car.setOwnerName(resultSet.getString(7));
        return car;
    }
}
