import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PingPong extends Thread {
    private String palavra;
    public PingPong(String palavra) { this.palavra = palavra; }

    public void run() { // implementa o método run
        for (int i = 0; i < 1000; i++) {
            System.out.print(palavra + " ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var executor = Executors.newSingleThreadExecutor();
        List<Future> futures = new ArrayList<>();
        futures.add(executor.submit(new TestFuture("Ping")));
        futures.add(executor.submit(new TestFuture("Pong")));
        System.out.println("Threads submetidas");
        for (Future future: futures)
            future.get();
        executor.shutdown();
        System.out.println("Threads finalizadas");
    }
}