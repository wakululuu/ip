package botbot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import botbot.tasks.Deadline;
import botbot.tasks.Event;
import botbot.tasks.Task;
import botbot.tasks.TaskList;
import botbot.tasks.TaskStatus;
import botbot.tasks.Todo;

/**
 * Represents the storage of data from Botbot.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a storage with a specified filepath.
     *
     * @param filePath Filepath where data will be located.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads any existing data at the filepath.
     * If there is no existing data, a new file is created at the filepath.
     *
     * @return List of existing tasks.
     */
    public List<Task> load() {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        assert file.getParentFile().isDirectory() : "Parent directory for data file not created";

        if (file.isFile()) {
            return readDataFile();
        } else {
            return createDataFile(file);
        }
    }

    private List<Task> readDataFile() {
        Scanner sc = createFileScanner();
        assert sc != null : "Scanner not created";
        return extractTasks(sc);
    }

    private Scanner createFileScanner() {
        FileInputStream file;
        try {
            file = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            assert false : "Data file not found";
            return null;
        }
        return new Scanner(file);
    }

    private List<Task> extractTasks(Scanner sc) {
        List<Task> list = new LinkedList<>();

        while (sc.hasNextLine()) {
            String data = sc.nextLine();

            String[] dataArr = data.split("\\|");
            char taskType = dataArr[0].charAt(0);
            TaskStatus taskStatus = TaskStatus.convertToStatus(dataArr[1]);
            String description = dataArr[2];

            switch (taskType) {
            case Deadline.TYPE_CODE:
                String by = dataArr[3];
                list.add(new Deadline(description, taskStatus, by));
                break;

            case Event.TYPE_CODE:
                String at = dataArr[3];
                list.add(new Event(description, taskStatus, at));
                break;

            case Todo.TYPE_CODE:
                list.add(new Todo(description, taskStatus));
                break;

            default:
                assert false : "Invalid task type";
            }
        }

        sc.close();
        return list;
    }

    private List<Task> createDataFile(File file) {
        try {
            file.createNewFile();
            return new LinkedList<>();
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }
        return null;
    }

    /**
     * Saves the current task list in the storage.
     *
     * @param tasks Task list to be saved.
     * @param numOfAttempts Number of previous attempts to save.
     */
    public void save(TaskList tasks, int numOfAttempts) {
        try {
            writeToDataFile(tasks);
        } catch (IOException e) {
            e.printStackTrace();
            retrySave(tasks, numOfAttempts);
        }
    }

    private void writeToDataFile(TaskList tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);

        for (Task task : tasks) {
            List<String> temp = new LinkedList<>();
            temp.add(String.valueOf(task.getType()));
            temp.add(task.getStatus());
            temp.add(task.getDescription());
            if (task instanceof Deadline) {
                temp.add(task.getBy().toString());
            } else if (task instanceof Event) {
                temp.add(task.getAt().toString());
            } else {
                assert task instanceof Todo : "Invalid task type";
            }
            String data = String.join("|", temp);
            fw.write(data + "\n");
        }

        fw.close();
    }

    private void retrySave(TaskList tasks, int numOfAttempts) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        if (!file.isFile()) {
            createDataFile(file);
        }

        if (numOfAttempts <= 2) {
            save(tasks, ++numOfAttempts);
        }
    }
}
