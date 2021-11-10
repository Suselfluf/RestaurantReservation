package com.company;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.mysql.cj.log.Log;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.lang3.SerializationUtils;


class Server {


    public static void main(String[] args) throws IOException {
        int serverPort = 8000;


        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        System.out.println("Server has been initialized");


        server.setExecutor(null); // creates a default executor
        server.start();


        server.createContext("/api/inc", new MyHandler());


    }

    static class MyHandler implements HttpHandler {
        AtomicReference<Integer> users = new AtomicReference<>(0); // Value of users


        public class Cat {

            public String name; // имя
            public int age; // возраст

            // Конструктор
            public Cat(){

            }
        }


        @Override
        public void handle(HttpExchange exchange) throws IOException { //HttpExchange - learn about that class

            System.out.println("\n------------------");

            exchange.getResponseHeaders().set("Content-type", "application/json");

            GsonBuilder builder = new GsonBuilder();  //GSON json converter library
            Gson gson = builder.create();



            OutputStream output = exchange.getResponseBody();
            InputStream input = exchange.getRequestBody(); //getting input stream




            DataBaseHandler dbHandler = new DataBaseHandler();
//            dbHandler.signUpVisitors("RV-02", 2, "2021-07-21 22:00:00", 2);  //Insert data


            byte[] inputData = input.readAllBytes();
            System.out.println(new String(inputData));


            List<Object> respArray = new ArrayList<Object>();

            try{
                ResultSet rs = dbHandler.getReservation();  // Getting respond from DB query

                while (rs.next()) {
                    String reservationId = rs.getString("reservationId");       //Getting Id
                    int num_of_tables = rs.getInt("num_of_tables");             //Getting Table's nubmer
                    Timestamp date_of_reserv = rs.getTimestamp("date_of_reserv");         //Getting Date of reservation
                    int number_of_visitors = rs.getInt("number_of_visitors");   //Getting number of visitors
//                    System.out.println(reservationId + ", " + num_of_tables + ", " + date_of_reserv +
//                            ", " + number_of_visitors);
                    reservationResponse resResp = new reservationResponse(reservationId, num_of_tables, date_of_reserv, number_of_visitors);
                    respArray.add(resResp);
                }
            }catch (SQLException e){
                System.out.println(e);
            }



            System.out.println(respArray.getClass().getSimpleName());

            String resArray = gson.toJson(respArray);
            System.out.println(resArray);

            byte[] byteArrray = resArray.getBytes();

//            System.out.println(byteArrray);
//            System.out.println(byteArrray2);


            exchange.sendResponseHeaders(200, byteArrray.length);

            output.write(byteArrray);






//            byte[] data = SerializationUtils.serialize((Serializable) murzik); // New libray to parce object to bytes
//            System.out.println(data);




            output.flush();
            exchange.close();

        }
    }


}