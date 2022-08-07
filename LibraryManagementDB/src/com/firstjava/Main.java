package com.firstjava;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        HomePage();
        }
        public static void HomePage() throws SQLException {
        System.out.println(".............welcome to the library..............");
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Customer");
        System.out.println("2. librarian");
        switch (scanner.nextInt()) {
            case 1:
                User user=new User();
                user.customerHomePage();
                break;
            case 2:
                Librarian librarian=new Librarian();
                librarian.login();
                break;
        }

    }
}
