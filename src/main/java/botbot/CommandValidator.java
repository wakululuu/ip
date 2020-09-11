package botbot;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import botbot.commands.Command;
import botbot.commands.DeadlineCommand;
import botbot.commands.DeleteCommand;
import botbot.commands.EventCommand;
import botbot.commands.FindCommand;
import botbot.commands.InvalidCommand;
import botbot.commands.MarkAsDoneCommand;
import botbot.commands.TodoCommand;
import botbot.tasks.Deadline;
import botbot.tasks.Event;

/**
 * Checker for the validity of user input as Botbot commands.
 */
public class CommandValidator {
    public static final String ERROR_MESSAGE_NO_SUCH_COMMAND = "sorry, I don't know what that means!";
    
    private static final String ERROR_MESSAGE_EMPTY = "%s cannot be empty!";
    private static final String ERROR_MESSAGE_EMPTY_DESCRIPTION = String.format(ERROR_MESSAGE_EMPTY,
            "the description of a task");
    private static final String ERROR_MESSAGE_EMPTY_SEARCH = String.format(ERROR_MESSAGE_EMPTY,
            "the search keyword");
    private static final String ERROR_MESSAGE_EMPTY_TASK_ID = String.format(ERROR_MESSAGE_EMPTY,
            "the task number to be %s");
    private static final String ERROR_MESSAGE_EMPTY_TASK_ID_DELETE = String.format(ERROR_MESSAGE_EMPTY_TASK_ID,
            "deleted");
    private static final String ERROR_MESSAGE_EMPTY_TASK_ID_DONE = String.format(ERROR_MESSAGE_EMPTY_TASK_ID,
            "marked as done");

    private static final String ERROR_MESSAGE_INVALID_FORMAT = "invalid format! please follow '%s'!";
    private static final String ERROR_MESSAGE_INVALID_FORMAT_DEADLINE = String.format(ERROR_MESSAGE_INVALID_FORMAT,
            Deadline.COMMAND_FORMAT);
    private static final String ERROR_MESSAGE_INVALID_FORMAT_EVENT = String.format(ERROR_MESSAGE_INVALID_FORMAT,
            Event.COMMAND_FORMAT);
    private static final String ERROR_MESSAGE_INVALID_TASK_ID = "invalid task number!";

    private static final Pattern FORMAT_ARG_ADD_DEADLINE = Pattern.compile("(?<description>.*) /by (?<by>.*)");
    private static final Pattern FORMAT_ARG_ADD_EVENT = Pattern.compile("(?<description>.*) /at (?<at>.*)");

    /**
     * Attempts to create a DeadlineCommand.
     *
     * @param args Arguments to create deadline.
     * @return EventCommand if arguments given are valid, InvalidCommand otherwise.
     */
    public static Command tryAddDeadline(String args) {
        if (args.isBlank()) {
            return new InvalidCommand(ERROR_MESSAGE_EMPTY_DESCRIPTION);
        }

        Matcher matcher = FORMAT_ARG_ADD_DEADLINE.matcher(args);
        if (!matcher.matches()) {
            return new InvalidCommand(ERROR_MESSAGE_INVALID_FORMAT_DEADLINE);
        }

        String description = matcher.group("description").trim();
        String byStr = matcher.group("by").trim();
        LocalDateTime by = Parser.parseDateTime(byStr);
        return new DeadlineCommand(description, by);
    }

    /**
     * Attempts to create an EventCommand.
     *
     * @param args Arguments to create event.
     * @return EventCommand if arguments given are valid, InvalidCommand otherwise.
     */
    public static Command tryAddEvent(String args) {
        if (args.isBlank()) {
            return new InvalidCommand(ERROR_MESSAGE_EMPTY_DESCRIPTION);
        }

        Matcher matcher = FORMAT_ARG_ADD_EVENT.matcher(args);
        if (!matcher.matches()) {
            return new InvalidCommand(ERROR_MESSAGE_INVALID_FORMAT_EVENT);
        }

        String description = matcher.group("description").trim();
        String atStr = matcher.group("at").trim();
        LocalDateTime at = Parser.parseDateTime(atStr);
        return new EventCommand(description, at);
    }

    /**
     * Attempts to create a TodoCommand.
     *
     * @param args Arguments to create to-do.
     * @return TodoCommand if arguments given are valid, InvalidCommand otherwise.
     */
    public static Command tryAddTodo(String args) {
        if (args.isBlank()) {
            return new InvalidCommand(ERROR_MESSAGE_EMPTY_DESCRIPTION);
        }
        return new TodoCommand(args);
    }
    
    /**
     * Attempts to create a DeleteCommand.
     *
     * @param args Arguments to delete task.
     * @return DeleteCommand if arguments given are valid, InvalidCommand otherwise.
     */
    public static Command tryDelete(String args) {
        if (args.isBlank()) {
            return new InvalidCommand(ERROR_MESSAGE_EMPTY_TASK_ID_DELETE);
        }

        try {
            int id = Parser.parseCommandId(args);
            return new DeleteCommand(id);
        } catch (NumberFormatException e) {
            return new InvalidCommand(ERROR_MESSAGE_INVALID_TASK_ID);
        }
    }


    /**
     * Attempts to create a FindCommand.
     *
     * @param args Arguments to find task(s).
     * @return FindCommand if arguments given are valid, InvalidCommand otherwise.
     */
    public static Command tryFind(String args) {
        if (args.isBlank()) {
            return new InvalidCommand(ERROR_MESSAGE_EMPTY_SEARCH);
        }
        return new FindCommand(args);
    }

    /**
     * Attempts to create a DoneCommand.
     *
     * @param args Arguments to mark task as done.
     * @return DoneCommand if arguments given are valid, InvalidCommand otherwise.
     */
    public static Command tryMarkAsDone(String args) {
        if (args.isBlank()) {
            return new InvalidCommand(ERROR_MESSAGE_EMPTY_TASK_ID_DONE);
        }

        try {
            int id = Parser.parseCommandId(args);
            return new MarkAsDoneCommand(id);
        } catch (NumberFormatException e) {
            return new InvalidCommand(ERROR_MESSAGE_INVALID_TASK_ID);
        }
    }
}
