package interfaces;

import models.BinaryTreeNode;

public interface HashTableIface<T extends Comparable<? super T>> {
	
	void add(T key);
	BinaryTreeNode<T> delete(T key);
	BinaryTreeNode<T> search(T key);

}
