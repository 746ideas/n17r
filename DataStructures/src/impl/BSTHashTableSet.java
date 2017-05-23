package impl;

import adt.HashTableSet;

public class BSTHashTableSet<T extends Comparable> implements HashTableSet<T> {

	private BSTSet<T>[] buckets;
	private int size;

	public BSTHashTableSet(int n) {
		buckets = new BSTSet[n];
		size = 0;
	}

	@Override
	public void add(T value) {
		int hc = Math.abs(value.hashCode()) % buckets.length;

		if (buckets[hc] == null) {
			BSTSet<T> temp = new BSTSet<>();
			temp.add(value);
			buckets[hc] = temp;
			size++;
		} else {
			if (!buckets[hc].contains(value)) {
				buckets[hc].add(value);
				size++;
			}
		}

	}

	@Override
	public boolean contains(T value) {
		int hc = Math.abs(value.hashCode()) % buckets.length;
		boolean checker = false;

		if (buckets[hc] != null) {
			if (buckets[hc].contains(value)) {
				checker = true;
			}
		}

		return checker;
	}

	@Override
	public boolean remove(T value) {
		int hc = Math.abs(value.hashCode()) % buckets.length;
		boolean checker = false;

		if (buckets[hc] != null && buckets[hc].getSize() != 0) {
			if (buckets[hc].remove(value)) {
				checker = true;
				size--;
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
		T result = buckets[i].removeAny();
		size--;

		return result;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void clear() {
		size = 0;
		buckets = new BSTSet[getNumberOfBuckets()];
	}

        @Override
	public String toString() {
		String str = "";
		if (size != 0) {
			for (int i = 0; i < getNumberOfBuckets(); i++) {
				if (buckets[i] != null) {
					T result = null;
					BSTSet<T> temp = new BSTSet<>();
					int bsize = 0;
					try {
						bsize = getBucketSize(i);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					for (int j = 0; j < bsize; j++) {
						try {
							result = buckets[i].removeAny();
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						temp.add(result);
						str += result + " ";
					}
					for (int j = 0; j < bsize; j++) {
						try {
							buckets[i].add(temp.removeAny());
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
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
		if (index >= getNumberOfBuckets()) {
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
		for (int i = 0; i < getNumberOfBuckets(); i++) {
			if (buckets[i] != null && buckets[i].getSize() != 0) {
				str += "\n" + i + ": " + buckets[i];
			}
		}
		return "{" + str + "}";
	}

}
