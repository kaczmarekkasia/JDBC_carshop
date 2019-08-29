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
        System.out.println("Start date: yyyy-MM-dd HH:mm");
        LocalDateTime startDate = insertDate();
        System.out.println("Is it done?");
        Boolean isDone = Boolean.parseBoolean(scanner.nextLine());
        System.out.println("End date: yyyy-MM-dd HH:mm");
        LocalDateTime endDate = insertDate();
        System.out.println("Order content:");
        String contentOreder = scanner.nextLine();
        System.out.println("Car id:");
        Long carId = Long.parseLong(scanner.nextLine());

        repairOrder = new RepairOrder(null, startDate, isDone, endDate, contentOreder, carId);

        return repairOrder;
    }

    private LocalDateTime insertDate() {
        LocalDateTime localDateTime = null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            localDateTime = LocalDateTime.parse(scanner.nextLine(), dateTimeFormatter);
            System.out.println(localDateTime);
        }catch (DateTimeParseException dtpe){
            System.out.println("Wrong date format");
        }
        return localDateTime;
    }

    public String loadSingleInfo(String info){
        System.out.println("Please put "+ info);
        return scanner.nextLine();
    }
}
