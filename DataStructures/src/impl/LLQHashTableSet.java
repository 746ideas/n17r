package impl;

import adt.HashTableStats;
import adt.Set;

public class LLQHashTableSet<T> implements Set<T> {

	private LinkedListQueue<T>[] buckets;
	private int size;

	public LLQHashTableSet(int n) {
		buckets = new LinkedListQueue[n];
		size = 0;
	}

	@Override
	public void add(T value) {
		int hc = Math.abs(value.hashCode()) % buckets.length;

		if (buckets[hc] == null) {
			LinkedListQueue<T> temp = new LinkedListQueue<>();
			temp.enqueue(value);
			buckets[hc] = temp;
			size++;
		} else {
			if (!contains(value)) {
				buckets[hc].enqueue(value);
				size++;
			}
		}
	}

	@Override
	public boolean contains(T value) {
		int hc = Math.abs(value.hashCode()) % buckets.length;
		T result = null;
		boolean checker = false;
		if (buckets[hc] != null) {
			int sz = buckets[hc].getSize();
			for (int i = 0; i < sz; i++) {
				try {
					result = buckets[hc].dequeue();
					buckets[hc].enqueue(result);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				if (value.equals(result)) {
					checker = true;
				}
			}
		}

		return checker;
	}

	@Override
	public boolean remove(T value) {
		int hc = Math.abs(value.hashCode()) % buckets.length;
		boolean checker = false;

		if (buckets[hc] != null) {
			int sz = buckets[hc].getSize();
			T result = null;

			for (int i = 0; i < sz; i++) {
				try {
					result = buckets[hc].dequeue();
					if (value.equals(result)) {
						checker = true;
						size--;
					} else {
						buckets[hc].enqueue(result);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

		return checker;
	}

	@Override
	public T removeAny() throws Exception {
		if (size == 0) {
			throw new Exception("The set is empty.");
		}
		int i = 0;
		while (buckets[i] == null || buckets[i].getSize() == 0) {
			i++;
		}		
		T result = buckets[i].dequeue();
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

	public String toString() {
		String str = "";
		if (size != 0) {
			for (int i = 0; i < buckets.length; i++) {
				if (buckets[i] != null) {
					T result = null;
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

}
