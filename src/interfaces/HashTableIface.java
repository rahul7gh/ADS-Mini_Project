package interfaces;

public interface HashTableIface<T extends Comparable<? super T>> {
	
	void add(T key);
	void delete(T key);
	void search(T key);

}
