package com.firstjava;

import java.sql.*;

public class LibrarianDatabase {
    final static String LOGINCHECK="SELECT librarianid,password FROM librarian WHERE librarianid=? AND password=?";
    final static String INSERTBOOK="INSERT into books (ISBN,bookname,author,count,genre,language)VALUES (?,?,?,?,?,?)";
    final static String DELETEBOOK="DELETE FROM books WHERE ISBN=?";
    final static String DISPLAYBOOK="SELECT * FROM books";
    final static String ADDLIBRARIAN="INSERT INTO librarian(librarianID,Name,phoneNo,password)VALUES (?,?,?,?)";

    public static Connection connection() throws SQLException {


        String Url="jdbc:mysql://localhost:3306/library";
         String UserName="root";
         String Password="1010";
         return DriverManager.getConnection(Url,UserName,Password);
    }


    public static boolean login(String LibrarianId,String Password) throws SQLException {
        Connection con=connection();
        PreparedStatement statement=con.prepareStatement(LOGINCHECK);
        statement.setString(1,LibrarianId);
        statement.setString(2,Password);
        ResultSet resultSet=statement.executeQuery();
        boolean result=resultSet.next();
        con.close();
        return result;
    }

    public static void AddbooksDB(int ISBN,String name,String author,int count,String genre,String language) throws SQLException {
        Connection con=connection();
        PreparedStatement statement=con.prepareStatement(INSERTBOOK);
        statement.setInt(1,ISBN);
        statement.setString(2,name);
        statement.setString(3,author);
        statement.setInt(4,count);
        statement.setString(5,genre);
        statement.setString(6,language);
        statement.executeUpdate();
        con.close();
        System.out.println("BOOK ADDED SUCCESSFULLY");
    }

   public static void RemoveBookDB(int ISBN) throws SQLException {
        Connection con=connection();
        PreparedStatement statement=con.prepareStatement(DELETEBOOK);
        statement.setInt(1,ISBN);
        statement.executeUpdate();
        con.close();
        System.out.println("BOOK REMOVED SUCCESSFULLY");
    }

   public static void AddLibrarianDB(String Name,String phoneNo,String ID,String Password) throws SQLException {
        Connection con=connection();
        PreparedStatement statement=con.prepareStatement(ADDLIBRARIAN);
        statement.setString(1,ID);
        statement.setString(2,Name);
        statement.setString(3,phoneNo);
        statement.setString(4,Password);
        statement.executeUpdate();
        con.close();
        System.out.println("LIBRARIAN ADDED SUCCESSFULLY");
   }

   public static void DisplayBooksDB() throws SQLException {
       Connection con = connection();
       PreparedStatement statement = con.prepareStatement(DISPLAYBOOK);
       ResultSet set = statement.executeQuery();
       while (set.next()) {
           if(set.getRow()==0){
               System.out.println("NO BOOKS");
           }
           System.out.println("\nBOOK ID :" + set.getInt("ISBN") + "\nBOOK NAME :" + set.getString("bookname") + "\nAUTHOR :" + set.getString("author") + "\nLANGUAGE :" + set.getString("language") + "\nGENRE :" + set.getString("genre"));
           System.out.println("----------------");
       }
       set.close();
       con.close();
   }

   public static void BorrowedBooksDB() throws SQLException {
        Connection con =connection();
        Statement statement= con.createStatement();
        ResultSet set=statement.executeQuery("select * from userborrowedbooks");
        while(set.next()) {
            if(set.getRow()==0){
                System.out.println("NO BORROWED BOOKS");
            }
           System.out.println("USER ID :"+set.getString("userid") + "\nBOOK ID :" + set.getInt("ISBN") + "\nRETURN DATE :" + set.getString("returndate"));
           System.out.println("-----------------------");}
        set.close();
        con.close();
    }


   public static void ViewBookCount() throws SQLException {
        Connection con=connection();
        Statement statement=con.createStatement();
       ResultSet resultset= statement.executeQuery("SELECT bookname,count,takencount FROM books");
       while(resultset.next()){
           System.out.println("\nBOOK NAME :"+ resultset.getString(1)+
                              "\nBOOK COUNT :"+resultset.getInt(2)+
                              "\nNO BOOKS TAKEN : "+resultset.getInt(3));
       }
       resultset=statement.executeQuery("select sum(count),sum(takencount) from books");resultset.next();
       System.out.println("THE TOTAL COUNT OF THE BOOK :"+resultset.getInt(1)+
                          "\n THE NO OF BOOKS TAKEN :"+resultset.getInt(2));

       resultset.close();
       con.close();
   }
}
