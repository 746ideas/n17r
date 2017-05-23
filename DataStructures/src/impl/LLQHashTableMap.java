package impl;

import adt.HashTableMap;
import adt.HashTableStats;
import adt.Map;

public class LLQHashTableMap<K extends Comparable, V> implements HashTableMap<K, V>{

	private LinkedListQueue<KeyValuePair<K,V>>[] buckets;
	private int size;

	public LLQHashTableMap(int n) {
		buckets = new LinkedListQueue[n];
		size = 0;
	}
	@Override
	public void define(K key, V value) {
		int hc = Math.abs(key.hashCode()) % buckets.length;
		
		if (buckets[hc] == null) {
			LinkedListQueue<KeyValuePair<K,V>> temp = new LinkedListQueue<>();
			temp.enqueue(new KeyValuePair(key, value));
			buckets[hc] = temp;
			size++;
		} else {
			for(int i=0;i<buckets[hc].getSize();i++){
				KeyValuePair<K, V> x=null;
				try {
					x=buckets[hc].dequeue();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				if(x.getKey().equals(key)){
					x.setValue(value);
				}
				buckets[hc].enqueue(x);
			}
		}
	}

	@Override
	public V getValue(K key) {
		int hc = Math.abs(key.hashCode()) % buckets.length;
		KeyValuePair<K, V> result = null;
		V temp=null;
		if (buckets[hc] != null) {
			int sz = buckets[hc].getSize();
			for (int i = 0; i < sz; i++) {
				try {
					result = buckets[hc].dequeue();
					buckets[hc].enqueue(result);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				if (key.equals(result.getKey())) {
					temp=result.getValue();
				}
			}
		}
		return temp;
	}

	@Override
	public V remove(K key) {
		int hc = Math.abs(key.hashCode()) % buckets.length;
		V temp=null;

		if (buckets[hc] != null) {
			int sz = buckets[hc].getSize();
			KeyValuePair<K, V> result = null;

			for (int i = 0; i < sz; i++) {
				try {
					result = buckets[hc].dequeue();
					if (key.equals(result.getKey())) {
						temp=result.getValue();
						size--;
					} else {
						buckets[hc].enqueue(result);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

		return temp;
	}

	@Override
	public KeyValuePair<K, V> removeAny() throws Exception {
		if (size == 0) {
			throw new Exception("The set is empty.");
		}
		int i = 0;
		while (buckets[i] == null || buckets[i].getSize() == 0) {
			i++;
		}		
		KeyValuePair<K, V> result = buckets[i].dequeue();
		size--;
		
		return result;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void clear() {
		buckets = new LinkedListQueue[buckets.length];
		size = 0;
	}

	public String toString(){
		String str = "";
		if (size != 0) {
			for (int i = 0; i < buckets.length; i++) {
				if (buckets[i] != null) {
					KeyValuePair<K, V> result = null;
					int sz = buckets[i].getSize();
					for (int k = 0; k < sz; k++) {
						if (k == 0) {
							str += "\n";
						}
						try {
							result = buckets[i].dequeue();
							buckets[i].enqueue(result);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						str += result + " ";
					}
				}
			}
		}

		return "{" + str + "}";
	}
	@Override
	public int getNumberOfBuckets() {
		return buckets.length;
	}

	@Override
	public int getBucketSize(int index) throws Exception {
		if(index >= getNumberOfBuckets()) {
			throw new Exception("Invalid bucket index.");
		}
		return buckets[index].getSize();
	}

	@Override
	public double getLoadFactor() {
		return size / getNumberOfBuckets();
	}

	@Override
	public double getBucketSizeStandardDev() {
		double mean = 0;		
		for (int i = 0; i < getNumberOfBuckets(); i++) {
			try {
				mean += getBucketSize(i) / getNumberOfBuckets();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}			
		}
		
		double variance = 0;
		for (int i = 0; i < getNumberOfBuckets(); i++) {
			try {
				double temp = getBucketSize(i) - mean;
				variance += temp * temp;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}			
		}
		double deviation = Math.sqrt(variance);
		return deviation;
	}
	@Override
	public String bucketsToString() {
		String str = "";
		if (size != 0) {
			for (int i = 0; i < buckets.length; i++) {
				if (buckets[i] != null) {
					KeyValuePair<K, V> result = null;
					int sz = buckets[i].getSize();
					for (int k = 0; k < sz; k++) {
						if (k == 0) {
							str += "\n" + i + ": ";
						}
						try {
							result = buckets[i].dequeue();
							buckets[i].enqueue(result);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						str += result + " ";
					}
				}
			}
		}

		return "(" + str + ")";
	}
}
