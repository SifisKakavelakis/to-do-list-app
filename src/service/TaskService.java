package service;

import model.Task;
import model.Priority;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskService {

    public Task addTask() throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Create task...");
        System.out.println("Please enter tasks title: ");
        String titleInput = scanner.nextLine();

        System.out.println("Please enter the description of the task: ");
        String descriptionInput = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dueDateParsed = null;
        while (dueDateParsed == null) {
            System.out.println("Please enter due date (yyyy-MM-dd HH:mm): ");
            String dueDateInput = scanner.nextLine();
            try {
                dueDateParsed = LocalDateTime.parse(dueDateInput, formatter);
            } catch (Exception e) {
                System.out.println("Invalid date format! Please try again.");
            }
        }

        Boolean completedInput = null;
        while (completedInput == null) {
        try {
             System.out.println("Please enter the status of the tasks: ");
             completedInput = scanner.nextBoolean();
             scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid answer! Please choose between true or false");
            scanner.nextLine();
        }
    }

        System.out.println("Please enter the tags of your choice separated with commas: ");
        String tagsInput = scanner.nextLine();
        String[] tagsArray = tagsInput.split(",");
        List<String> tagsList = new ArrayList<>();
        for (String tag : tagsArray) {
            tagsList.add(tag.trim());
        }

        Priority priorityEnum = null;
        while (priorityEnum == null) {
            System.out.println("Please enter the priority of the project (LOW, MEDIUM, HIGH): ");
            String priorityInput = scanner.nextLine();
            try {
                 priorityEnum = Priority.valueOf(priorityInput.toUpperCase());
            } catch (Exception e) {
                System.out.println("Invalid answer! Please choose between (LOW, MEDIUM, HIGH)");
            }
        }

        Task task = new Task(titleInput, descriptionInput, dueDateParsed, completedInput,
                tagsList, priorityEnum);

        return task;
    }
}
