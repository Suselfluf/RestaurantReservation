package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DataBaseHandler {

    protected static String DRIVER = "com.mysql.cj.jdbc.Driver";
    protected static String DATABASEURL = "jdbc:mysql://localhost:3306/RestaurantDB";
    protected static String USERNAME = "root";
    protected static String PASSWORD = "dl1526ld";

    public static void main(String[] args) {
	// write your code here

        getConnection();
    }

    public static Connection getConnection() {
        try{

            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(DATABASEURL, USERNAME, PASSWORD);
            System.out.println("DatabaseConnected");
            return conn;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

//    public void signUpVisitors( String date_of_reserv, Integer num_of_tables, Integer number_of_visitors){         // Переделать!!!! на входе 3 параметра айди сделал автоувеличивающимся
//        String userReservation = "INSERT INTO Booking values (?,?,?,?)";    /// values (?,?,?,?) чтобы потом задать в prepareStatemnt
//        System.out.println("signUpVisitoirs has been awoken");                                                      //  Example insert into booking (num_of_tables, date_of_reserv, number_of_visitors) values("2","2021-12-22 15:00:00", 1);
//        try {
//            PreparedStatement prSt = getConnection().prepareStatement(userReservation);
//            prSt.setString(1, reservationId); //Обозначаем какая из переменных в массиве параметров чем являкется
//            prSt.setInt(2, num_of_tables);
//            prSt.setString(3, date_of_reserv);
//            prSt.setInt(4, number_of_visitors);
//
//            prSt.executeUpdate(); // Running query for adding infomation
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    public ResultSet getReservation(){
        ResultSet resSet = null;
        String select = "SELECT * FROM Booking";

        try {
            PreparedStatement prSt = getConnection().prepareStatement(select);

            resSet = prSt.executeQuery();    // Running query for getting information
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resSet;
    }


    public ResultSet reservationDependsOnDate(String inputDate){
        ResultSet resSet = null;
        String select = "SELECT * FROM Booking WHERE date_of_reserv like ?";

        try{
            PreparedStatement prSt = getConnection().prepareStatement(select);
            prSt.setString(1, inputDate);
            resSet = prSt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resSet;
    }


    public ResultSet getResvsDepsOnTable(String date, String tableNum){
        ResultSet resSet = null;
        String select = "SELECT * FROM Booking WHERE num_of_tables like ? AND date_of_reserv like ?";

        try{
            PreparedStatement prSt = getConnection().prepareStatement(select);
            prSt.setString(1, tableNum);
            prSt.setString(2,date);
            resSet = prSt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resSet;
    }


}
