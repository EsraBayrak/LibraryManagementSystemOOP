package app;

import java.util.Scanner;

public class LibraryApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Library Management System started.");

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Say hello");
            System.out.println("2) Enter a number");
            System.out.println("0) Exit");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // buffer temizleme

            if (choice == 0) {
                System.out.println("Bye.");
                break;
            } else if (choice == 1) {
                System.out.print("Your name: ");
                String name = sc.nextLine();
                System.out.println("Hello " + name);
            } else if (choice == 2) {
                System.out.print("Enter number: ");
                int x = sc.nextInt();
                System.out.println("You entered: " + x);
            } else {
                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}
