import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        LoadContentFromUser loadContentFromUser = new LoadContentFromUser();
        CarDao carDao;
        RepairOrderDao repairOrderDao;
        try {
            carDao = new CarDao();
            repairOrderDao = new RepairOrderDao();
        } catch (SQLException e) {
            System.err.println("Car dao cannot be created. Mysql error.");
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.err.println("Configuration file error.");
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
            return;
        }


        String command;
        do {
            System.out.println("What do you want to do? Add car (a)\n" +
                    "Delete car by id (b)\n" +
                    "Delete car by registration number (c)\n" +
                    "List all cars (d)\n" +
                    "Select car by registration number (e)\n" +
                    "Select car by name (f)\n" +
                    "Select car by brand (g)\n" +
                    "List all repair orders query by car id (h)\n" +
                    "Add new repair order (i)\n" +
                    "Mark repair query as done (j)\n" +
                    "List all undone repair orders (k)\n" +
                    "List all done repair orders (l)\n" +
                    "List ale repair orders from the last X days (k)\n" +
                    "Quit (q)");
            command = scanner.nextLine();

            if (command.equalsIgnoreCase("q")) {
                break;
            }
            try {

                switch (command) {
                    case "a":
                        Car car = loadContentFromUser.loadFullICarInfo();
                        carDao.insertCar(car);
                        break;
                    case "b":
                        String info1 = " ID:";
                        Long id = Long.parseLong(loadContentFromUser.loadSingleInfo(info1));
                        Boolean isDelete = carDao.deleteById(id);
                        System.out.println("Deleted: "+ isDelete);
                        break;
                    case "c":
                        String info2 = " registation number:";
                        String regNr = loadContentFromUser.loadSingleInfo(info2);
                        Boolean isDelete1 = carDao.deleteByRegNr(regNr);
                        System.out.println("Deleted: "+ isDelete1);
                        break;
                    case "d":
                        List<Car> allCars = carDao.listAllCars();
                        for (Car c : allCars) {
                            System.out.println(c);
                        }
                        break;
                    case "e":
                        String info3 = " registration number:";
                        String regNr1 = loadContentFromUser.loadSingleInfo(info3);
                        List<Car> carsByRegNr = carDao.selectByRegNr(regNr1);
                        for (Car c : carsByRegNr) {
                            System.out.println(c);
                        }
                        break;
                    case "f":
                        String info4 = " owners name:";
                        String name = loadContentFromUser.loadSingleInfo(info4);
                        List<Car> carsByName = carDao.selectByName(name);
                        for (Car c : carsByName) {
                            System.out.println(c);
                        }
                        break;
                    case "g":
                        String info5 = " brand:";
                        String brand = loadContentFromUser.loadSingleInfo(info5);
                        List<Car> carsByBrand = carDao.selectByBrand(brand);
                        for (Car c : carsByBrand) {
                            System.out.println(c);
                        }
                        break;
                    case "h":
                        String info6 = " car id:";
                        Long carId = Long.parseLong(loadContentFromUser.loadSingleInfo(info6));
                        List<RepairOrder> ordersById = repairOrderDao.listByCarId(carId);
                        for (RepairOrder r : ordersById) {
                            System.out.println(r);
                        }
                        break;
                    case "i":
                        RepairOrder repairOrder = loadContentFromUser.loadFullRepairOrderInfo();
                        repairOrderDao.insertRepairOrderByCarId(repairOrder);
                        break;
                }
            } catch (IllegalArgumentException iae) {
                System.err.println("Wrong command..try again!\n");
            } catch (SQLException e) {
                System.err.println("Error executing command: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error executing command: " + e.getMessage());
            }
        } while (!command.equalsIgnoreCase("q"));
    }


}
