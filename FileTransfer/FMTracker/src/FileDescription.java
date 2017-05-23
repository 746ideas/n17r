

public class FileDescription implements Comparable<FileDescription> {

	private String name;
	private String type;
	private long size;
	private String modified;
	private String ip;
	private int port;
	
	public FileDescription(String name, String type, long size, String modified, String ip, int port) {
		this.setName(name);
		this.setType(type);
		this.setSize(size);
		this.setModified(modified);
		this.setIp(ip);
		this.setPort(port);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(FileDescription o) {
		return o.name.compareToIgnoreCase(name);
	}
	
	public String toString(){
		return "<"
				+ name + ", "
				+ type + ", "
				+ size + ", "
				+ modified + ", "
				+ ip + ", "
				+ port + ">";
	}

}
