package clean.code.completableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DemoCompletableFuture {
    /**
     * học cách sử dụng asynchronous APi
     * học cách tạo ra code non-blocking và làm thế nào thực hiện pipeline 2 tính toán bất đồng bộ 
     * và merge kết quả của chúng lại.
     * 
     * Học cách xử lý reactive events
     */
    public static void main(String[] args) {
        
        Shop shop = new Shop();
        
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsyncApi("async product");
        
        long invocationTime = (System.nanoTime()- start) / 1000000;
        
        System.out.println("Invocation returned after " + invocationTime + " msecs");
        
        //1. Do some more task ( vs database or call api so on...)
        doSomeThingAnothers();
        
        //2. while the price of the product is being calculated
        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
            
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        
        long retrievalTime = (System.nanoTime()- start) / 1000000;
        System.out.println("Price returned after " + retrievalTime + " msecs");
        
        
    }
    
    private static void doSomeThingAnothers() {
        System.out.println("do some things===================");
        return;
    }

}


class Shop {
    
    
    /**
     * <PRE>
     * Re-write get price vs asynchronous Api
     *  create a instance of CompletableFuture is Future
     *  mô tả kết quả của một action bất đồng bộ khi nó hoàn thành
     * </PRE>
     */
    public Future<Double> getPriceAsyncApi(String product) {
        
        //1. create CompletableFuture will contain kết quả của một action
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        
        
        Runnable thread = () -> {
            //2. Thực hiện việc tính toán ở 1 thread khác.
            double price = calculatePrice(product);
            
            
            //3. set price được trả về bởi 1 tính toán trong 1 khoảng thời gian dài
            // của Future khi tính toán này được hoàng thành.
            futurePrice.complete(price);
        };
        
        thread.run();
        
        //4. Trả kết quả trả về mà ko cần phải đợi việc tính toán được hoàn thành.
        return futurePrice;
        
    }
    
    /**
     * <PRE>
     * When call this function : việc tính toán giá sẽ bị delay 1s
     * mới hoàn thành cho đến khi gọi 1 tính toán khác
     * this synchronous api
     * </PRE>
     */
    public double getPriceSynchApi(String product) {
        // do stuff
        
        // query database of shop, 
        // + call other sevices
        // can be delayed => create a delay method to simulate
        return calculatePrice(product);
    }
    
    /**
     * Simulating return random price of product
     */
    public double calculatePrice(String product) {
        delay();
        Random random = new Random();
        
        return random.nextDouble() * product.charAt(0) + product.charAt(1); 
    }
    
    private static void delay() {
        try {
            // delay 1s
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}