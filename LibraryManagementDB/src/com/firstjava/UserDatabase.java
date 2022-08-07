package com.firstjava;

import java.sql.*;
import java.time.LocalDate;

public class UserDatabase {

   final static String ADDUSER="INSERT INTO userdata (userid,userpassword,name,address,phoneno,membershipplan,membershipenddate,membershipstartdate)VALUES (?,?,?,?,?,?,?,?)";
   final static String DELETEUSER="DELETE FROM userdata where userid=? AND userpassword=?";
   final static String LOGIN="SELECT userid,userpassword FROM userdata WHERE userid=? AND userpassword=?";
   final static String PAYMENT="INSERT INTO paymentdetails (userid,finepayed,date_and_time) VALUES (?,?,?)";
   final static String VIEWPAYMENT="SELECT * FROM paymentdetails WHERE userid=?";
   final static String USERDATA="SELECT * FROM userdata WHERE userid=?";



    public static Connection connection() throws SQLException {


        String Url="jdbc:mysql://localhost:3306/library";
        String UserName="root";
        String Password="1010";
        return DriverManager.getConnection(Url,UserName,Password);
    }

     static boolean LoginDB(String userid,String password) throws SQLException {
        Connection con=connection();
        PreparedStatement statement=con.prepareStatement(LOGIN);
        statement.setString(1,userid);
        statement.setString(2,password);
        ResultSet resultSet=statement.executeQuery();
        boolean result=resultSet.next();
        resultSet.close();
        con.close();
        return result;

     }

     static void AddUserDB(String userid, String password, String name, String address, String phno, int memberShipPlan, LocalDate memberShipEndDate,LocalDate memberShipStartDate) throws SQLException {
        Connection con=connection();
        PreparedStatement statement= con.prepareStatement(ADDUSER);
        statement.setString(1,userid);
        statement.setString(2,password);
        statement.setString(3,name);
        statement.setString(4,address);
        statement.setString(5,phno);
        statement.setInt(6,memberShipPlan);
        statement.setDate(7, Date.valueOf(memberShipEndDate));
        statement.setDate(8, Date.valueOf(memberShipStartDate));
        statement.executeUpdate();
        con.close();
        System.out.println("USER ADDED SUCCESSFULLY");
    }

     static void RemoveuserDB(String userid,String password) throws SQLException {
        Connection con=connection();
        PreparedStatement statement= con.prepareStatement(DELETEUSER);
        statement.setString(1,userid);
        statement.setString(2,password);
        statement.executeUpdate();
        con.close();
        System.out.println("USER REMOVED SUCCESSFULLY");
    }

     static void CheckMemberShip(String userid) throws SQLException {
        Connection con=connection();
        Statement state= con.createStatement();
        Date date= Date.valueOf(LocalDate.now());
        ResultSet set= state.executeQuery("select membershipEndDate from userdata where userid='"+userid+"'");
        set.next();
        Date membershipEndDate=set.getDate(1);
        if(membershipEndDate.compareTo(date)<0) {
            System.out.println("CONTINUE YOUR MEMBERSHIP");
            int plan= User.ApplyMemberShip();
            state.executeUpdate("insert into userdata (membershipstartdate,membershipenddate,membershipplan)values('"+LocalDate.now()+"','"+LocalDate.now().plusDays(plan)+"','"+plan/30+"')");
        }
        set.close();
        con.close();

    }

     static void PayFine(String userid,int fine) throws SQLException {
        Connection con=connection();
        PreparedStatement statement=con.prepareStatement(PAYMENT);
        System.out.println("PAY YOUR FINE AMOUNT : "+fine);
        int amount=User.scanner.nextInt();
        if(amount==fine){
        statement.setString(1,userid);
        statement.setInt(2,amount);
        statement.setDate(3, Date.valueOf(LocalDate.now()));
        statement.executeUpdate();
        }
        else {
            System.out.println("PAY CORRECT AMOUNT ");
            PayFine(userid, fine);
        }
        con.close();
    }

    static void PaymentDetails(String userid) throws SQLException {
        Connection con=connection();
        PreparedStatement statement=con.prepareStatement(VIEWPAYMENT);
        statement.setString(1,userid);
        ResultSet set=statement.executeQuery();
        while(set.next()){
            System.out.println("FINE AMOUNT PAYED :"+ set.getString(2)
                                +"DATE AND TIME OF PAYMENT :"+set.getString(3));
        }
        set.close();
       con.close();
    }

   static void MemberShipDetails(String userid) throws SQLException {
        Connection con=connection();
        PreparedStatement statement=con.prepareStatement(USERDATA);
        statement.setString(1,userid);
        ResultSet set= statement.executeQuery();
        set.next();
       System.out.println("\nUSER ID :"+set.getString("userid")+
                          "\nYOUR MEMBERSHIPIP PLAN "+set.getInt("membershipPlan")/30+" MONTHS"+
                           "\nYOUR MEMEBERSHIP ENDS ON :"+set.getString("membershipEndDate"));
       set.close();
       con.close();

   }



}
