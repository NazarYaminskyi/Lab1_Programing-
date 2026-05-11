package org.gym;

import org.gym.data.GymRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Gym gym = new Gym("Power Gym");

        GymRepository repository = new GymRepository("visitors.csv");

        while (true) {

            System.out.println("\n=== GYM MENU ===");
            System.out.println("1. Add visitor");
            System.out.println("2. Visit gym");
            System.out.println("3. Show visitors");
            System.out.println("4. Export visitors");
            System.out.println("5. Import visitors");
            System.out.println("0. Exit");

            System.out.print("Choose option: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1 -> {

                    System.out.print("Enter visitor ID: ");
                    int id = scanner.nextInt();

                    System.out.print("Enter visitor name: ");
                    String name = scanner.next();

                    Membership membership = new Membership(
                            id,
                            LocalDate.now(),
                            LocalDate.now().plusDays(30),
                            true
                    );

                    Visitor visitor = new Visitor(id, name, membership);

                    gym.addVisitor(visitor);

                    System.out.println("Visitor added.");
                }

                case 2 -> {

                    System.out.print("Enter visitor ID: ");
                    int id = scanner.nextInt();

                    boolean result = gym.visitGym(id);

                    if (result) {
                        System.out.println("Visitor entered the gym.");
                    } else {
                        System.out.println("Access denied.");
                    }
                }

                case 3 -> {

                    List<Visitor> visitors = gym.getVisitors();

                    if (visitors.isEmpty()) {
                        System.out.println("No visitors.");
                    } else {

                        for (Visitor v : visitors) {
                            System.out.println(v);
                        }
                    }
                }

                case 4 -> {

                    try {

                        System.out.println("1. Export with sorting by name");
                        System.out.println("2. Export with sorting by ID");
                        System.out.println("3. Export without sorting");

                        int exportChoice = scanner.nextInt();

                        switch (exportChoice) {

                            case 1 -> repository.exportVisitors(
                                    gym.getVisitors(),
                                    Comparator.comparing(Visitor::getName),
                                    true
                            );

                            case 2 -> repository.exportVisitors(
                                    gym.getVisitors(),
                                    Comparator.comparingInt(Visitor::getId),
                                    true
                            );

                            case 3 -> repository.exportVisitors(
                                    gym.getVisitors(),
                                    (a, b) -> 0,
                                    true
                            );

                            default -> {
                                System.out.println("Wrong option.");
                                continue;
                            }
                        }

                        System.out.println("Export completed.");

                    } catch (IOException e) {
                        System.out.println("Export error.");
                    }
                }

                case 5 -> {

                    try {

                        List<Visitor> importedVisitors = repository.importVisitors();

                        for (Visitor v : importedVisitors) {
                            gym.addVisitor(v);
                        }

                        System.out.println("Import completed.");

                    } catch (IOException e) {
                        System.out.println("Import error.");
                    }
                }

                case 0 -> {

                    System.out.println("Program ended.");
                    return;
                }

                default -> System.out.println("Wrong option.");
            }
        }
    }
}