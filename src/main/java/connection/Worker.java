package connection;

import repository.Operation;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hammer on 2018/4/29.
 */
public class Worker implements Runnable {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Worker.class);

    private int threadId;

    private int taskId;

    public Worker(int taskId, int threadId) {
        this.threadId = threadId;
        this.taskId = taskId;
    }

    private static final int limit = 100;

    private static final int threadNumber = 200;

    public void run() {
        int count = 0;
        while (count < limit) {
            count++;
            /**
             * todo using normal query
             */
//            Operation.executeQuery(taskId, threadId);
            /**
             * todo using exclusive lock
             */
            Operation.executeQueryForUpdate(taskId, threadId);


        }

    }

    public static void startTask(int taskId) {
        Long startMilli = System.currentTimeMillis();
        log.info("start testing  [{}]", startMilli);
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            Runnable worker = new Worker(taskId, i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        Long endMilli = System.currentTimeMillis();
        log.info("end testing  [{}]", endMilli);
        log.info("cost milliseconds  [{}]", endMilli - startMilli);
        log.info("Finished all threads");
    }


}
