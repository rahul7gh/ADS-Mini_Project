package models;



public class BinaryTreeNode<T extends Comparable<? super T>>
	implements Comparable<BinaryTreeNode<T>> {
	
	private T data;
	private BinaryTreeNode<T> left;
	private BinaryTreeNode<T> right;
	
	private int height;
	
	public BinaryTreeNode(T data) {
		super();
		this.data = data;
		this.left = null;
		this.right = null;
		height=1;
	}
	
	
	
	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public BinaryTreeNode<T> getLeft() {
		return left;
	}
	public void setLeft(BinaryTreeNode<T> left) {
		this.left = left;
	}
	public BinaryTreeNode<T> getRight() {
		return right;
	}
	public void setRight(BinaryTreeNode<T> right) {
		this.right = right;
	}
	
	@Override
	public String toString() {
		return "[data=" + data + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BinaryTreeNode ))
			return false;
		BinaryTreeNode<T> anNode=(BinaryTreeNode)obj;
		return data.equals(anNode.getData());
	}
	
	@Override
	public int compareTo(BinaryTreeNode<T> o) {
		return data.compareTo(o.getData());
	}
	
	
	
}
