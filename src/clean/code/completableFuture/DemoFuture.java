package clean.code.completableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DemoFuture {
    
    public static void main(String[] args) {
        // Create an ExecutorService cho phép submit các tasks tới một thread pool.
        ExecutorService executorService = Executors.newCachedThreadPool();
        
        
        Future<Double> future = executorService.submit(
                
                // submit a callable to ExecutorService
                // can refator to lamda
                new Callable<Double>() {
                    @Override
                    public Double call() {
                       
                        try {
                            // Thực hiện 1 tính toán tại 1 thread khác 
                            // function này được thực hiện bất đồng bộ
                            return doSomeThingsLongComputation();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return 0d;
                    }
        });
        
        // thực hiện 1 task khác trong khi doSomeThingsLongComputation vẫn đang thực hiện
        doSomeThingAnothers();
        
        try {
            // thực hiện việc lấy kết quả của việc tính toán ở thread khác (doSomeThingsLongComputation)
            // sau khi đợi 1s.
            // đây là function blocking.
            future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    /***
     * Các vấn đề của Future
     * Việc kết hợp  các tính toán bất đồng bộ là 1 khi cả hai độc lập vs nhau, hoặc khi task 2 phụ thuộc vào task 1
     * 
     * Việc chờ đợi hoàn thành tất cả các task đã thực hiện bởi các Futures
     * Waiting for the completion of only the quickest task in a set of Futures (possibly because they’re
        trying to calculate the same value in different ways) and retrieving its result
      * Programmatically completing a Future (that is, by manually providing the result of the asynchronous
        operation)
      * Reacting to a Future completion (that is, being notified when the completion happens and then
        having the ability to perform a further action using the result of the Future, instead of being blocked
        waiting for its result)
     * 
     */
    
   

    
    //
    private static Double doSomeThingsLongComputation() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("do some things takes a long time ===================");
        return null;
    }
    
    
    private static void doSomeThingAnothers() {
        System.out.println("do some things===================");
        return;
    }

}
