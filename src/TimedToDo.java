import java.sql.Time;
import java.time.LocalDateTime;

public class TimedToDo extends ToDo{

    private LocalDateTime endet;

    public TimedToDo(String name, String beschreibung, boolean erledigt, LocalDateTime endet){
        super(name, beschreibung, erledigt);
        this.endet = endet;
    }

    @Override
    public String toString() {
        return "TimedToDo{" +
                "titel'" + getTitel() + '\'' +
                ", beschreibung='" + getBeschreibung() + '\'' +
                ", erledigt=" + erledigt() +
                ", endet=" + endet +
                '}';
    }

    public LocalDateTime getEndet() {
        return endet;
    }
    public void setEndet(LocalDateTime endet) {
        this.endet = endet;
    }

}
