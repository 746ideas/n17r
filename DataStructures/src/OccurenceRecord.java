import impl.LLQHashTableMap;
import impl.LinkedListQueue;

public class OccurenceRecord {
	private String filename;
	private int linenumber;
	private int colnumber;
	public OccurenceRecord(String name, int lnumber, int cnumber){
		filename=name;
		linenumber=lnumber;
		colnumber=cnumber;
	}	
	public String toString(){
		return "{"+filename+", "+linenumber+", "+colnumber+"}";
	}
}
