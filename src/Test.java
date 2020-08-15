
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test extends Thread{
    public static void main(String[] args) throws IOException {
        //
        System.out.println("test started");      
        Test runnable = new Test(); // or an anonymous class, or lambda...
        runnable.start();
        System.out.println("\ntest finished");
        
    }

    @Override
    public void run() {
        System.out.println("starting to run");
    }

    
    
    
}