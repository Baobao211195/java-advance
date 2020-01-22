package clean.code.oop;

public class Clazz {
    
    private String name;
    private static int counter = 0;
    
    private static String sayHello(String name) {
        return "hello " +  name;
    }

    public Clazz() {
        this.counter += 1;
    }
    
    
    public static void main(String[] args) {
        Clazz cl1 = new Clazz();
        System.out.println(cl1.getCounter());
        Clazz cl2 = new Clazz();
        System.out.println(cl2.getCounter());
        Clazz cl3 = new Clazz();
        System.out.println(Clazz.getCounter());
    }

    public static int getCounter() {
        return counter;
    }

    
    public void setName(String name) {
        this.name = name;
    }
}
