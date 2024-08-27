//print even & odd in multithreading 
//Run this code in https://www.onlinegdb.com/online_java_compiler
public class Main{
    
    private volatile boolean oddTurn = true;
    public void printOdd(int number){
        synchronized (this){
            while(!oddTurn){
                try{
                    wait();
                }
                catch(Exception e){
                    Thread.currentThread().interrupt();
                    System.out.println("Thread Interrupted "+ e);
                }
            }
            
            System.out.println("Odd: " + number);
            oddTurn = false;
            notify();
        }
    }
    
    public void printEven(int number){
        synchronized (this){
        while(oddTurn){
            try{
                wait();
            }
            catch(Exception e){
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted " + e);
            }
        }
        
        System.out.println("Even: "+ number);
        oddTurn = true;
        notify();
        }
    }
    
    
    public static void main(String[] args){
        Main printer = new Main();
        Thread t1 = new Thread(()->{
           for(int i=1;i<=10;i+=2)
           {
               printer.printOdd(i);
           }
        });
        
        Thread t2 = new Thread(()->{
           for(int i=2;i<=10;i+=2)
           {
               printer.printEven(i);
           }
        });
        
        t1.start();
        t2.start();
    }
}
