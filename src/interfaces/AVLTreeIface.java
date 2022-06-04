package interfaces;

import models.BinaryTreeNode;

public interface AVLTreeIface<T extends Comparable<? super T>> {
	
	 void add(BinaryTreeNode<T> keyNode);
	 BinaryTreeNode<T> delete(BinaryTreeNode<T> keyNode);
	 BinaryTreeNode<T> search(BinaryTreeNode<T> keyNode);

}
