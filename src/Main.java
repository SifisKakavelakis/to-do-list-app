import model.Task;
import service.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        TaskService service = new TaskService();

        // Φορτώνουμε τα tasks από το JSON
        service.loadTasksFromFile();

        System.out.println("Welcome!");

        while (true) {

            int choice = 0;
            while (choice < 1 || choice > 6) {
                try {
                    System.out.println("Please choose your action.");
                    System.out.println("1. Add Task");
                    System.out.println("2. Show all the Tasks");
                    System.out.println("3. Search Task");
                    System.out.println("4. Update Task");
                    System.out.println("5. Delete Task");
                    System.out.println("6. Exit");

                    choice = scanner.nextInt();
                    scanner.nextLine();

                } catch (Exception e) {
                    System.out.println("Please select a number between 1 and 6!");
                    scanner.nextLine();
                }
            }


            switch (choice) {
                case 1:
                    service.addTask(scanner);
                    // Αποθήκευση όλων των tasks στο JSON
                    service.saveTasksToFile();
                    break;
                case 2:
                    service.showAllTheTasks().forEach(System.out::println);
                    break;
                case 3:
                    service.searchTask(scanner);
                    break;
                case 4:
                    service.updateTask(scanner);
                    break;
                case 5:
                    service.deleteTask(scanner);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
            }
        }
    }
}
