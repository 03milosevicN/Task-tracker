package com.app.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.File;
import java.awt.Desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

enum Status {
    ToBeDone,
    InProgress,
    Done
};

public class Tasks {

    private int id;
    private String description;
    private Status status;
    private String createdAt;

    private static int counter = 0;

    public Tasks() {
        this.id = Tasks.counter++;
        this.description = "No initial description";
        this.status = Status.ToBeDone;
        // ! not ISO 8061 compliant.
        this.createdAt = LocalDate.now().toString();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDate.now().toString();
    }

    private static final ArrayList<Tasks> tasks = new ArrayList<>();

    public static ArrayList<Tasks> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "[id=" + id +
                ", description=" + description +
                ", status=" + status +
                ", createdAt=" + createdAt + "]";
    }

    /**
     * Creates a new task.
     * 
     * @return new task.
     */
    public Tasks addTask(BufferedReader reader) throws IOException {
        Tasks task = new Tasks();

        System.out.println("What's on the agenda? ");
        String desc = reader.readLine();
        task.setDescription(desc);

        System.out.println("Checking and setting current time...");
        task.setCreatedAt();

        System.out.println("Task noted! " + task.toString());
        tasks.add(task);

        return task;
    }

    /**
     * Updates existing task.
     * 
     * @return updated task.
     */
    public void updateTask(BufferedReader reader) throws IOException {
        System.out.println("<DEBUG> Updating task... <DEBUG>");

        String options = reader.readLine();
        switch (options) {
            case "desc":
                //? How do you know which exact task you are updating?
                setDescription(options);
                System.out.println("Description updated.");
                break;
            case "status":
                System.out.println("Currently cannot change status.");
                break;
            default:
                System.out.println("Field does not exist.");
                break;
        }
        // based on a switch case choose which field to update. If the updated
        // field is status, write separate task getters based on status and use those in
        // listTasks() switch.
    }

    /**
     * Deletes task.
     */
    public void deleteTask() {
        System.out.println("<DEBUG> Deleting task... <DEBUG>");

    }

    /**
     * Lists either all tasks or tasks based on task status.
     * 
     * @return list of tasks.
     */
    public void listTasks(BufferedReader reader) throws IOException {
        System.out.println("<DEBUG> Listing tasks... <DEBUG>");

        System.out.println("Which tasks do you need?");
        String options = reader.readLine();

        switch (options) {
            case "tbd":
                if (this.getStatus() == Status.ToBeDone) {
                    System.out.println("List of all to-be-done tasks" + getTasks());
                }
                break;
            case "inp":
                if (this.getStatus() == Status.InProgress) {
                    System.out.println("List of all in-progress tasks" + getTasks());
                }
                break;
            case "done":
                if (this.getStatus() == Status.Done) {
                    System.out.println("List of all finished tasks" + getTasks());
                }
                break;
            case "all":
                // TODO: logic belongs in a method.
                ObjectMapper mapper = new ObjectMapper();
                ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
                System.out.println("List of all tasks: " + writer.writeValueAsString(getTasks()));
                break;
            default:
                System.out.println("No such status.");
                break;
        }
    }

    // ! Currently only works for all tasks.
    // ! Exports data to home_path\Documents\.
    public void exportList() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        
        String home_path = System.getProperty("user.home");
        File folder = new File(home_path + "/Documents");

        Desktop.getDesktop().open(folder);
        
        File storeJSON = new File(folder, "tasks.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(storeJSON, getTasks());
    
        System.out.println("Exported to: " + storeJSON.getAbsolutePath());
    }

}
