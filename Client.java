package Lab05;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            TimeServerInterface timeServer = (TimeServerInterface) registry.lookup("TimeServer");

            Process process01 = new Process(0,timeServer);
            Process process02 = new Process(0,timeServer);
            Process process03 = new Process(0,timeServer);
            // timeServer.register(process01);
            // timeServer.register(process02);
            // timeServer.register(process03);
            

            process01.synchronize();
            System.out.println("Process 1 called synchronize:\n     Process 1 " + process01.getTime() + "\n     Process 2 " + process02.getTime() + "\n     Process 3 " + process03.getTime() + " ");
            process02.synchronize();
            System.out.println("Process 2 called synchronize:\n     Process 1 " + process01.getTime() + "\n     Process 2 " + process02.getTime() + "\n     Process 3 " + process03.getTime() + " ");
            process03.synchronize();
            System.out.println("Process 3 called synchronize:\n     Process 1 " + process01.getTime() + "\n     Process 2 " + process02.getTime() + "\n     Process 3 " + process03.getTime() + " ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
