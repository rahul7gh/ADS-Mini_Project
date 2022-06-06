package implementations;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import implementations.bucketEvaluators.Log2BucketEvaluator;
import interfaces.HashFunctionIface;
import interfaces.HashTableIface;
import models.BinaryTreeNode;

@SuppressWarnings("unchecked")
public class HashTable<T extends Comparable<? super T>> implements HashTableIface<T>{

	private AVLTree<T> table[];
	private int size;
	private int count;
	private int resizingFactor;
	private double loadFactor;
	private HashFunctionIface<T> hashFunction;
	private Log2BucketEvaluator bucketEvaluator;
	
	{
		bucketEvaluator=new Log2BucketEvaluator();
	}
	
	
	public HashTable(HashFunctionIface<T> hashFunctionIface) {
		this.size=10;
		this.loadFactor=0.75;
		this.resizingFactor=2;
		this.hashFunction=hashFunctionIface;
		this.table=(AVLTree<T>[])Array.newInstance(AVLTree.class, size);
	}
	
	public HashTable(int size,HashFunctionIface<T> hashFunctionIface) {
		this.size=size;
		this.loadFactor=0.75;
		this.resizingFactor=2;
		this.hashFunction=hashFunctionIface;
		this.table=(AVLTree<T>[])Array.newInstance(AVLTree.class, size);
	}
	
	public HashTable(int size,double loadFactor,int resizingFactor,HashFunctionIface<T> hashFunctionIface) {
		this.size=size;
		this.loadFactor=loadFactor;
		this.resizingFactor=resizingFactor;
		this.hashFunction=hashFunctionIface;
		this.table=(AVLTree<T>[])Array.newInstance(AVLTree.class, size);
	}
	
	
	
	public AVLTree<T>[] getTable() {
		return table;
	}
	public void setTable(AVLTree<T>[] table) {
		this.table = table;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public double getLoadFactor() {
		return loadFactor;
	}
	public void setLoadFactor(double loadFactor) {
		this.loadFactor = loadFactor;
	}
	
	@Override
	public void add(T key) {
		int hash=hashFunction.getHash(key, size);
		if(table[hash]==null)
		{
			table[hash]=new AVLTree<>();
			count++;
		}
		table[hash].add(new BinaryTreeNode<T>(key));
		
		boolean isHashTableEfficient=isHashTableEfficient();
		
		double currentLoad=(1.0*count)/size;
		if(currentLoad>=loadFactor 
				|| !isHashTableEfficient
				)
		{
			rehash();
		}
	}

	private boolean isHashTableEfficient() {
		for (int i = 0; i < table.length; i++) {
			if(table[i]!=null)
			{
				if(!bucketEvaluator.isBucketEfficient(table[i].getSize(), size))
					return false;
			}
		}
		return true;
	}

	private void rehash() {
		AVLTree<T>[] resized= resize();
		for(int i=0;i<table.length;i++)
		{
			if(table[i]!=null)
			{
				BinaryTreeNode<T> current=table[i].getRoot();
				
				inOrderRehashingOfIthBucket(current,resized);
			}
		}
		table=resized;
		size=resized.length;
		
	}

	private void inOrderRehashingOfIthBucket(BinaryTreeNode<T> current, AVLTree<T>[] resized) {
		if(current==null) return;
		int hash=hashFunction.getHash(current.getData(), resized.length);
		
		if(resized[hash]==null)
			resized[hash]=new AVLTree<>();
		
//		resized[hash].add(current);
		resized[hash].add(new BinaryTreeNode<T>(current.getData()));
		
		if(current.getLeft()!=null)
			inOrderRehashingOfIthBucket(current.getLeft(), resized);
		
		if(current.getRight()!=null)
			inOrderRehashingOfIthBucket(current.getRight(), resized);
	}

	private AVLTree<T>[] resize() {
		return (AVLTree<T>[]) Array.newInstance(AVLTree.class, resizingFactor*size);
	}

	@Override
	public BinaryTreeNode<T> delete(T key) {
		int hash=hashFunction.getHash(key, size);
		if(table[hash]!=null)
			return null;
		return table[hash].delete(new BinaryTreeNode<T>(key));
	}

	@Override
	public BinaryTreeNode<T> search(T key) {
		int hash=hashFunction.getHash(key, size);
		if(table[hash]!=null)
			return null;
		return table[hash].search(new BinaryTreeNode<T>(key));
	}
	@Override
	public String toString() {
		return "HashTable [table=" + Arrays.toString(table) + ", size=" + size + ", loadFactor=" + loadFactor + "]";
	}
	
	public void debug()
	{
		for (int i = 0; i < table.length; i++) {
			if(table[i]==null) continue;
			System.out.println("<--"+i+"-->");
			table[i].printTreeLevelOrder();
//			Map<Integer, List<BinaryTreeNode<T>>> map = table[i].levelOrderTraversal();
//			
//			map.entrySet().stream().forEach(
//					entry-> entry.getValue().stream().filter(node-> node.getData()!=null)
//					.forEach(node-> System.out.println(entry.getKey()+"<>"+node+"<>"))
//					);
			System.out.println("<--->");
		}
	}

	
	
}
