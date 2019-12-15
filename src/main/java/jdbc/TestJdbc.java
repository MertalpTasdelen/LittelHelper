package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {
    public static void main(String[] args) {

        String jdbc = "jdbc:mysql://localhost/telegram_bot?useSSL=false";
        String user = "root";
        String password = "111333456alp";


        try{
            System.out.println("Connecting to db");

            Connection myConn = DriverManager.getConnection(jdbc,user,password);

            System.out.println("Scuccesfull");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}


