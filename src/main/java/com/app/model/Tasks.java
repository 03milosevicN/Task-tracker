package com.app.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.awt.Desktop;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    /**
     * Task accumulator.
     */
    private static final ArrayList<Tasks> tasks = new ArrayList<>();

    public static ArrayList<Tasks> getTasks() {
        return tasks;
    }
    public static ArrayList<Tasks> getTasksByStatus(Status status) {
        ArrayList<Tasks> filteredTasks = new ArrayList<>();
        for (Tasks task : tasks) {
            if (task.getStatus() == status) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    @Override
    public String toString() {
        return "[id=" + id +
                ", description=" + description +
                ", status=" + status +
                ", createdAt=" + createdAt + "]";
    }

    /**
     * Creates a new task based on a given task description.
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
     * Updates existing task at a valid given ID.
     * 
     * @return updated task.
     */
    public void updateTask(BufferedReader reader) throws IOException {
        System.out.println("Choose which field to update in format of [command] -> [explanation]: \n");
        System.out.println(" 'desc' -> update description. ");
        System.out.println(" 'status' -> update status. ");

        int atThisId;
        String options = reader.readLine();
        switch (options) {
            case "desc":

                System.out.println("Pick description based on task's ID: ");
                atThisId = Integer.parseInt(reader.readLine());

                try {
                    System.out.println("Add new description: ");
                    String newDesc = reader.readLine();
                    tasks.get(atThisId).setDescription(newDesc);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("ID of this task is out of bounds.");
                }
                break;

            case "status":

                System.out.println("Pick status based on task's ID: ");
                atThisId = Integer.parseInt(reader.readLine());

                try {
                    System.out.println("Change status: ");
                    String newStatus = reader.readLine();
                    tasks.get(atThisId).setStatus(Status.valueOf(newStatus));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("ID of this task is out of bounds.");
                }
                break;

            default:
                System.out.println("Invalid field.");
                break;
        }
    }

    /**
     * Deletes task at a valid given ID.
     */
    public void deleteTask(BufferedReader reader) throws IOException {

        System.out.println("Pick task based on it's ID: ");
        int atThisId = Integer.parseInt(reader.readLine());

        try {
            tasks.remove(tasks.get(atThisId));
            System.out.println("Successfully removed task.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ID out of bounds.");
            return;
        }

    }

    /**
     * Lists either all tasks or tasks based on task status.
     * 
     * @return list of tasks.
     */
    public void listTasks(BufferedReader reader) throws IOException {
        System.out.println("Which tasks do you need?");
        String options = reader.readLine();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        switch (options) {
            case "tbd":
                System.out.println("List of TO BE DONE: " + writer.writeValueAsString(getTasksByStatus(Status.ToBeDone)));
                break;
            case "inp":
                System.out.println("List of all IN PROGRESS: " + writer.writeValueAsString(getTasksByStatus(Status.InProgress)));
                break;
            case "done":
                System.out.println("List of all DONE: " + writer.writeValueAsString(getTasksByStatus(Status.Done)));
                break;
            case "all":
                System.out.println("List of all tasks: " + writer.writeValueAsString(getTasks()));
                break;
            default:
                System.out.println("No such status.");
                break;
        }
    }

    public void clear() {
        for (int i = 0; i < 60; ++i) {
            System.out.println("\t\n");
        }
    }

    public void manual() throws IOException {

        Path filePath = Paths.get("INIT.md");

        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

        List<String> subLines = lines.subList(6, 36);

        String markdownSlice = String.join("\n", subLines);

        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownSlice);

        TextContentRenderer renderer = TextContentRenderer.builder().build();
        String text = renderer.render(document);

        System.out.println(text);
    }

    /**
     * Exports current CLI session's tasks to home/Documents folder.
     */
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
