import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Race {
    private ArrayList<Stage> stages;
    private AtomicBoolean winnerAvailable = new AtomicBoolean(false);


    public ArrayList<Stage> getStages() { return stages; }

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    public synchronized void printWinner(String name){
        if (!this.winnerAvailable.get()){
            System.out.println(name + " WIN");
            this.winnerAvailable.set(true);
        }
    }
}
