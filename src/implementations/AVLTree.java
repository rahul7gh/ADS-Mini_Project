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
	private int size;
	
	public AVLTree() {
		this.root=null;
		this.size=0;
	}
	
	public BinaryTreeNode<T> getRoot() {
		return root;
	}

	
//	public void setRoot(BinaryTreeNode<T> root) {
//		this.root = root;
//	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void add(BinaryTreeNode<T> keyNode) {
		if(root==null)
		{
			root=keyNode;
			size++;
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
			
		size++;
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
	public BinaryTreeNode<T> delete(BinaryTreeNode<T> keyNode) {
		
		return deleteRecursive(root, keyNode);
		
	}
	
	private BinaryTreeNode<T> deleteRecursive(BinaryTreeNode<T> node,BinaryTreeNode<T> keyNode) {
		
		if(node==null)
			return node;
		
		//key<node.data
		if(keyNode.compareTo(node)<0) {
			node.setLeft(deleteRecursive(node.getLeft(),keyNode));
		}
		//key>node.data
		else if(keyNode.compareTo(node)>0) {
			node.setRight(deleteRecursive(node.getRight(),keyNode));
		}
		//key==node.data
		else {
			if ((node.getLeft() == null) || (node.getRight() == null))
            {
				BinaryTreeNode<T> temp = null;
                if (temp == node.getLeft())
                    temp = node.getRight();
                else
                    temp = node.getRight();
 
                
                if (temp == null)
                {
                    temp = node;
                    node = null;
                }
                else 
                    node = temp; 
            }else
            {           
            	BinaryTreeNode<T> temp = minValueNode(node.getRight());
 
               node.setData(temp.getData());
               node.setRight(deleteRecursive(node.getRight(), temp));
            }
		}
		
		if (node == null)
            return node;
 
       
        int balance = getBalanceFactor(node);
 
        
        // Left Left Case
        if (balance > 1 && getBalanceFactor(node.getLeft()) >= 0)
            return right(node);
 
        // Left Right Case
        if (balance > 1 && getBalanceFactor(node.getLeft()) < 0)
        {
        	node.setLeft(left(node.getLeft()));
            return right(node);
        }
 
        // Right Right Case
        if (balance < -1 && getBalanceFactor(node.getRight()) <= 0)
            return left(node);
 
        // Right Left Case
        if (balance < -1 && getBalanceFactor(node.getRight()) > 0)
        {
        	node.setRight(right(node.getRight()));
            return left(node);
        }
 
        return node;
	}
	
	private BinaryTreeNode<T> left(BinaryTreeNode<T> x) {
		BinaryTreeNode<T> y = x.getRight();
		BinaryTreeNode<T> T2 = y.getLeft();
 
        
		y.setLeft(x);
        x.setRight(T2);
        
		return y;
	}

	private BinaryTreeNode<T> right(BinaryTreeNode<T> y) {
		BinaryTreeNode<T> x = y.getLeft();
		BinaryTreeNode<T> T2 = x.getRight();
	 
			x.setRight(y);
	        y.setLeft(T2);
	        
	 
		return x;
	}

	private BinaryTreeNode<T> minValueNode(BinaryTreeNode<T> node) {
		BinaryTreeNode<T> current = node;
		 
        while (current.getLeft() != null)
        	current = current.getLeft();
 
        return current;
	}

	@Override
	public BinaryTreeNode<T> search(BinaryTreeNode<T> keyNode) {
		
		return searchRecursive(root,keyNode);
	}
	
	private BinaryTreeNode<T> searchRecursive(BinaryTreeNode<T> node, BinaryTreeNode<T> key){
		if (key.equals(node))
	        return node;
		if(node==null)
			return null;
		//key>node
	    if (key.compareTo(node)>0)
	       return searchRecursive(node.getRight(), key);
	 
	    //key<node
	    return searchRecursive(node.getLeft(), key);
		
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
