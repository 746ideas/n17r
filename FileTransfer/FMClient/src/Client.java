import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Client implements Runnable {
	
	private ServerSocket welcomeSocket = null;
	
	public Client(ServerSocket serverSocket) {
		this.welcomeSocket = serverSocket;
	}
	
	@Override
	public void run() {	
//		System.out.println("<ClientServer run>");
		
		try {
			while (true) {
				Socket sock = welcomeSocket.accept();
				new Thread(new UploadRunnable(sock)).start();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
//		System.out.println("</ClientServer run>");
	}
	
	private class UploadRunnable implements Runnable {
	
		private Socket sock = null;
		
		public UploadRunnable(Socket sock) {
			this.sock = sock;
		}
		
		@Override
		public void run() {
//			System.out.println("<UploadRunnable run>");
			
			try {
				BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				DataOutputStream output = new DataOutputStream(sock.getOutputStream());
				
				String s = input.readLine();
				String[] splitted = s.split("\\s+");
				
				if (splitted.length > 1 && splitted[0].equals("DOWNLOAD:")) {
					String filename = MainWindow.sFolderName + "/" + s.substring(10);
					File file = new File(filename);
					
					File encryptedfile = new File("encrypted");
			    	Encryption.encryptFile("An4ik Leha Madik", file, encryptedfile);
					
					if (encryptedfile != null && encryptedfile.exists() && encryptedfile.isFile()) {
				        byte[] bytes = new byte[16 * 1024];
				        InputStream in = new FileInputStream(encryptedfile);

				        int count;
				        while ((count = in.read(bytes)) > 0) {
				            output.write(bytes, 0, count);
				        }

				        in.close();
					}
					encryptedfile.delete();
				}
				
				input.close();
				output.close();
				sock.close();
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
//			System.out.println("</UploadRunnable run>");
		}
		
	}
	
}
