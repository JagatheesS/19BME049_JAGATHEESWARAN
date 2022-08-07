package com.firstjava;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDate.now;

public class Book {
    final static String SEARCHBOOK = "SELECT * FROM books where genre like ?  OR author like ? OR bookname like ?";
    final static String BORROWBOOK="  SELECT  * FROM books where ISBN = ? && count>takencount";
    final static String DISPLAYBORROWED="SELECT userid, books.bookname,userborrowedbooks.ISBN,returndate,userborrowedbooks.takencount FROM books INNER JOIN  userborrowedbooks ON userborrowedbooks.ISBN=books.ISBN WHERE userid = ?";
    final static String CHECKUSERBORROWED="SELECT * FROM userborrowedbooks WHERE userid = ? AND ISBN = ?";
    final static String RETURNBOOK="DELETE  FROM userborrowedbooks WHERE userid=? AND ISBN= ?";
    final static String UPDATE="UPDATE userborrowedbooks SET takencount=takencount+? where userid= ? AND ISBN= ?";
    final static String UPDATECOUNT="UPDATE books SET takencount=takencount+?  WHERE ISBN=?";
    final static String INSETUSERBORROWED="INSERT INTO  userborrowedbooks(userid,ISBN,borroweddate,returndate,takencount)values(?,?,?,?,?)";


    public static Connection connection() throws SQLException {
        String Url = "jdbc:mysql://localhost:3306/library";
        String UserName = "root";
        String Password = "1010";
        return DriverManager.getConnection(Url, UserName, Password);
    }

     static void SearchBook(String word) throws SQLException {
        Connection con = connection();
        PreparedStatement statement = con.prepareStatement(SEARCHBOOK);
        statement.setString(1, "genre");
        statement.setString(2, word);
        statement.setString(3, word);
       // statement.setString(4,word);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            if(set.getRow()==0){
                System.out.println("NO BOOK MATCHED WITH THIS KEYWORD");
            }
            System.out.println("BOOK ID :" + set.getInt("ISBN") + "\nBOOK NAME :" + set.getString("bookname") + "\nAUTHOR :" + set.getString("author") + "\nLANGUAGE :" + set.getString("language"));
            System.out.println("-----------------------");
          }
        set.close();
        con.close();
    }

     static void BorrowBookDB(String userid,int ISBN) throws SQLException {
         Connection con=connection();
         PreparedStatement statement= con.prepareStatement(BORROWBOOK);
         statement.setString(1,userid);
         ResultSet resultSet= statement.executeQuery();
         resultSet.next();
         LocalDate current= now();
         LocalDate bookreturndate=current.plusDays(10);
         PreparedStatement statement1=con.prepareStatement(CHECKUSERBORROWED);
         statement1.setString(1,userid);
         statement1.setInt(2,ISBN);
         ResultSet set= statement1.executeQuery();
         if(set.next()){
            PreparedStatement state= con.prepareStatement(UPDATE);
            state.setInt(1,1);
            state.setString(2,userid);
            state.setInt(3,ISBN);
            state.executeQuery();
        }else {
            PreparedStatement state= con.prepareStatement(INSETUSERBORROWED);
            state.setString(1,userid);
            state.setInt(2,ISBN);
            state.setDate(3, Date.valueOf(current));
            state.setDate(4, Date.valueOf(bookreturndate));
            state.setInt(5,1);
            state.executeUpdate();
        }
        PreparedStatement statement2=con.prepareStatement(UPDATECOUNT);
        statement2.setInt(1,+1);
        statement2.setString(2,userid);
        statement2.executeUpdate();
        System.out.println("SUCCESFULLY YOU HAVE TAKEN THE BOOK ");
        con.close();
    }

     static void ReturnBook(String userid,int ISBN) throws SQLException {
        Connection con=connection();
        PreparedStatement state=con.prepareStatement(CHECKUSERBORROWED);
        state.setInt(2,ISBN);
        state.setString(1,userid);
        ResultSet set= state.executeQuery();
        set.next();
        if(set.getInt("takencount")==1){
        PreparedStatement statement=con.prepareStatement(RETURNBOOK);
        statement.setString(1,userid);
        statement.setInt(2,ISBN);
        statement.executeUpdate();}
        else{
            System.out.printf("YOU HAVE TAKEN %d BOOKS\n ENTER HOW MANY BOOKS DO YOU RETURN :",set.getInt(6));
            PreparedStatement statem= con.prepareStatement(UPDATE);
            int count=User.scanner.nextInt();
            statem.setInt(1,-count);
            statem.setInt(3,ISBN);
            statem.setString(2,userid);
            statem.executeUpdate();
        }
        PreparedStatement statement1= con.prepareStatement(UPDATECOUNT);
        statement1.setInt(1,-1);
        statement1.setInt(2,ISBN);
        statement1.executeUpdate();
        con.close();
        System.out.println("BOOK RETURNED SUCCESSFULLY");
    }


     static int CheckForFine(String userid,int ISBN) throws SQLException {
        Connection con=connection();
        PreparedStatement statement=con.prepareStatement(CHECKUSERBORROWED);
        statement.setString(1,userid);
        statement.setInt(2,ISBN);
        LocalDate currentdate= now();
        ResultSet set=statement.executeQuery();
        set.next();
        Date returndate1=set.getDate("returndate");
        LocalDate returndate= returndate1.toLocalDate();
        int fine=0;
        long days= ChronoUnit.DAYS.between(currentdate, returndate);
        if(days>10){
            fine= (int) (days-10)*5;}
        else
            fine=0;
        return fine;

    }

     static void DisplayBorrowed(String userid) throws SQLException {
        Connection con = connection();
        PreparedStatement statement=con.prepareStatement(DISPLAYBORROWED);
        statement.setString(1,userid);
        ResultSet set=statement.executeQuery();
        while(set.next()){
            System.out.println(
                    "BOOK ISBN :"+set.getString(3)+
                    "\nBOOK NAME :"+set.getString(2)+
                    "\nRETURN DATE :"+set.getDate(4)+
                    "\n NO OF BOOKS TAKEN :"+set.getInt(5)
            );}
        set.close();
        con.close();




    }
}