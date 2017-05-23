

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.io.IOException;

public class Tracker implements Runnable{

	private int								serverPort   = 8080;
    private ServerSocket					serverSocket = null;
    private boolean							isStopped    = false;
    private Thread							runningThread= null;
	private static Map<String, TreeSet<FileDescription>> 	filelist;
	private static Map<String, TreeSet<FileDescription>>	clientlist;

    public Tracker(){}
    
    public Tracker(int port){
    	filelist = new HashMap<String, TreeSet<FileDescription>>();
    	clientlist = new HashMap<String, TreeSet<FileDescription>>();
        this.serverPort = port;
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
        
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            new Thread(
                new WorkerRunnable(
                    clientSocket, filelist, clientlist)
            ).start();
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

}