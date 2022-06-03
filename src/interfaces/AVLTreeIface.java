package interfaces;

import models.BinaryTreeNode;

public interface AVLTreeIface<T extends Comparable<? super T>> {
	
	 void add(BinaryTreeNode<T> keyNode);
	 void delete(BinaryTreeNode<T> keyNode);
	 void search(BinaryTreeNode<T> keyNode);

}
