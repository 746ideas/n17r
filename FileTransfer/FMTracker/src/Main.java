

public class Main {

	
	public static void main(String[] args) {
		
		Tracker server = new Tracker(44444);
		new Thread(server).start();

		try {
		    Thread.sleep(2000 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		System.out.println("Stopping Server");
		server.stop();

	}

}