package com.firstjava;

import java.sql.SQLException;
import java.util.Scanner;

public class User {
    static Scanner scanner=new Scanner(System.in);
    Librarian librarian=new Librarian();
    public void customerHomePage() throws SQLException {
        System.out.println("1. New user Registration\n2. Already a usr? login \n3.Home");
        switch (scanner.nextInt()) {
            case 1: librarian.AddUser();customerHomePage();break;
            case 2: login();break;
            case 3: Main.HomePage();break;
        }
    }
    public void customerFunctions(String id) throws SQLException {
      int flag=1;
       while(flag==1){
        System.out.print("\n1.VIEW BOOKs\n2.RENT BOOKs\n3.RETURN BOOKs\n4.VIEW BORRROWED BOOKs\n5.PAY FINE\n6.VIEW PAYMENT DETAILS\n7.MEMBERSHIP DETAILS\n8.LOGOUT\nENTER YOUR CHOICE : ");
        switch(scanner.nextInt()){
            case 1:
                librarian.Displaybooks();break;
            case 2:
                BorrowBook(id);break;
            case 3:
                ReturnBook(id);break;
            case 4:
                Book.DisplayBorrowed(id);break;
            case 5:
                LibrarianDatabase.DisplayBooksDB();break;
            case 6:
                UserDatabase.PaymentDetails(id);break;
            case 7:
                UserDatabase.MemberShipDetails(id);break;
            case 8:
                customerHomePage();break;
        }
           System.out.println("ENTER 1 TO CONTINUE");
          flag = scanner.nextInt();
       }
    }

    public void login() throws SQLException {
        System.out.print("\nEnter the USER ID : ");
        String userId=scanner.next();
        System.out.print("Enter the password : ");
        String userPass=scanner.next();
        boolean login =UserDatabase.LoginDB(userId,userPass);
        if(login){customerFunctions(userId);}
        else{
            System.out.println("INVALID USERID/PASSWORD");
            login();
        }
    }

    public static void BorrowBook(String userid) throws SQLException {
        UserDatabase.CheckMemberShip(userid);
        System.out.println("ENTER THE KEY WORD TO SEARCH IN LIBRARY");
        Book.SearchBook(scanner.next());
        System.out.println("ENTER THE BOOK ISBN NUMBER :");
        int ISBN=scanner.nextInt();
        Book.BorrowBookDB(userid,ISBN);
        
    }
    public  void ReturnBook(String userid) throws SQLException {
        Book.DisplayBorrowed(userid);
        System.out.println("ENTER THE ISBN NUMBER TO RETURN :");
        int ISBN= scanner.nextInt();
        int fine=Book.CheckForFine(userid,ISBN);
        if(fine>0){
            UserDatabase.PayFine(userid,fine);
        }
        Book.ReturnBook(userid,ISBN);

    }



    public static int ApplyMemberShip(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("WILL YOU LIKE JOIN MEMBERSHIP IN THIS LIBRARY");
        System.out.println(".............................................");
        System.out.println("MEMBERSHIP PLANS \n3 MONTH-----200\n6 MONTH-----300\n 1 YEAR-----500");
        System.out.println("ENTER YOUR PLAN:");
        int memberShipPlan= scanner.nextInt();
        System.out.println("PAY THE AMOUNT FOR THIS PLAN");
        int  amount=scanner.nextInt();
        switch (memberShipPlan) {
            case 1: if(amount==500){return 365;}
                    else {System.out.println("PAY THE CORRECT AMOUNT");
                     }break;
            case 6: if(amount==300){return 180;}
                     else {System.out.println("PAY THE CORRECT AMOUNT");}
                     break;
            case 3: if(amount==200){return 90;}
                    else {System.out.println("PAY THE CORRECT AMOUNT");}
                     break;
        }return memberShipPlan;
    }
}
