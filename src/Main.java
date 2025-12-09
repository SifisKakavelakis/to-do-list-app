import model.Task;
import service.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        TaskService taskService = new TaskService();

        System.out.println("Welcome!");
        System.out.println("Let's create your first task.");

        Task task = taskService.addTask();

        System.out.println("\nTask created successfully!");
        System.out.println(task);

        scanner.close();
    }
}
