package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.LocalDateTimeAdapter;
import model.Task;
import model.Priority;

import javax.security.auth.kerberos.DelegationPermission;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskService {

    private List<Task> tasks = new ArrayList<>();
    private long idCounter = 1;

    public Task addTask(Scanner scanner) throws Exception {

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

        Task task = new Task(idCounter++, titleInput, descriptionInput, dueDateParsed, completedInput,
                tagsList, priorityEnum);

        tasks.add(task);
        System.out.println("Task created successfully!");
        return task;
    }

    public List<Task> showAllTheTasks() throws Exception {
        return tasks;
    }

    public Task searchTask(Scanner scanner) throws Exception {

        return null;
    }

    public Task updateTask(Scanner scanner) throws Exception {

        return null;
    }

    public Task deleteTask(Scanner scanner) throws Exception {

        return null;
    }

    public void saveTasksToFile() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        try (FileWriter writer = new FileWriter("tasks.json")) {
            gson.toJson(tasks, writer);
            System.out.println("Tasks saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void loadTasksFromFile() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        try (FileReader reader = new FileReader("tasks.json")) {
            Type taskListType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> loadedTasks = gson.fromJson(reader, taskListType);

            if (loadedTasks != null && !loadedTasks.isEmpty()) {
                tasks = loadedTasks;
                // idCounter = max id + 1
                idCounter = tasks.stream()
                        .mapToLong(Task::getId)
                        .max()
                        .orElse(0) + 1;
            } else {
                idCounter = 1; // Αν δεν υπάρχει κανένα task
            }

            System.out.println("Tasks loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("No existing task file found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
}
