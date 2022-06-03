package interfaces;

public interface HashFunctionIface<T extends Comparable<? super T>> {
	
	int getHash(T key,int size);
}
