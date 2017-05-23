

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.TreeSet;

/**

 */
public class WorkerRunnable implements Runnable {

	public static InputStream input;
	public static OutputStream output;
	protected Socket clientSocket = null;
	private boolean connected = false;
	private String clientIP = null;
	private int clientPort = -1;
	private Map<String, TreeSet<FileDescription>> filelist;
	private Map<String, TreeSet<FileDescription>> clientlist;
	private Mutex mutex;

	public WorkerRunnable(Socket clientSocket, Map<String, TreeSet<FileDescription>> filelist,
			Map<String, TreeSet<FileDescription>> clientlist) {
		this.filelist = filelist;
		this.clientlist = clientlist;
		this.clientSocket = clientSocket;
		this.mutex = new Mutex();
	}

	public void run() {
		connected = true;
		System.out.println("Client connected");

		try {
			input = clientSocket.getInputStream();
			output = clientSocket.getOutputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(input));

			establishConnection(br);
			getFilesFromClient(br);
			while (connected)
				messageProcessor(br);

		} catch (IOException e) {
			// report exception somewhere.
			e.printStackTrace();
		}
	}

	private void establishConnection(BufferedReader br) {
		System.out.println("Establishing Connection...");
		String s = null;

		try {
			while ((s = br.readLine()) != null) {
				if (s.isEmpty())
					break;

				System.out.println("CLIENT: " + s);

				if (s.equalsIgnoreCase("HELLO")) {
					output.write("HI\n".getBytes());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("Connection Established");
	}

	private void getFilesFromClient(BufferedReader br) {
		System.out.println("Reading Files List...");
		String s = null;

		try {
			while ((s = br.readLine()) != null) {
				if (s.isEmpty())
					break;

				String fname;
				String ftype;
				long fsize = 0;
				String modified;
				String ip;
				int port;

				System.out.println(s);
				// Get rid of '<' and '>'
				s = s.substring(1, s.length() - 1);

				String[] parts = s.split("\\,\\s");

				fname = parts[0];
				ftype = parts[1];
				try {
					fsize = Long.parseLong(parts[2]);
				} catch (NumberFormatException e) {
					System.out.println(e);
				}
				modified = parts[3];
				ip = parts[4];
				port = Integer.parseInt(parts[5]);

				if (clientIP == null && clientPort == -1) {
					clientIP = ip;
					clientPort = port;
				}

				FileDescription fd = new FileDescription(fname, ftype, fsize, modified, ip, port);

				TreeSet<FileDescription> file = filelist.get(fname);

				if (file == null) {
					file = new TreeSet<FileDescription>();
					filelist.put(fname, file);
				}
				try {
					mutex.acquire();
					try {
						file.add(fd);
					} finally {
						mutex.release();
					}
				} catch (InterruptedException ie) {
					System.out.println(ie.getMessage());
				}

				file = clientlist.get(String.join(":", ip, Integer.toString(port)));
				if (file == null) {
					file = new TreeSet<FileDescription>();

					try {
						mutex.acquire();
						try {
							clientlist.put(String.join(":", ip, Integer.toString(port)), file);
						} finally {
							mutex.release();
						}
					} catch (InterruptedException ie) {
						System.out.println(ie.getMessage());
					}
				}
				try {
					mutex.acquire();
					try {
						file.add(fd);
					} finally {
						mutex.release();
					}
				} catch (InterruptedException ie) {
					System.out.println(ie.getMessage());
				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Files Read");

	}

	private void messageProcessor(BufferedReader br) {
		String s = null;

		try {
			while ((s = br.readLine()) != null) {
				if (s.isEmpty())
					break;

				if (s.equalsIgnoreCase("BYE")) {
					connected = false;
					output.close();

					TreeSet<FileDescription> client_filelist = clientlist
							.get(String.join(":", clientIP, Integer.toString(clientPort)));

					for (FileDescription client_fd : client_filelist) {
						TreeSet<FileDescription> list_of_fd = filelist.get(client_fd.getName());

						for (FileDescription fd : list_of_fd) {
							if (fd.getIp().equals(clientIP) && fd.getPort() == clientPort) {
								try {
									mutex.acquire();
									try {
										list_of_fd.remove(fd);
									} finally {
										mutex.release();
									}
								} catch (InterruptedException ie) {
									System.out.println(ie.getMessage());
								}
							}
						}

						if (list_of_fd.size() == 0) {
							try {
								mutex.acquire();
								try {
									filelist.remove(client_fd.getName());
								} finally {
									mutex.release();
								}
							} catch (InterruptedException ie) {
								System.out.println(ie.getMessage());
							}
						}
					}

					clientlist.remove(String.join(":", clientIP, Integer.toString(clientPort)));

					System.out.println("Client disconnected");
					break;
				}

				if (s.startsWith("SEARCH:")) {
					if ((s = s.substring(8)).isEmpty()) {
						output.write("NOT FOUND\n".getBytes());
						break;
					}

					TreeSet<FileDescription> files = new TreeSet<FileDescription>();
					for (String key : filelist.keySet()) {
						if (key.toLowerCase().contains(s.toLowerCase())) {
							files.addAll(filelist.get(key));
						}
					}

					if (files.size() == 0) {
						output.write("NOT FOUND\n".getBytes());
						break;
					}

					String list = "FOUND: ";
					for (FileDescription file : files) {
						list += "<" + file.getName() + ", " + file.getType() + ", " + file.getSize() + ", "
								+ file.getModified() + ", " + file.getIp() + ", " + file.getPort() + ">\n";

					}

					System.out.println(list);
					output.write((list + "\n").getBytes());
				}

				output.write("INCORRECT MESSAGE\n".getBytes());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}