package org.gym;

import org.gym.data.GymRepository;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Gym gym = new Gym("Power Gym");

    private static int nextVisitorId = 1;
    private static int nextMembershipId = 1;
    private static int nextTrainerId= 1;

    private static final Scanner scanner = new Scanner(System.in);

    private static  final String filePath = "test_data/visitors.txt";
    private static final GymRepository repository = new GymRepository(filePath);

    public static void main(String[] args) {
        showMainMenu();
    }

    // =========================
    // MAIN MENU
    // =========================

    public static void showMainMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n=== GYM SYSTEM ===");
            System.out.println("1. Manage Visitors");
            System.out.println("2. Manage Trainers");
            System.out.println("3. Manage Data");
            System.out.println("0. Exit");

            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    manageVisitorsMenu();
                    break;

                case 2:
                    manageTrainersMenu();
                    break;

                case 3:
                    manageDataMenu();
                    break;

                case 0:
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // VISITORS MENU

    public static void manageVisitorsMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n=== MANAGE VISITORS ===");
            System.out.println("1. Add visitor");
            System.out.println("2. Remove visitor");
            System.out.println("3. Show visitors");
            System.out.println("4. Find visitor by ID");
            System.out.println("5. Assign trainer to a visitor");
            System.out.println("0. Back");

            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter visitors name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter membership duration (days): ");
                    int days = readInt();
                    LocalDate start = LocalDate.now();
                    LocalDate end = start.plusDays(days);
                    Membership membership = new Membership(nextMembershipId, start, end, true);
                    nextMembershipId++;

                    Visitor visitor = new Visitor(nextVisitorId, name, membership);
                    nextVisitorId++;
                    gym.addVisitor(visitor);
                    break;

                case 2:
                    System.out.print("Enter visitor ID: ");

                    int idRemove = readInt();

                    boolean removed = gym.removeVisitor(idRemove);

                    if (removed) {
                        System.out.println("Visitor removed!");
                    }
                    else {
                        System.out.println("Visitor not found!");
                    }
                    break;

                case 3:
                    List<Visitor> visitors = gym.getVisitors();
                    for(Visitor v : visitors)
                    {
                        System.out.println(v.getName());
                    }
                    break;

                case 4:
                    System.out.print("Enter visitor ID: ");
                    int idFind = readInt();

                    Visitor visitorFind = gym.getVisitorById(idFind);

                    if (visitorFind == null) {

                        System.out.println("Visitor not found!");
                        return;
                    }

                    System.out.println("\n=== VISITOR INFO ===");
                    System.out.println("ID: " + visitorFind.getId());
                    System.out.println("Name: " + visitorFind.getName());

                    if (visitorFind.getMembership() != null) {

                        System.out.println("Membership valid: " + visitorFind.getMembership().isValid());
                    }
                    visitorProfileMenu(visitorFind);
                    break;
                case 5:
                    System.out.print("Enter visitor ID: ");
                    int idAssignV = readInt();
                    if(gym.getVisitorById(idAssignV) == null)
                    {
                        System.out.println("Visitor not found.");
                        break;
                    }

                    System.out.print("Enter trainer ID: ");
                    int idAssignT = readInt();
                    if(gym.getTrainerById(idAssignT) == null)
                    {
                        System.out.println("Trainer not found.");
                        break;
                    }
                    gym.assignTrainer(idAssignV, idAssignT);

                    System.out.println("Trainer assigned to a visitor!");
                    break;


                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // TRAINERS MENU

    public static void manageTrainersMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n=== MANAGE TRAINERS ===");
            System.out.println("1. Add trainer");
            System.out.println("2. Remove trainer");
            System.out.println("3. Show trainers");
            System.out.println("0. Back");

            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter trainers name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter trainers name: ");
                    String spec = scanner.nextLine();

                    Trainer trainer = new Trainer(nextTrainerId, name, spec);
                    nextTrainerId++;
                    gym.addTrainer(trainer);
                    break;

                case 2:
                    System.out.print("Enter trainer ID: ");
                    int idRemove = readInt();

                    boolean removed = gym.removeTrainer(idRemove);

                    if (removed) {
                        System.out.println("Trainer removed!");
                    }
                    else {
                        System.out.println("Trainer not found!");
                    }
                    break;

                case 3:
                    List<Trainer> trainers = gym.getTrainers();
                    for(Trainer t : trainers)
                    {
                        System.out.println(t.getName());
                    }
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // VISITOR PROFILE MENU

    public static void visitorProfileMenu(Visitor visitor) {

        boolean running = true;

        while (running) {

            //System.out.println("\n=== VISITOR PROFILE ===");
            System.out.println("1. Visit gym");
            System.out.println("2. Show visits history");
            System.out.println("0. Back");

            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    if(!visitor.visitGym())
                    {
                        System.out.println("Visit failed.");
                        break;
                    }
                    System.out.println("Visit successful.");
                    break;

                case 2:
                    List<LocalDateTime> visitsTime = visitor.getVisitHistory();
                    if(visitsTime.isEmpty())
                    {
                        System.out.println("No visits recorded");
                    }
                    for(LocalDateTime time : visitsTime)
                    {
                        System.out.println(time);
                    }
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // DATA MENU

    public static void manageDataMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n=== MANAGE DATA ===");
            System.out.println("1. Export visitors");
            System.out.println("2. Export visitors sorted by name");
            System.out.println("3. Export visitors sorted by ID");
            System.out.println("4. Import visitors");
            System.out.println("0. Back");

            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    try {
                        repository.exportVisitors(
                                gym.getVisitors(),
                                null,
                                false
                        );

                        System.out.println("Visitors exported!");
                    }
                    catch (Exception e) {

                        System.out.println("Export failed!");
                    }
                    break;

                case 2:
                    try {
                        repository.exportVisitors(
                                gym.getVisitors(),
                                Comparator.comparing(Visitor::getName),
                                true
                        );

                        System.out.println("Visitors exported and sorted by ID!");
                    }
                    catch (Exception e) {

                        System.out.println("Export failed!");
                    }
                    break;

                case 3:
                    try {
                        repository.exportVisitors(
                                gym.getVisitors(),
                                Comparator.comparing(Visitor::getId),
                                true
                        );

                        System.out.println("Visitors exported and sorted by ID!");
                    }
                    catch (Exception e) {

                        System.out.println("Export failed!");
                    }
                    break;

                case 4:
                    try {
                        List<Visitor> imported = repository.importVisitors();

                        for (Visitor visitor : imported) {

                            gym.addVisitor(visitor);
                        }

                        System.out.println("Visitors imported!");
                    }
                    catch (Exception e) {

                        System.out.println("Import failed!");
                    }
                    break;
                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // SAFE INTEGER INPUT

    public static int readInt() {

        while (true) {

            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            }
            catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}