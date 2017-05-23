package impl;

import adt.Map;
import impl.LinkedListQueue;

public class LLQueueMap<K, V> implements Map<K, V>{

	private LinkedListQueue<KeyValuePair<K, V>> pairs;
	
	public LLQueueMap(){
		pairs=new LinkedListQueue();
	}
	public void define(K key, V value) {
		int checker=0;
		for(int i=0; i<pairs.getSize(); i++){
			KeyValuePair<K, V> x = null;
			try {
				x = pairs.dequeue();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if(key.equals(x.getKey())){
				x.setValue(value);
				checker++;
			}
				pairs.enqueue(x);
			
		}
		if(checker==0){
			pairs.enqueue(new KeyValuePair<K, V>(key, value));
		}
	}

	public V getValue(K key) {
		for(int i=0; i<pairs.getSize(); i++){
			KeyValuePair<K, V> x = null;
			try {
				x = pairs.dequeue();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if(x.getKey()==key){
				return x.getValue();
			}
		}
		return null;
	}


	public V remove(K key) {
		int size=pairs.getSize();
		V value=null;
		for(int i=0; i<size; i++){
			KeyValuePair<K, V> x = null;
			try {
				x = pairs.dequeue();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if(x.getKey()==key){
				try {
					x = pairs.dequeue();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				value=x.getValue();
			}
			pairs.enqueue(x);
		}
		return value;
	}


	public KeyValuePair<K, V> removeAny() throws Exception {
		if(pairs.getSize()!=0){
			return pairs.dequeue();
		}else{
			throw new Exception("Queue is empty");
		}
	}

	public int getSize() {
		return pairs.getSize();
	}

	public void clear() {
		pairs=new LinkedListQueue();
	}
	

	public String toString(){
		KeyValuePair<K, V> x = null;
		String result="";
		int size=pairs.getSize();
		for(int i=0; i<size; i++){
			try {
				x=pairs.dequeue();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			result+=x.toString();
			pairs.enqueue(x);
		}
		return result;
	}
}
