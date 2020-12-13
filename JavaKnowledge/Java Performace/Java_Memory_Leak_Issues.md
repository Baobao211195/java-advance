# Memory Leak là gì?
 Các object lưu trên heap không được sử dụng trong một thời gian dài, nhung GC không thể tìm tìm thấy chúng

# Các nguyên nhân 
## Sử dụng quá nhiều biến static.
 Thời gian sống của 1 biến static là thời gian sống của một class mà biến đó xuất hiện. GC sẽ không thể thu thập tất cả các biến này trừ khi **classLoader** của class chưa biết đó đủ điều kiện để GC thu thập.  Giả sử chúng ta có một collection là một **List** các object được khai bao là **static** , trong quá trình thực thi, danh sách này được thêm nhưng object mới vào, ứng dụng vẫn sẽ giữ tất cả chúng lại ngay cả khi chúng không được dùng nữa. Như ví dụ dưới đây.
 ``` java
import java.util.ArrayList;
import java.util.List;
    public class StaticCollectionTest {
        public static List<Student> list = new ArrayList<>();

        public void addStudentsToList() {
            for (int i = 0; i < 1E7; i++) {
            list.add(new Student("studentName_"+i));
            }
        }
        public static void main(String[] args) {
            new StaticCollectionTest().addNumbersToList();
        }
    }
    class Student {
        private String name;
        public Student(String name) {
        this.name = name;
        }
    }

 ```
Như ví dụ này nếu ta add 10 triệu object và danh sách này thì danh sách này vẫn duy trì bộ nhớ khi ta thực hiện xong hàm **addStudentToList** như vậy rất có thể gây ra lỗi out of memory. Cần rất cẩn thận khi sử dụng từ khoá static cho collection các object.  

##  ThreadLocal Object và Thread Pool.
 **ThreadLocal** cho phép lưu trữ data mà chỉ **Thread** cụ thể sẽ được access, data này sẽ bị hidden bởi các thread khác.**ThreadPool** là một application server được implement để tạo ra một pool chứa các thread. Một vài thread trong pool sẽ không được thu thập và tái sử dụng. Thông thường, **ThreadLocal** object sẽ được thu thập sau khi quá trình thực thi của thread đó được kết thúc, tuy nhiên thì trong một vài trường hợp thì nó sẽ không thu thập được và có thể dẫn tới việc memory leaks do nhưng object này vẫn được tham chiếu tới thread pool. Chính vì vậy việc extends **threadPool** cho phép chúng ta xử lý vẫn đề này bằng cách implement method **afterExecute()** giúp chúng ta tránh được memory leak trong **threadLocal** 
  ``` java

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalAwareThreadPool extends ThreadPoolExecutor {

    public ThreadLocalAwareThreadPool (
        int corePoolSize, int maximumPoolSize,
        long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue) {

        super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
    }
    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
    /* Call remove method for each ThreadLocal object of given thread*/
     if
    }
}
```

## USE OF NON-STATIC INNER CLASSES
