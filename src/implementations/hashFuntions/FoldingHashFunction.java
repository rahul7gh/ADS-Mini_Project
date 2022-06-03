package implementations.hashFuntions;

import interfaces.HashFunctionIface;

public class FoldingHashFunction<T extends Comparable<? super T>> implements HashFunctionIface<T>  {

	@Override
	public int getHash(T key,int size) {
		return key.hashCode() % size;
	}


}
