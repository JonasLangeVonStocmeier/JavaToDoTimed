import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class ToDoWindow extends JFrame {

    private final ToDoManager toDoManager;

    private JPanel toDoArea;

    public ToDoWindow(ToDoManager toDoManager) {

        this.toDoManager = toDoManager;

        setTitle("ToDo App");
        setSize(500, 700);


        add(toDoArea = createToDoArea(), BorderLayout.CENTER);
        add(createButtonRow(), BorderLayout.SOUTH);

        pack();

        addWindowListener(new CustomWindowAdapter(toDoManager));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createButtonRow() {
        final JPanel jPanel = new JPanel();

        jPanel.setLayout(new FlowLayout());

        JButton add = new JButton("Hinzufügen");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final String title = getInput("Gib deinen Titel ein: ");
                if (title.isEmpty()) return;

                final String description = getInput("Gib deine Beschreibung ein: ");
                if (description.isEmpty()) return;

                final String hours = getInput("In wieviel Stunden soll das ToDo ablaufen: ");
                if (hours.isEmpty()) return;

                try {
                    final int hoursInteger = Integer.parseInt(hours);

                    if (hoursInteger == 0) {
                        toDoManager.add(new ToDo(title, description, false));
                    } else {
                        toDoManager.add(new TimedToDo(title, description, false, LocalDateTime.now().plusHours(hoursInteger)));
                    }
                } catch (Exception exception) {

                    toDoManager.add(new ToDo(title, description, false));

                }

                remove(toDoArea);
                add((toDoArea = createToDoArea()), BorderLayout.CENTER);

                pack();
            }
        });

        final JButton removeAll = new JButton("Alle entfernen");
        removeAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDoManager.getToDos().clear();

                remove(toDoArea);
                add((toDoArea = createToDoArea()), BorderLayout.CENTER);

                pack();
            }
        });
        JButton removeCompleted = new JButton("Erledigte entfernen");
        removeCompleted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = toDoManager.getToDos().size() - 1; i >= 0; i--) {
                    if (toDoManager.getToDos().get(i).erledigt()) {
                        toDoManager.getToDos().remove(i);
                    }

                }
                remove(toDoArea);
                add((toDoArea = createToDoArea()), BorderLayout.CENTER);

                pack();
            }
        });


        jPanel.add(add);
        jPanel.add(removeAll);
        jPanel.add(removeCompleted);


        return jPanel;

    }

    private JPanel createToDoArea() {
        JPanel jPanel = new JPanel();

        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        ArrayList<ToDo> toDos = toDoManager.getToDos();

        for (int i = 0; i < toDos.size(); i++) {
            jPanel.add(createToDoBlock(toDos.get(i)));
        }

        return jPanel;
    }

    private JPanel createToDoBlock(ToDo toDo) {

        JPanel jPanel = new JPanel();

        jPanel.setLayout(new BorderLayout());

        jPanel.add(new JLabel(toDo.getTitel()), BorderLayout.NORTH);
        jPanel.add(new JLabel(toDo.getBeschreibung()), BorderLayout.CENTER);

        JCheckBox jCheckBox = new JCheckBox();
        jCheckBox.setSelected(toDo.erledigt());
        jCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDo.setErledigt(jCheckBox.isSelected());
            }
        });

        jPanel.add(jCheckBox, BorderLayout.EAST);

        if (toDo instanceof TimedToDo) {
            jPanel.add(new JLabel(((TimedToDo) toDo).getEndet().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))), BorderLayout.SOUTH);
        }

        return jPanel;
    }

    private String getInput(String prompt) {
        while (true) {
            String input = JOptionPane.showInputDialog(prompt);

            if (input == null) {
                return "";
            }

            if (!input.isEmpty()) {
                return input;
            }
        }
    }

}
