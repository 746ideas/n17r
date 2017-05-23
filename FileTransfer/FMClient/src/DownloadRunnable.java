import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DownloadRunnable implements Runnable {
	
	private static final String TAG = "DownloadRunnable";
	
	private String selected = null;
	
	public DownloadRunnable(String[] selected) {
		this.selected = selected[0].substring(1, selected[0].length() - 1);
	}
	
	@Override
	public void run() {
		try {
			File downloadFolder = createDirectory(MainWindow.dFolderName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
//		System.out.println("<DownloadRunnable run>");
//		System.out.println(TAG + ": " + selected);
		
		try {
			String IP = getSelectedItemIP(selected);
			int port = getSelectedItemPort(selected);
			Socket sock = new Socket(IP, port);
			
			DataOutputStream output = new DataOutputStream(sock.getOutputStream());
	    	
	    	String filename = getSelectedItemFileName(selected);
	    	output.writeBytes("DOWNLOAD: " + filename + "\n");
	    	
	    	BufferedInputStream input = null;
	        FileOutputStream foutput = null;
	        try {
	            input = new BufferedInputStream(sock.getInputStream());
	            foutput = new FileOutputStream(MainWindow.dFolderName + "/decrypted");

	            final byte data[] = new byte[1024 * 16];
	            int count;
	            while ((count = input.read(data, 0, 1024)) != -1) {
//	            	System.out.println(count);
	                foutput.write(data, 0, count);
	            }
	            File file = new File(MainWindow.dFolderName +"/decrypted");
	            File decryptedfile=new File(MainWindow.dFolderName +"/"+filename);
		    	Encryption.decryptFile("An4ik Leha Madik", file, decryptedfile);
		    	file.delete();
	        } finally {
	            if (input != null) {
	                input.close();
	            }
	            if (foutput != null) {
	                foutput.close();
	            }
	        }
	    	
//	    	System.out.println("Download completed!");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
//		System.out.println("</DownloadRunnable run>");
	}
	
	public static File createDirectory(String directoryPath) throws IOException {
//		System.out.println("<createDirectory/>");

		File dir = new File(directoryPath);
		if (dir.exists()) {
			return dir;
		}
		if (dir.mkdirs()) {
			return dir;
		}
		throw new IOException("Failed to create directory '" + dir.getAbsolutePath() + "' for an unknown reason.");
	}
	
	private String getSelectedItemFileName(String s) {
//		System.out.println("<getSelectedItemFileName/>");
		
		String[] splitted = s.split("\\,\\s");
		
		if (splitted.length != 6 || splitted[0].length() < 3)
			return null;
		
		return splitted[0];
	}
	
	private String getSelectedItemFileType(String s) {
//		System.out.println("<getSelectedItemFileType/>");
		
		String[] splitted = s.split("\\,\\s");
		
		if (splitted.length != 6 || splitted[1].length() < 2)
			return null;
		
		return splitted[1];
	}
	
	private int getSelectedItemSize(String s) {
//		System.out.println("<getSelectedItemSize/>");
		
		String[] splitted = s.split("\\,\\s");
		
		if (splitted.length != 6 || splitted[2].length() < 2)
			return 0;
		
		return Integer.parseInt(splitted[2]);
	}
	
	private String getSelectedItemLastModified(String s) {
//		System.out.println("<getSelectedItemLastModified/>");
		
		String[] splitted = s.split("\\,\\s");
		
		if (splitted.length != 6 || splitted[3].length() < 2)
			return null;
		
		return splitted[3];
	}
	
	private String getSelectedItemIP(String s) {
//		System.out.println("<getSelectedItemIP/>");
		
		String[] splitted = s.split("\\,\\s");
		
		if (splitted.length != 6 || splitted[4].length() < 2)
			return null;
		
		return splitted[4];
	}
	
	private int getSelectedItemPort(String s) {
//		System.out.println("<getSelectedItemPort/>");
		
		String[] splitted = s.split("\\,\\s");
		
		if (splitted.length != 6 || splitted[5].length() < 2)
			return 0;
		
		return Integer.parseInt(splitted[5]);
	}
	
}
