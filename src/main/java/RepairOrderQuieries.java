public interface RepairOrderQuieries {

    String CREATE_TABLE_QUERY = "create table if not exists `repair_orders`(\n" +
            "`id` INT NOT NULL AUTO_INCREMENT primary key,\n" +
            "`addDate` DATETIME not null,\n" +
            "`isDone` tinyint default 0,\n" +
            "`endDate` DATETIME default null,\n" +
            "`orderContent` varchar(300) not null,\n" +
            "`carId` int not null,\n" +
            "foreign key (carId) references `cars` (id)" +
            ")";

    String SELECT_BY_ID = "select * from `repair_orders` where (id = ? and carId = ?)";

    String INSERT_QUERY = "insert into `repair_orders` (addDate, orderContent, carId)" +
            "values (now(), ?, ?)";

    String REPAIR_ORDER_IS_DONE_QUERY = "update `repair_orders` set `isDone` = 1, `endDate`=NOW() where (id = ? and carId = ?)";
    String SELECT_UNDONE_ORDERS_QUERY = "select * from `repair_orders` where `isDone` = 0 ";
    String SELECT_DONE_ORDERS_QUERY = "select * from `repair_orders` where `isDone` = 1";
    String SELECT_ORDERS_BY_DAYS_QUERY = "select * from `repair_orders` where `addDate` >= now() - interval ? day;";

}
