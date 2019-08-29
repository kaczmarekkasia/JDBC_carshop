

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepairOrderDao {

    private MySqlConnection mySqlConnection;

    public RepairOrderDao() throws IOException, SQLException {
        mySqlConnection = new MySqlConnection();

        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(RepairOrderQuieries.CREATE_TABLE_QUERY)) {
                statement.execute();
            }
        }
    }

    public List<RepairOrder> listByCarId(Long carId) throws SQLException {
        List<RepairOrder> ordersByCarId = new ArrayList<>();
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(RepairOrderQuieries.SELECT_BY_ID)) {

                statement.setLong(1, carId);
                ResultSet resultSet = statement.executeQuery();

                loadMultipleOrdersFromResultSet(ordersByCarId, resultSet);
            }
        }
        return ordersByCarId;
    }

    public void insertRepairOrderByCarId(RepairOrder repairOrder) throws SQLException, IOException {
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(RepairOrderQuieries.INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                CarDao carDao = new CarDao();
                if (carDao.selectById(repairOrder.getCarId()).isPresent()) {
                    statement.setString(1, repairOrder.getOrderContent());
                    statement.setLong(2, repairOrder.getCarId());

                    boolean success = statement.execute();
                    ResultSet resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()) {

                        Long generatedId = resultSet.getLong(1);
                        System.out.println("The repair order has been insert. Id:" + generatedId);
                    }
                } else {
                    System.err.println("There is no car in database with such car id.");
                }

            }
        }
    }

    public void markOrderAsDone(Long orderId, Long carId) throws SQLException {
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(RepairOrderQuieries.REPAIR_ORDER_IS_DONE_QUERY)) {
                statement.setLong(1, orderId);
                statement.setLong(2, carId);

                int affectedRecords = statement.executeUpdate();

                if (affectedRecords > 0) {
                    System.out.println("The order with id: " + orderId + " has been marked as done!");
                } else {
                    System.err.println("Something went wrong...");
                }
            }
        }
    }

    public List<RepairOrder> selectAllUndone() throws SQLException {
        return executeQueryAndAddToList(RepairOrderQuieries.SELECT_UNDONE_ORDERS_QUERY);
    }

    public List<RepairOrder> selectAllDone() throws SQLException {
        return executeQueryAndAddToList(RepairOrderQuieries.SELECT_DONE_ORDERS_QUERY);
    }

    private List<RepairOrder> executeQueryAndAddToList(String query) throws SQLException {
        List<RepairOrder> doneOrders = new ArrayList<>();
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                loadMultipleOrdersFromResultSet(doneOrders, resultSet);
            }
        }
        return doneOrders;
    }

    public List<RepairOrder> selectByDays(int days) throws SQLException {
        List<RepairOrder> selectedByDaysOrders = new ArrayList<>();
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(RepairOrderQuieries.SELECT_ORDERS_BY_DAYS_QUERY)) {

                statement.setInt(1, days);
                ResultSet resultSet = statement.executeQuery();
                loadMultipleOrdersFromResultSet(selectedByDaysOrders, resultSet);

            }
        }
        return selectedByDaysOrders;
    }

    private void loadMultipleOrdersFromResultSet(List<RepairOrder> ordersByCarId, ResultSet resultSet) throws
            SQLException {
        while (resultSet.next()) {
            RepairOrder repairOrder = loadRepairOrderFromResultSet(resultSet);
            ordersByCarId.add(repairOrder);
        }
    }

    private RepairOrder loadRepairOrderFromResultSet(ResultSet resultSet) throws SQLException {
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setId(resultSet.getLong(1));
        repairOrder.setAddDate(resultSet.getObject(2, LocalDateTime.class));
        repairOrder.setDone(resultSet.getBoolean(3));
        repairOrder.setEndDate(resultSet.getObject(4, LocalDateTime.class));
        repairOrder.setOrderContent(resultSet.getString(5));
        repairOrder.setCarId(resultSet.getLong(6));
        return repairOrder;
    }
}
