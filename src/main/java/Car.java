import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }
    private CyclicBarrier readyCyclicBarrier;

    public Car(Race race, int speed, CyclicBarrier readyCyclicBarrier) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.readyCyclicBarrier = readyCyclicBarrier;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            readyCyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        race.printWinner(name);

        // еще один способ, узнаем позицию гонщика и отмечаемся что окончили гонку одновременно,
        // но результат напишется только после окончания гонки, что не совсем соответствует заданию в методичке
        try {
            int position = CARS_COUNT - readyCyclicBarrier.await();
            if (position == 1) {
                System.out.println("Победитель " + name + " (результат из класса Car)");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
