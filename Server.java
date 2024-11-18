package Lab05;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            TimeServer timeServer = new TimeServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("TimeServer", timeServer);

            System.out.println("TimeServer is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

interface ProcessInterface extends Remote {
    public void synchronize() throws RemoteException;

    public long getTime() throws RemoteException;
}

class Process extends UnicastRemoteObject implements ProcessInterface {
    private long localTime;
    public TimeServerInterface timeServer;

    public Process(long time, TimeServerInterface timeServer) throws RemoteException {
        super();
        this.localTime = time;
        this.timeServer = timeServer;
    }

    // methods below
    @Override
    public void synchronize() throws RemoteException {
        // gets the time it took to recieve the response, then add half of that to the time received
        long requestTime = System.currentTimeMillis();
        long serverTime = timeServer.getTime();
        long recieveTime = System.currentTimeMillis();
        
        this.localTime = serverTime + ((recieveTime - requestTime) / 2);
    }

    @Override
    public long getTime() throws RemoteException {
        return this.localTime;
    }
}

interface TimeServerInterface extends Remote {
    public long getTime() throws RemoteException;
}

class TimeServer extends UnicastRemoteObject implements TimeServerInterface {
    private long masterClock;

    public TimeServer() throws RemoteException {
        super();
        this.masterClock = System.currentTimeMillis();
    }

    @Override
    public long getTime() throws RemoteException {
        masterClock = System.currentTimeMillis();
        System.out.println("getTime() called, returning: " + masterClock);
        return masterClock;
    }
}
