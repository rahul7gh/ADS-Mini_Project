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
				
		if(keyNode==null || root==null) return null;
		
		Stack<BinaryTreeNode<T>> stack= new Stack<>();
		BinaryTreeNode<T> current=root;
		while(current!=null)
		{
			if(keyNode.equals(current)) break;
			stack.push(current);
			if(keyNode.compareTo(current)<=0)
				current=current.getLeft();
			else
				current=current.getRight();
				
		}
		
		if(current==null) return null;
		BinaryTreeNode<T> retVal=current;
		size--;
		if(current.getLeft()==null && current.getRight()==null)
		{
			if(current==root)
			{

				root=null;
				return retVal;
			}
			else
			{

				if(current.compareTo(stack.peek())<=0)
					stack.peek().setLeft(null);
				else
					stack.peek().setRight(null);
			}
			
			while(!stack.isEmpty())
			{
				int bf=getBalanceFactor(stack.peek());
				if(bf>1 || bf<-1) break;
				
				stack.pop();			
			}
			if(stack.isEmpty()) return retVal;
			
			BinaryTreeNode<T> g= stack.pop();
			BinaryTreeNode<T> gparent=null;
			if(!stack.isEmpty())
				gparent=stack.pop();
			
			StringBuilder imbalanceType=new StringBuilder("");
			
			BinaryTreeNode<T> p= null;
			BinaryTreeNode<T> x= null;
			if(current.compareTo(g)<=0)
			{
				imbalanceType.append("R");
				p=g.getRight();
			}
			else
			{
				imbalanceType.append("L");
				p=g.getLeft();
			}
			if(p!=null)
			{
				if(imbalanceType.toString().equals("R"))
				{
					if(getBalanceFactor(p)<=0)
					{
						imbalanceType.append("R");
					}
					else
					{
						imbalanceType.append("L");
						x=p.getLeft();
					}
				}
				else
				{
					if(getBalanceFactor(p)>=0)
					{
						imbalanceType.append("L");
					}
					else
					{
						imbalanceType.append("R");
						x=p.getRight();
					}
				}
				
				switch(imbalanceType.toString())
				{
					case "LL":
							rightRotate(gparent, g, p);
							break;
						
					case "RR":
							leftRotate(gparent, g, p);
							break;
						
					case "LR":
							leftRotate(g, p, x);
							rightRotate(gparent, g, x);
							break;
						
					case "RL":
							rightRotate(g, p, x);
							leftRotate(gparent, g, x);
							break;	
						
				}
			}
		
			
		}
		else
		{

			Stack<BinaryTreeNode<T>> succStack=new Stack<>();
			BinaryTreeNode<T> successor=current.getRight();
			BinaryTreeNode<T> successorParent=null;
			while(successor!=null)
			{
				if(successor.getLeft()==null) break;
				succStack.push(successor);
				successorParent=successor;
				successor=successor.getLeft();
			}
			
			if(successor==null)
			{
				if(current==root)
				{
					root=root.getLeft();
				}
				else
				{
					if(current.compareTo(stack.peek())<=0)
						stack.peek().setLeft(current.getLeft());
					else
						stack.peek().setRight(current.getLeft());
				}
			}
			else
			{
				swapDataOfNodes(current,successor);
				if(successorParent==null)
				{
					current.setRight(successor.getRight());
				}
				else
				{
					successorParent.setLeft(successor.getRight());
				}
			}
			
			while(!succStack.isEmpty())
			{
				int bf=getBalanceFactor(succStack.peek());
				if(bf>1 || bf<-1) break;
				
				succStack.pop();			
			}
			if(succStack.isEmpty()) return retVal;
			
			BinaryTreeNode<T> g= succStack.pop();
			BinaryTreeNode<T> gparent=current;
			if(!succStack.isEmpty())
				gparent=succStack.pop();
			
			StringBuilder imbalanceType=new StringBuilder("");
			
			BinaryTreeNode<T> p= null;
			BinaryTreeNode<T> x= null;
			if(successor.compareTo(g)<=0)
			{
				imbalanceType.append("R");
				p=g.getRight();
			}
			else
			{
				imbalanceType.append("L");
				p=g.getLeft();
			}
			if(p!=null)
			{
				if(imbalanceType.toString().equals("R"))
				{
					if(getBalanceFactor(p)<=0)
					{
						imbalanceType.append("R");
					}
					else
					{
						imbalanceType.append("L");
						x=p.getLeft();
					}
				}
				else
				{
					if(getBalanceFactor(p)>=0)
					{
						imbalanceType.append("L");
					}
					else
					{
						imbalanceType.append("R");
						x=p.getRight();
					}
				}
				
				switch(imbalanceType.toString())
				{
					case "LL":
							rightRotate(gparent, g, p);
							break;
						
					case "RR":
							leftRotate(gparent, g, p);
							break;
						
					case "LR":
							leftRotate(g, p, x);
							rightRotate(gparent, g, x);
							break;
						
					case "RL":
							rightRotate(g, p, x);
							leftRotate(gparent, g, x);
							break;	
						
				}
			}
				
		}

		return retVal;
		
	}

	private void swapDataOfNodes(BinaryTreeNode<T> current, BinaryTreeNode<T> successor) {
		T temp=current.getData();
		current.setData(successor.getData());
		successor.setData(temp);
	}

	@Override
	public BinaryTreeNode<T> search(BinaryTreeNode<T> keyNode) {
		
		return searchRecursive(root,keyNode);
	}
	
	private BinaryTreeNode<T> searchRecursive(BinaryTreeNode<T> node, BinaryTreeNode<T> key){
		
		if(node==null)
			return null;
		
		if (key.equals(node))
	        return node;
		
		//key>node
	    if (key.compareTo(node)>0)
	       return searchRecursive(node.getRight(), key);
	 
	    //key<node
	    return searchRecursive(node.getLeft(), key);
		
	}

	public void heightAndBalanceFactorOfEveryNode(BinaryTreeNode<T> node)
	{
		if(node!=null)
		{
			heightAndBalanceFactorOfEveryNode(node.getLeft());
			System.out.println(node+" "+getHeight(node)+" "+getBalanceFactor(node));
			heightAndBalanceFactorOfEveryNode(node.getRight());
		}
		
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
			rh=getHeight(node.getRight());
		
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
			map.forEach((k,v) -> System.out.println(k+ "<>"+v));
			return map;
		}
		return null;
	}
	
	
}