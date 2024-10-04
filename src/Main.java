import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        final ToDoManager toDoManager = new ToDoManager("todoapp_savefile.txt");

        toDoManager.loadToDos();

        toDoManager.removeExpiredToDos();

        new ToDoWindow(toDoManager);
    }

}
