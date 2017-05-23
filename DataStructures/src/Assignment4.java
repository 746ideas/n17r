import impl.BSTSet;
import impl.LLQHashTableMap;
import impl.LinkedListQueue;
import impl.LinkedListSortedQueue;
import impl.LinkedListStack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import adt.Queue;
import adt.Stack;

public class Assignment4 {
	static int number = 0;

	public static Queue<File> getAllFiles(File rootDir) {
		Queue<File> temp = new LinkedListQueue();

		File[] contents = rootDir.listFiles();
		for (File file : contents) {
			if (file.isFile()) {
				temp.enqueue(file);
			} else if (file.isDirectory()) {
				Queue<File> temp2 = getAllFiles(file);
				while (temp2.getSize() != 0) {
					try {
						temp.enqueue(temp2.dequeue());
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		}
		return temp;
	}

	public static void readfromfiles(File rootDir, LLQHashTableMap<String, LinkedListQueue<OccurenceRecord>> map,
			LinkedListSortedQueue<String> sort) throws Exception {
		Queue<File> temp = new LinkedListQueue();
		temp = getAllFiles(rootDir);
		BSTSet<String> set=new BSTSet();
		for (int i = 0; i < temp.getSize(); i++) {
			File file = null;
			try {
				file = temp.dequeue();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int line = 0;
			int column = 0;
			int ch;
			String token = "";
			do {
				ch = reader.read();
				if (ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '-' || ch == ',' || ch == '('
						|| ch == ')' || ch == '{' || ch == '}' || ch == '.' || ch == '?' || ch == '!' || ch == '\''
						|| ch == '\"' || ch == ';' || ch == ':' || ch == -1) {
					if (token.length() > 0) {
						token = token.toLowerCase();
						if (map.getValue(token) == null) {
							LinkedListQueue<OccurenceRecord> record = new LinkedListQueue();
							record.enqueue(new OccurenceRecord(file.getName(), line, column - token.length()));
							map.define(token, record);
						} else {
							map.getValue(token)
									.enqueue(new OccurenceRecord(file.getName(), line, column - token.length()));
						}

						set.add(token);
						token = "";
						number++;
					}
					if (ch == '\n') {
						line++;
						column = 0;
					}
				} else {
					token = token + (char) ch;
					column++;
				}
			} while (ch != -1);
			reader.close();
		}
		while(set.getSize()!=0){
			String temp2=set.removeAny();
			sort.insert(temp2);
		}
		System.out.println("Sorted order:");
		System.out.println(sort);
	}

	public static void main(String[] args) throws Exception {
		File file = new File("inputfiles");
		LLQHashTableMap<String, LinkedListQueue<OccurenceRecord>> map = new LLQHashTableMap(100);
		LinkedListSortedQueue<String> sort = new LinkedListSortedQueue();
		try {
			readfromfiles(file, map, sort);
		} catch (IOException e) {
			System.out.println("empty file");
		}
		File infile = new File("getwordinfo.txt");
		Scanner scanner = new Scanner(infile);
		while (scanner.hasNext()) {
			String w = scanner.next();
			w = w.toLowerCase();
			System.out.println(w);
			if (map.getValue(w) != null) {
				System.out.println("Occurrences:" + map.getValue(w));
				System.out.println("number of occurences:" + map.getValue(w).getSize());
				System.out.println("frequency:" + 100 * (double) map.getValue(w).getSize() / (double) number);
				System.out.println("");
			} else {
				System.out.print("Not found");
				System.out.println("\n");
			}
		}
		System.out.println("Size of map is "+map.getSize());
		System.out.println("Load Factor is "+map.getLoadFactor());
		System.out.println("Standard Deviation is "+map.getBucketSizeStandardDev());
	}
}
