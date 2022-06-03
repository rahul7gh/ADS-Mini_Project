package implementations;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import interfaces.AVLTreeIface;
import models.BinaryTreeNode;

public class AVLTree<T extends Comparable<? super T>> implements AVLTreeIface<T> {

	private BinaryTreeNode<T> root;
	
	public AVLTree() {
		this.root=null;
	}
	
	public BinaryTreeNode<T> getRoot() {
		return root;
	}

//	public void setRoot(BinaryTreeNode<T> root) {
//		this.root = root;
//	}

	@Override
	public void add(BinaryTreeNode<T> keyNode) {
		if(root==null)
		{
			root=keyNode;
			return;
		}
		BinaryTreeNode<T> cur=root;
		Stack<BinaryTreeNode<T>> stack= new Stack<>();
		while(cur!=null)
		{
			stack.push(cur);
			if(keyNode.compareTo(cur)<=0)
				cur=cur.getLeft();
			else
				cur=cur.getRight();
		}
		if(keyNode.compareTo(stack.peek())<=0)
			stack.peek().setLeft(keyNode);
		else
			stack.peek().setRight(keyNode);
			
		while(!stack.isEmpty())
		{
			int balanceFactor = getBalanceFactor(stack.peek());
			if(balanceFactor>1 || balanceFactor<-1) break;
			stack.pop();
		}
		if(stack.isEmpty()) return;
		BinaryTreeNode<T> g=stack.pop();
		BinaryTreeNode<T> p=null;
		BinaryTreeNode<T> x=null;
		BinaryTreeNode<T> gparent=null;
		if(!stack.isEmpty())
			gparent=stack.pop();
		
		StringBuilder imbalanceType=new StringBuilder("");
		
		if(keyNode.compareTo(g)<=0)
		{
			p=g.getLeft();
			imbalanceType.append("L");
		}
		else 
		{
			p=g.getRight();
			imbalanceType.append("R");
		}
			
		if(keyNode.compareTo(p)<=0)
		{
			x=p.getLeft();
			imbalanceType.append("L");
		}
		else
		{
			x=p.getRight();
			imbalanceType.append("R");
		}
		
		switch(imbalanceType.toString())
		{
			case "LL":
				rightRotate(gparent,g,p);
				break;
			
			case "LR":
				leftRotate(g,p,x);
				rightRotate(gparent,g,x);
				break;
				
			case "RL":
				rightRotate(g,p,x);
				leftRotate(gparent,g,x);
				break;
			
			case "RR":
				leftRotate(gparent,g,p);
				break;
		}
		
	}
	

	public void leftRotate(BinaryTreeNode<T> gparent, BinaryTreeNode<T> g, BinaryTreeNode<T> p) {
		g.setRight(p.getLeft());
		p.setLeft(g);
		if(gparent==null)
			root=p;
		else
		{
			if(g.compareTo(gparent)<=0)
				gparent.setLeft(p);
			else
				gparent.setRight(p);
		}

		
	}

	public void rightRotate(BinaryTreeNode<T> gparent, BinaryTreeNode<T> g, BinaryTreeNode<T> p) {
	
		g.setLeft(p.getRight());
		p.setRight(g);
		if(gparent==null)
			root=p;
		else
		{
			if(g.compareTo(gparent)<=0)
				gparent.setLeft(p);
			else
				gparent.setRight(p);
		}

	}

	@Override
	public void delete(BinaryTreeNode<T> keyNode) {
	}

	@Override
	public void search(BinaryTreeNode<T> keyNode) {
	}
	
	public int getBalanceFactor(BinaryTreeNode<T> node)
	{
		return (getHeight(node.getLeft())-1) - (getHeight(node.getRight())-1);
	}

	public int getHeight(BinaryTreeNode<T> node) {
		if(node==null) return 0;
		
		int lh=0,rh=0;
		
		if(node.getLeft()!=null)
			lh=getHeight(node.getLeft());
		
		if(node.getRight()!=null)
			lh=getHeight(node.getRight());
		
		return Math.max(lh,rh)+1;
	}

	@Override
	public String toString() {
		return "AVLTree [root=" + root + "]";
	}

	public Map<Integer,List<BinaryTreeNode<T>>> levelOrderTraversal()
	{
		if (root != null) {
			Map<Integer, List<BinaryTreeNode<T>>> map = new HashMap<>();
			
			Queue<BinaryTreeNode<T>> q = new ArrayDeque<>();
			
			final BinaryTreeNode<T> NULLNODE = new BinaryTreeNode<>(null);
			
			q.add(root);
			
			int current = 1,next = 0,level = 0;
			
			while (!q.isEmpty()) {
				BinaryTreeNode<T> removed = q.remove();

				List<BinaryTreeNode<T>> list = map.get(level);
				if (list == null) {
					map.put(level, new ArrayList<>());
				}
				map.get(level).add(removed);
				current--;
				
				if (removed == NULLNODE)
					continue;
				
				if (removed.getLeft() != null) {
					q.add(removed.getLeft());
				} 
				else {
					q.add(NULLNODE);
				}
				next++;

				if (removed.getRight() != null) {
					q.add(removed.getRight());
				}
				else {
					q.add(NULLNODE);
				}
				next++;

				if (current == 0) {
					current = next;
					next = 0;
					level++;
				}

			}
			return map;
		}
		return null;
	}
	
	
}
