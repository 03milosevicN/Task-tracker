package com.app;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import com.app.model.Tasks;


public class App {

    public static final InputStreamReader input = new InputStreamReader(System.in);
    public static final BufferedReader reader = new BufferedReader(input);
    public static final Tasks tasks = new Tasks();
    public static final HashMap<String, Runnable> tokens = new HashMap<>();
    static {
        tokens.put("add", () -> {
            try {
                tasks.addTask(reader);
            } catch (IOException e) {
                e.getCause();
            }
        });
        tokens.put("update", () -> {
            try {
                tasks.updateTask(reader);
            } catch (IOException e) {
                e.getCause();
            }
        });
        tokens.put("delete", () -> {
            try {
                tasks.deleteTask(reader);
            } catch (IOException e) {
                e.getCause();
            }
        });
        tokens.put("list", () -> {
            try {
                tasks.listTasks(reader);
            } catch (IOException e) {
                e.getCause();
            }
        });
        tokens.put("export", () -> {
            try {
                tasks.exportList();
            } catch (IOException e) {
                e.getCause();
            }
        });
    }


    public static void main(String[] args) throws IOException { runPrompt(); }

    /**
     * REPL, prompts for existing mapped tasks.
     * 
     * @throws IOException
     */
    private static void runPrompt() throws IOException {

        for (;;) {
            System.out.print("> ");

            String line = reader.readLine();
            if (line == null || line.equalsIgnoreCase("exit")) {
                break;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            run(line);
        }

    }


    private static void run(String source) {
        switch (source) {
            case "add":
                tokens.get("add").run();
                break;
            case "update":
                tokens.get("update").run();
                break;
            case "delete":
                tokens.get("delete").run();
                break;
            case "list":
                tokens.get("list").run();
                break;
            case "export":
                tokens.get("export").run();
                break;
            default:
                System.out.println("Invalid method.");
                break;
        }
    }



}
