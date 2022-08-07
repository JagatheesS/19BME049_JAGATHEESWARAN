package com.firstjava;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Librarian {
    static  Scanner scanner = new Scanner(System.in);
    public  void librarianfunctions() throws SQLException {
        System.out.print("\n1.ADD NEW BOOK\n" +
                "2.REMOVE BOOK\n" +
                "3.Display Books\n" +
                "4.ADD NEW USER\n" +
                "5.REMOVE USER\n" +
                "6.VIEW BOOK COUNT\n" +
                "7.ADD LIBRARIAN\n"+
                "8.LOGOUT\n" +
                "ENTER YOUR CHOICE : ");

        switch (scanner.nextInt()) {
            case 1: Addbooks();librarianfunctions();break;
            case 2: Removebooks();librarianfunctions();break;
            case 3: Displaybooks();librarianfunctions();break;
            case 4: AddUser();librarianfunctions();break;
            case 5: RemoveUser();librarianfunctions();break;
            case 6: LibrarianDatabase.ViewBookCount();librarianfunctions();break;
            case 7: AddLibrarian();librarianfunctions();break;
            case 8: Main.HomePage();
            default:
                throw new IllegalStateException("Unexpected value: " + scanner.nextInt());}
    }
    public  void login() throws SQLException {

        Scanner scanner=new Scanner(System.in);
        System.out.println("LIBRARIAN ID :");
        String librarianid=scanner.next();
        System.out.println("PASSWORD :");
        String password=scanner.next();
        boolean check=LibrarianDatabase.login(librarianid,password);
        if(check){
            librarianfunctions();}
        else
            System.out.println("INVALID USERID/PASSWORD");login();
    }

   public void Addbooks() throws SQLException {
       System.out.print("Enter the book ISBN NUMBER :");
       int ids=scanner.nextInt();
       System.out.print("Enter The Book Name : ");
       String bookName=scanner.next();
       System.out.println("Enter the author of the book :");
       String author=scanner.next();
       System.out.println("Enter the count of the book");
       int count=scanner.nextInt();
       System.out.println("Enter the genre of the book");
       String genre=scanner.next();
       System.out.println("Enter the language of the book");
       String language=scanner.next();
       LibrarianDatabase.AddbooksDB(ids,bookName,author,count,genre,language);
    }

   public void Removebooks() throws SQLException {
       System.out.print("Enter The Book ID to be removed : ");
       int ISBN=scanner.nextInt();
       LibrarianDatabase.RemoveBookDB(ISBN);
   }

   public void Displaybooks() throws SQLException {
       System.out.print("\n1.All BOOKS\n2.BARROWED BOOKS\n3.Back To menu\nEnter your choice : ");
       switch(scanner.nextInt()){
           case 1:LibrarianDatabase.DisplayBooksDB();break;
           case 2:LibrarianDatabase.BorrowedBooksDB();break;
           case 3:librarianfunctions();break;
       }
    }


   public void AddUser() throws SQLException {
       System.out.print("\nEnter the User ID : ");
       String  userId= scanner.next();
       System.out.print("Enter the password : ");
       String password= scanner.next();
       System.out.print("Enter the Name : ");
       String name= scanner.next();
       System.out.print("Enter the Address : ");
       String address= scanner.next();
       System.out.print("Enter the Phone no : ");
       String phno= scanner.next();
       int memberShipPlan=User.ApplyMemberShip();
       LocalDate memberShipStartDate=LocalDate.now();
       LocalDate memberShipEndDate=memberShipStartDate.plusMonths(memberShipPlan);
       UserDatabase.AddUserDB(userId,password,name,address,phno,memberShipPlan,memberShipEndDate,memberShipStartDate);

    }

   public void RemoveUser() throws SQLException {
       System.out.print("\nEnter the UserName to be removed : ");
       String userid=scanner.next();
       System.out.print("Enter the password for confirmation : ");
       String password= scanner.next();
       UserDatabase.RemoveuserDB(userid,password);
   }

    private void AddLibrarian() throws SQLException {
        System.out.println("ENTER THE NAME :");
        String name= scanner.next();
        System.out.println("ENTER THE PHONE NO :");
        String phoneno=scanner.next();
        System.out.println("ENTER THE ID :");
        String id=scanner.next();
        System.out.println("ENTER THE PASSWORD :");
        String password=scanner.next();
        LibrarianDatabase.AddLibrarianDB(name,phoneno,id,password);
   }


}
