import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class LoadContentFromUser {

    private Scanner scanner = new Scanner(System.in);

    public Car loadFullICarInfo() {
        Car car = null;
        System.out.println("Registration number:");
        String regNr = scanner.nextLine();
        System.out.println("Millage");
        Long age = Long.parseLong(scanner.nextLine());
        System.out.println("Brand and the model");
        String brandModel = scanner.nextLine();
        System.out.println("Year of production");
        Integer yearOfProduction = Integer.parseInt(scanner.nextLine());
        System.out.println("Car type:" + Arrays.toString(CarType.values()));
        try {
            CarType carType = CarType.valueOf(scanner.nextLine().toUpperCase());
            System.out.println("Owners name");
            String ownerName = scanner.nextLine();
            car =  new Car(null, regNr, age, brandModel, yearOfProduction, carType, ownerName);
        } catch (IllegalArgumentException iae) {
            System.err.println("Wrong type! Try again");
        }
        return car;
    }

    public RepairOrder loadFullRepairOrderInfo(){
        RepairOrder repairOrder;
        System.out.println("Order content:");
        String contentOreder = scanner.nextLine();
        System.out.println("Car id:");
        Long carId = Long.parseLong(scanner.nextLine());

        repairOrder = new RepairOrder(contentOreder, carId);

        return repairOrder;
    }


    public String loadSingleInfo(String info){
        System.out.println("Please put "+ info);
        return scanner.nextLine();
    }
}
