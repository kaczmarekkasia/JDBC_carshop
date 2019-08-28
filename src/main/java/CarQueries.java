public interface CarQueries {

    String CREATE_TABLE_QUERY = "create table if not exists `cars`(\n" +
            "`id` INT NOT NULL AUTO_INCREMENT primary key,\n" +
            "`regNr` varchar(10) not null,\n" +
            "`millage` int not null,\n" +
            "`brandModel` varchar(220) not null,\n" +
            "`yearOfProduction` int not null,\n" +
            "`carType` varchar(25) not null,\n" +
            "`ownerName` varchar(225) not null\n" +
            ")";


    String INSERT_QUERY = "insert into " +
            "cars(`regNr`, `millage`, `brandModel`, `yearOfProduction`, `carType`, `ownerName`) " +
            "values (?,?,?,?,?,?);";

    String DELETE_BY_ID_QUERY = "delete from `cars` where id = ?";
    String DELETE_BY_REGNR_QUERY = "delete from `cars` where regNr = ?";
    String SELECT_ALL_QUERY = "select * from `cars`;";
    String SELECT_BY_REGNR_QUERY = "select * from `cars` where regNr like ?;";
    String SELECT_BY_NAME = "select * from `cars` where ownerName like ?";
    String SELECT_BY_BRAND = "select * from `cars` where brandModel like ?";

}
