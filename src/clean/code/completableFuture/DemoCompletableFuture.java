package clean.code.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

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
        
        // create multiple shops
        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop"),
                new Shop("MyFavoriteShop")
        );
        // find prices of shop base on products
        System.out.println("=======================START USE STREAM===========================");
        long starts = System.nanoTime();
        findPricesStream(shops).forEach(System.out::println);
        long retrievalTimes = (System.nanoTime()- starts) / 1000000;
        System.out.println("Price returned after " + retrievalTimes + " msecs");
        System.out.println("========================STOP==========================");
        
        
        System.out.println("=======================START USING PARALLE STREAM=====");
        long startsT = System.nanoTime();
        findPriceParalleStream(shops).forEach(System.out::println);
        long retrievalTimesT = (System.nanoTime()- startsT) / 1000000;
        System.out.println("Price returned after " + retrievalTimesT + " msecs");
        
        System.out.println("========================STOP==========================");
        
        
        System.out.println("=======================START USING COMPLETABLEFUTURE =====");
        long startsAs = System.nanoTime();
        findPriceAsync(shops).forEach(System.out::println);
        long retrievalTimesAs = (System.nanoTime()- startsAs) / 1000000;
        System.out.println("Price returned after " + retrievalTimesAs + " msecs");
        System.out.println("========================STOP==========================");
        
        
        System.out.println("========================NUMBER THREADS OF POOLS========================== "
                + Runtime.getRuntime().availableProcessors());
        
        System.out.println("How to calculate number of thread in a pools (size of thread pool)");
        /**
         * Number of core of = Runtime.getRuntime().availableProcessors() (Ncpu)
         * target CPU utilization (from 0 to 1) (Ucpu)
         * W/C (waste/computed) time => tỉ lệ lãng phí thời gian trên thời gian tính toán.
         * 
         * Nthreads = Ncpu * Ucpu * (1 + W/C)
         * 
         * example : ứng dụng dành 99% thời gian để đợi response của shops => W/C= 100
         * giả sử Ucp = 100%
         * Runtime.getRuntime().availableProcessors() (Ncpu) =  4
         * => Nthread = 4 * 100% * (1 + 100) => approximate 400 threads
         * 
         */
        
        System.out.println("Set up less than 100 threads");
        
        final Executor executor  = Executors.newFixedThreadPool(
                Math.min(shops.size(), 1000), // tạo ra một thread pool vs số lượng threads bằng tối thiểu giữa 100 và số lượng các shops
                new ThreadFactory () {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true); // use daemon threads để ngăn cản việc termination of program
                        return t;
                    }
        });
        
        System.out.println("=======================START USING COMPLETABLEFUTURE VS EXECUTOR =====");
        long startsAsPool = System.nanoTime();
        findPriceAsyncVsExecutor(shops, executor).forEach(System.out::println);
        long retrievalTimesAsPools = (System.nanoTime()- startsAsPool) / 1000000;
        System.out.println("Price returned after " + retrievalTimesAsPools + " msecs");
        System.out.println("========================STOP==========================");
        
    }
    
    private static void doSomeThingAnothers() {
        System.out.println("do some things===================");
        return;
    }
    
    public static List<String> findPricesStream(List<Shop> shops) {
        return shops
                .stream()
                .map(Shop::formatShop)
                .collect(Collectors.toList());
    }
    
    public static List<String> findPriceParalleStream(List<Shop> shops) {
        return shops
                .parallelStream()
                .map(Shop::formatShop)
                .collect(Collectors.toList());
    }
    
    public static List<String> findPriceAsync (List<Shop> shops) {
        // calculate each price vs CompletableFuture
        List<CompletableFuture<String>> priceAsyncs = 
                shops.stream()
                    .map(shop -> CompletableFuture.supplyAsync(() -> shop.formatShop()))
                    .collect(Collectors.toList());
        // wait for completion all asynchronous calculation prices
        return priceAsyncs.stream().map(CompletableFuture::join).collect(Collectors.toList());
        
    }
    public static List<String> findPriceAsyncVsExecutor (List<Shop> shops, final Executor executor) {
        // calculate each price vs CompletableFuture
        List<CompletableFuture<String>> priceAsyncs = 
                shops.stream()
                    .map(shop -> CompletableFuture.supplyAsync(() -> shop.formatShop(), executor))
                    .collect(Collectors.toList());
        // wait for completion all asynchronous calculation prices
        return priceAsyncs.stream().map(CompletableFuture::join).collect(Collectors.toList());
        
    }

}

class Product {
    private double price;
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
class Shop {
    
    private Product product;
    private String name;
    private double price;
    
    public String formatShop() {
        return String.format("%s price is %.2f", this.getName(), this.getPrice(this.getName()));
    }
    
    
    public double getPrice(String name) {
        this.price = calculatePrice1(name);
        return price;
    }
    public Shop(String name) {
        this.name = name;
    }
    public Shop() {
    }
    public String getName() {
        return name;
    }

  
    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public double calculatePrice1(String product) {
        delay();
        Random random = new Random();
        
        return random.nextDouble() * product.charAt(0) + product.charAt(1); 
    }
    
    /**
     * <PRE>
     * Use async factory method
     * </PRE>
     */
    public Future<Double> getPriceAsyncApiVsSupplyAsyncFactory(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }
    
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
            try {
                //2. Thực hiện việc tính toán ở 1 thread khác.
                double price = calculatePrice(product);
                
                //3. set price được trả về bởi 1 tính toán trong 1 khoảng thời gian dài
                // của Future khi tính toán này được hoàng thành.
                futurePrice.complete(price);
                
            } catch (Exception ex) {
                // process in case has exception of asyn
                futurePrice.completeExceptionally(ex);
            }
         
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