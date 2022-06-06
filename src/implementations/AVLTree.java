package implementations;

import java.util.ArrayDeque;
import java.util.LinkedList;

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
    
    
    int getHeight(BinaryTreeNode<T> node)
    {
        if (node == null)
            return 0;
         
        return node.getHeight();
    }
     
    void updateHeight(BinaryTreeNode<T> node)
    {
        if (node == null) return;
         
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1); 
    }
     
     
    BinaryTreeNode<T> rotateRight(BinaryTreeNode<T> node)
    {
        if (node == null) return node;
         
        BinaryTreeNode<T> beta  = node.getLeft();
         
        BinaryTreeNode<T> t2  = beta.getRight();
         
        beta.setRight(node);
        node.setLeft(t2);
         
        
        updateHeight(node);
         
        
        updateHeight(beta);
         
        return beta;
    }
     
     
    BinaryTreeNode<T> rotateLeft(BinaryTreeNode<T> node)
    {
        if (node == null) return node;
         
        BinaryTreeNode<T> beta  = node.getRight();
        BinaryTreeNode<T> t2  = beta.getLeft();
         
        beta.setLeft(node);
        node.setRight(t2);
         
       
        updateHeight(node);
         
   
        updateHeight(beta);
         
        return beta;
    }
     
     
    int getBalance(BinaryTreeNode<T> node)
    {
        if (node == null)
        {
            return 0;
        }
        int balance;
         
        balance = getHeight(node.getLeft()) - getHeight(node.getRight());
         
        return balance;
    }
     
     
    T getMinValue(BinaryTreeNode<T> node)
    {
        // though this case should not be hit ever for the usecase this function is employed for
        if (node == null) return null;
         
        // if this is the left-most node
        if (node.getLeft() == null) return node.getData();
         
        return getMinValue(node.getLeft());
    }
     
     
    private BinaryTreeNode<T> delete(BinaryTreeNode<T> node, BinaryTreeNode<T> keyNode)
    {
        // if empty tree 
        if (node == null) return null;
         
        if (keyNode.compareTo(node)<0)
        {
            node.setLeft(delete(node.getLeft(), keyNode));
        }
        else if (keyNode.compareTo(node)>0)
        {
            node.setRight(delete(node.getRight(), keyNode));
        }
         
        else 
        {
            // one child/no child case
            if (node.getLeft() == null)
            {
                node = node.getRight();
            }
            else if (node.getRight() == null)
            {
                node = node.getLeft();
            }
             

            else
            {
                T inorderSuccessorValue = getMinValue(node.getRight());
                node.setData(inorderSuccessorValue);
                node.setRight(delete(node.getRight(), new BinaryTreeNode<T>(inorderSuccessorValue)));
            }
        }
 
       
        if (node == null)
        {
            return null;
        }
         

        updateHeight(node);
         

        int balance = getBalance(node);
         
        if (balance > 1) // indicates either left-left or left-right case
        {
            if (getBalance(node.getLeft()) >= 0) // confirms left-left case
            {
                node = rotateRight(node);
            }
            else // confirms left-right case
            {
                node.setLeft(rotateLeft(node.getRight()));
                node = rotateRight(node);
            }
        }
         
        else if (balance < -1) // indicates either right-right or right-left case
        {
            if (getBalance(node.getRight()) <= 0) // confirms right-right case
            {
                node = rotateLeft(node);
            }
            else // confirms right-left case
            {
                node.setRight(rotateRight(node.getRight()));
                node = rotateLeft(node);
            }
        }
        return node;
    }
     
     
    private BinaryTreeNode<T> insert(BinaryTreeNode<T> node, BinaryTreeNode<T> keyNode)
    {
        // do usual BST insertion first
        if (node == null)
        {
            return keyNode;
        }
         
        if (keyNode.compareTo(node)<0)
        {
            node.setLeft(insert(node.getLeft(), keyNode));
        }
        else if (keyNode.compareTo(node)>0)
        {
            node.setRight(insert(node.getRight(), keyNode));
        }
        else
        {
            return node;
        }
         
       
        updateHeight(node);
         
        // check the balance at this node and perform rotations accordingly
        int balance = getBalance(node);
         
        if (balance > 1) // indicates either left-left or left-right case
        {
            if (keyNode.compareTo(node.getLeft())<0) // confirms left-left case
            {
                node = rotateRight(node);
            }
            else 
            {
                node.setLeft(rotateLeft(node.getLeft()));
                node = rotateRight(node);
            }
        }
         
        else if (balance < -1) // indicates either right-right or right-left case
        {
            if (keyNode.compareTo(node.getRight())>0) // confirms right-right case
            {
                node = rotateLeft(node);
            }
            else // confirms right-left case
            {
                node.setRight( rotateRight(node.getRight())); 
                node = rotateLeft(node);
            }
        }
        return node;
    }
     
     
    public void insert(T key)
    {
        root = insert(this.root, new BinaryTreeNode<T>(key));
        return;
    }
     
     
    public void delete(T key)
    {
        root = delete(this.root, new BinaryTreeNode<T>(key));
        return;
    }
     
     
    public void printTreeLevelOrder()
    {
        if (root == null) return;
         
        ArrayDeque<QueueNode<T>> queue = new ArrayDeque<>();
        queue.add(new QueueNode<T>(root, 0));
         
        int maxLevelVisited = -1;
         
        while (!queue.isEmpty())
        {
            QueueNode<T> currentNode = (QueueNode<T> ) queue.remove();
             
            if (currentNode.level > maxLevelVisited)
            {
                maxLevelVisited = currentNode.level;
                System.out.print("\nlevel-" + currentNode.level + " nodes: ");
            }
            System.out.print(" " + currentNode.treeNode.getData());
             
            if (currentNode.treeNode.getLeft() != null)
            {
                queue.add(new QueueNode<T>(currentNode.treeNode.getLeft(), currentNode.level + 1));
            }
             
            if (currentNode.treeNode.getRight() != null)
            {
                queue.add(new QueueNode<T>(currentNode.treeNode.getRight(), currentNode.level + 1));
            }
        }
    }
     
     
    public static void main(String[] args)
    {
        AVLTree<Integer> tree = new AVLTree<>();
 
        int size=40;
        
        for (int i = 0; i < size; i++) {
			tree.insert(i+1);
		}
 
        for (int i = 0; i < size; i++) {
        	System.out.print("Before :-"+( i+1));
        	
            tree.printTreeLevelOrder();
            System.out.println();
            tree.delete(i+1);
     
            System.out.print("After :-"+( i+1));
            tree.printTreeLevelOrder();
            System.out.println();
		}
        
        
    }

	@Override
	public void add(BinaryTreeNode<T> keyNode) {
		root=insert(root,keyNode);
	}

	@Override
	public BinaryTreeNode<T> delete(BinaryTreeNode<T> keyNode) {
		root= delete(root,keyNode);
		return root;
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
}
class QueueNode<T extends Comparable<? super T>> 
{
    BinaryTreeNode<T> treeNode;
    int level;
     
    QueueNode( BinaryTreeNode<T> treeNode, int level)
    {
        this.treeNode = treeNode;
        this.level = level;
    }
}


//import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Queue;
//import java.util.Stack;
//
//import interfaces.AVLTreeIface;
//import models.BinaryTreeNode;
//
//public class AVLTree<T extends Comparable<? super T>> implements AVLTreeIface<T> {
//
//	private BinaryTreeNode<T> root;
//	private int size;
//	
//	public AVLTree() {
//		this.root=null;
//		this.size=0;
//	}
//	
//	public BinaryTreeNode<T> getRoot() {
//		return root;
//	}
//
//	
////	public void setRoot(BinaryTreeNode<T> root) {
////		this.root = root;
////	}
//
//	public int getSize() {
//		return size;
//	}
//
//	public void setSize(int size) {
//		this.size = size;
//	}
//
//	@Override
//	public void add(BinaryTreeNode<T> keyNode) {
//		if(root==null)
//		{
//			root=keyNode;
//			size++;
//			return;
//		}
//		BinaryTreeNode<T> cur=root;
//		Stack<BinaryTreeNode<T>> stack= new Stack<>();
//		while(cur!=null)
//		{
//			stack.push(cur);
//			if(keyNode.compareTo(cur)<=0)
//				cur=cur.getLeft();
//			else
//				cur=cur.getRight();
//		}
//		if(keyNode.compareTo(stack.peek())<=0)
//			stack.peek().setLeft(keyNode);
//		else
//			stack.peek().setRight(keyNode);
//			
//		size++;
//		while(!stack.isEmpty())
//		{
//			int balanceFactor = getBalanceFactor(stack.peek());
//			if(balanceFactor>1 || balanceFactor<-1) break;
//			stack.pop();
//		}
//		if(stack.isEmpty()) return;
//		BinaryTreeNode<T> g=stack.pop();
//		BinaryTreeNode<T> p=null;
//		BinaryTreeNode<T> x=null;
//		BinaryTreeNode<T> gparent=null;
//		if(!stack.isEmpty())
//			gparent=stack.pop();
//		
//		StringBuilder imbalanceType=new StringBuilder("");
//		
//		if(keyNode.compareTo(g)<=0)
//		{
//			p=g.getLeft();
//			imbalanceType.append("L");
//		}
//		else 
//		{
//			p=g.getRight();
//			imbalanceType.append("R");
//		}
//			
//		if(keyNode.compareTo(p)<=0)
//		{
//			x=p.getLeft();
//			imbalanceType.append("L");
//		}
//		else
//		{
//			x=p.getRight();
//			imbalanceType.append("R");
//		}
//		
//		switch(imbalanceType.toString())
//		{
//			case "LL":
//				rightRotate(gparent,g,p);
//				break;
//			
//			case "LR":
//				leftRotate(g,p,x);
//				rightRotate(gparent,g,x);
//				break;
//				
//			case "RL":
//				rightRotate(g,p,x);
//				leftRotate(gparent,g,x);
//				break;
//			
//			case "RR":
//				leftRotate(gparent,g,p);
//				break;
//		}
//		
//	}
//	
//
//	public void leftRotate(BinaryTreeNode<T> gparent, BinaryTreeNode<T> g, BinaryTreeNode<T> p) {
//		g.setRight(p.getLeft());
//		p.setLeft(g);
//		if(gparent==null)
//			root=p;
//		else
//		{
//			if(g.compareTo(gparent)<=0)
//				gparent.setLeft(p);
//			else
//				gparent.setRight(p);
//		}
//
//		
//	}
//
//	public void rightRotate(BinaryTreeNode<T> gparent, BinaryTreeNode<T> g, BinaryTreeNode<T> p) {
//	
//		g.setLeft(p.getRight());
//		p.setRight(g);
//		if(gparent==null)
//			root=p;
//		else
//		{
//			if(g.compareTo(gparent)<=0)
//				gparent.setLeft(p);
//			else
//				gparent.setRight(p);
//		}
//
//	}
//
//	@Override
//	public BinaryTreeNode<T> delete(BinaryTreeNode<T> keyNode) {
//		BinaryTreeNode<T> res = delete(root,keyNode,null);
////		balanceCurrentNode(res, root);
//		return res;
//				
//	}
//	
//	public BinaryTreeNode<T> delete(BinaryTreeNode<T> current,BinaryTreeNode<T> keyNode,BinaryTreeNode<T> parent) {
//		if(current==null || keyNode==null)
//			return null;
//		
//		if(keyNode.equals(current))
//		{
//			BinaryTreeNode<T> retValue= null;
//			if(current.getLeft()==null && current.getRight()==null)
//			{
//				retValue=current;
//				if(parent==null)
//				{
//					root=null;
//				}
//				else
//				{
//					if(current.compareTo(parent)<=0)
//						parent.setLeft(null);
//					else
//						parent.setRight(null);
//					
//					
//				}
//			}
//			else
//			{
//				if(current.getRight()==null)
//				{
//					retValue=current;
//					if(current==root)
//					{
//						root=root.getLeft();
//					}
//					else
//					{
//						if(current.compareTo(parent)<=0)
//							parent.setLeft(current.getLeft());
//						else
//							parent.setRight(current.getLeft());
//					}
//				}
//				else if(current.getRight().getLeft()==null)
//				{
//					swapDataOfNodes(current, current.getRight());
//					retValue=current.getRight();
//					current.setRight(current.getRight().getRight());
//				}
//				else
//				{
//					BinaryTreeNode<T> successor=current.getRight();
//					BinaryTreeNode<T> successorParent=null;
//					while(successor!=null)
//					{
//						if(successor.getLeft()==null) break;
//						successorParent=successor;
//						successor=successor.getLeft();
//					}
//					swapDataOfNodes(current, successor);
////					successorParent.setLeft(successor.getRight());
//					return delete(current.getRight(),successor,current);
//				}
//			}
//			
//			return current;
//		}
//		else if(keyNode.compareTo(current)<=0)
//		{
//			
//			BinaryTreeNode<T> res = delete(current.getLeft(),keyNode,current);
////			balanceCurrentNode(current, parent);
//			return res;
//		}
//		else 
//		{
//			BinaryTreeNode<T> res = delete(current.getRight(),keyNode,current);
////			balanceCurrentNode(current, parent);
//			return res;
//		}
////		
////
////		
////		if(keyNode==null || root==null) return null;
////		
////		Stack<BinaryTreeNode<T>> stack= new Stack<>();
////		BinaryTreeNode<T> current=root;
////		while(current!=null)
////		{
////			if(keyNode.equals(current)) break;
////			stack.push(current);
////			if(keyNode.compareTo(current)<=0)
////				current=current.getLeft();
////			else
////				current=current.getRight();
////				
////		}
////		
////		if(current==null) return null;
////		BinaryTreeNode<T> retVal=current;
////		size--;
////		if(current.getLeft()==null && current.getRight()==null)
////		{
////			if(current==root)
////			{
//////				retVal=current;
////				root=null;
//////				System.out.println(getSize());
////				return retVal;
////			}
////			else
////			{
//////				retVal=current;
////				if(current.compareTo(stack.peek())<=0)
////					stack.peek().setLeft(null);
////				else
////					stack.peek().setRight(null);
////			}
////			
////			while(!stack.isEmpty())
////			{
////				int bf=getBalanceFactor(stack.peek());
////				if(bf>1 || bf<-1) break;
////				
////				stack.pop();			
////			}
////			if(stack.isEmpty()) return retVal;
////			
////			BinaryTreeNode<T> g= stack.pop();
////			BinaryTreeNode<T> gparent=null;
////			if(!stack.isEmpty())
////				gparent=stack.pop();
////			
////			StringBuilder imbalanceType=new StringBuilder("");
////			
////			BinaryTreeNode<T> p= null;
////			BinaryTreeNode<T> x= null;
////			if(current.compareTo(g)<=0)
////			{
////				imbalanceType.append("R");
////				p=g.getRight();
////			}
////			else
////			{
////				imbalanceType.append("L");
////				p=g.getLeft();
////			}
////			if(p!=null)
////			{
////				if(imbalanceType.toString().equals("R"))
////				{
////					if(getBalanceFactor(p)<=0)
////					{
////						imbalanceType.append("R");
////					}
////					else
////					{
////						imbalanceType.append("L");
////						x=p.getLeft();
////					}
////				}
////				else
////				{
////					if(getBalanceFactor(p)>=0)
////					{
////						imbalanceType.append("L");
////					}
////					else
////					{
////						imbalanceType.append("R");
////						x=p.getRight();
////					}
////				}
////				
////				switch(imbalanceType.toString())
////				{
////					case "LL":
////							rightRotate(gparent, g, p);
////							break;
////						
////					case "RR":
////							leftRotate(gparent, g, p);
////							break;
////						
////					case "LR":
////							leftRotate(g, p, x);
////							rightRotate(gparent, g, x);
////							break;
////						
////					case "RL":
////							rightRotate(g, p, x);
////							leftRotate(gparent, g, x);
////							break;	
////						
////				}
////			}
////		
////			
////		}
////		else
////		{
//////			stack.clear();
////			Stack<BinaryTreeNode<T>> succStack=new Stack<>();
////			BinaryTreeNode<T> successor=current.getRight();
////			BinaryTreeNode<T> successorParent=null;
////			while(successor!=null)
////			{
////				if(successor.getLeft()==null) break;
////				succStack.push(successor);
////				successorParent=successor;
////				successor=successor.getLeft();
////			}
////			
////			if(successor==null)
////			{
////				if(current==root)
////				{
////					root=root.getLeft();
////				}
////				else
////				{
////					if(current.compareTo(stack.peek())<=0)
////						stack.peek().setLeft(current.getLeft());
////					else
////						stack.peek().setRight(current.getLeft());
////				}
////			}
////			else
////			{
////				swapDataOfNodes(current,successor);
////				if(successorParent==null)
////				{
////					current.setRight(successor.getRight());
////				}
////				else
////				{
////					successorParent.setLeft(successor.getRight());
////				}
////			}
////			
////			while(!succStack.isEmpty())
////			{
////				int bf=getBalanceFactor(succStack.peek());
////				if(bf>1 || bf<-1) break;
////				
////				succStack.pop();			
////			}
////			if(succStack.isEmpty()) return retVal;
////			
////			BinaryTreeNode<T> g= succStack.pop();
////			BinaryTreeNode<T> gparent=current;
////			if(!succStack.isEmpty())
////				gparent=succStack.pop();
////			
////			StringBuilder imbalanceType=new StringBuilder("");
////			
////			BinaryTreeNode<T> p= null;
////			BinaryTreeNode<T> x= null;
////			if(successor.compareTo(g)<=0)
////			{
////				imbalanceType.append("R");
////				p=g.getRight();
////			}
////			else
////			{
////				imbalanceType.append("L");
////				p=g.getLeft();
////			}
////			if(p!=null)
////			{
////				if(imbalanceType.toString().equals("R"))
////				{
////					if(getBalanceFactor(p)<=0)
////					{
////						imbalanceType.append("R");
////					}
////					else
////					{
////						imbalanceType.append("L");
////						x=p.getLeft();
////					}
////				}
////				else
////				{
////					if(getBalanceFactor(p)>=0)
////					{
////						imbalanceType.append("L");
////					}
////					else
////					{
////						imbalanceType.append("R");
////						x=p.getRight();
////					}
////				}
////				
////				switch(imbalanceType.toString())
////				{
////					case "LL":
////							rightRotate(gparent, g, p);
////							break;
////						
////					case "RR":
////							leftRotate(gparent, g, p);
////							break;
////						
////					case "LR":
////							leftRotate(g, p, x);
////							rightRotate(gparent, g, x);
////							break;
////						
////					case "RL":
////							rightRotate(g, p, x);
////							leftRotate(gparent, g, x);
////							break;	
////						
////				}
////			}
////				
////		}
////	
////		
////		
//////		System.out.println(getSize());
////		
////		
////		return retVal;
//////		return deleteRecursive(root, keyNode);
////		
////	
////		
////		
////		
//////		return deleteRecursive(root, keyNode);
////		
//	}
//	
//	
//	private void balanceCurrentNode(BinaryTreeNode<T> current, BinaryTreeNode<T> parent) {
//		int bf=getBalanceFactor(current);
//		
//		
//		
//		if (bf >=-1 && bf <=1)
//			return;
//		
//		StringBuilder imbalanceType=new StringBuilder("");
//		
//		BinaryTreeNode<T> gparent=parent;
//		BinaryTreeNode<T> g=current;
//		
//		BinaryTreeNode<T> p=null;
//		BinaryTreeNode<T> x=null;
//		
//		if(current.compareTo(parent)<=0)
//		{
//			imbalanceType.append("R");
//			p=g.getRight();
//			
//			if(getBalanceFactor(p)<=0)
//			{
//				imbalanceType.append("R");
//			}
//			else
//			{
//				imbalanceType.append("L");
//				x=p.getLeft();
//			}
//		}
//		else
//		{
//			imbalanceType.append("L");
//			p=g.getLeft();
//			
//			if(getBalanceFactor(p)>=0)
//			{
//				imbalanceType.append("L");
//			}
//			else
//			{
//				imbalanceType.append("R");
//				x=p.getRight();
//			}
//		}
//		
//		switch(imbalanceType.toString())
//		{
//			case "LL":
//					rightRotate(gparent, g, p);
//					break;
//				
//			case "RR":
//					leftRotate(gparent, g, p);
//					break;
//				
//			case "LR":
//					leftRotate(g, p, x);
//					rightRotate(gparent, g, x);
//					break;
//				
//			case "RL":
//					rightRotate(g, p, x);
//					leftRotate(gparent, g, x);
//					break;	
//				
//		}
//		
//		
//	}
//
//private void swapDataOfNodes(BinaryTreeNode<T> current, BinaryTreeNode<T> successor) {
//	T temp=current.getData();
//	current.setData(successor.getData());
//	successor.setData(temp);
//}
//
////	private BinaryTreeNode<T> deleteRecursive(BinaryTreeNode<T> node,BinaryTreeNode<T> keyNode) { 
////		
////		if(node==null)
////			return node;
////		
////		//key<node.data
////		if(keyNode.compareTo(node)<0) {
////			node.setLeft(deleteRecursive(node.getLeft(),keyNode));
////		}
////		//key>node.data
////		else if(keyNode.compareTo(node)>0) {
////			node.setRight(deleteRecursive(node.getRight(),keyNode));
////		}
////		//key==node.data
////		else {
////			if ((node.getLeft() == null) || (node.getRight() == null))
////            {
////				BinaryTreeNode<T> temp = null;
////                if (temp == node.getLeft())
////                    temp = node.getRight();
////                else
////                    temp = node.getRight();
//// 
////                
////                if (temp == null)
////                {
////                    temp = node;
////                    node = null;
////                }
////                else 
////                    node = temp; 
////            }else
////            {           
////            	BinaryTreeNode<T> temp = minValueNode(node.getRight());
//// 
////               node.setData(temp.getData());
////               node.setRight(deleteRecursive(node.getRight(), temp));
////            }
////		}
////		
////		if (node == null)
////            return node;
//// 
////       
////        int balance = getBalanceFactor(node);
//// 
////        
////        // Left Left Case
////        if (balance > 1 && getBalanceFactor(node.getLeft()) >= 0)
////            return right(node);
//// 
////        // Left Right Case
////        if (balance > 1 && getBalanceFactor(node.getLeft()) < 0)
////        {
////        	node.setLeft(left(node.getLeft()));
////            return right(node);
////        }
//// 
////        // Right Right Case
////        if (balance < -1 && getBalanceFactor(node.getRight()) <= 0)
////            return left(node);
//// 
////        // Right Left Case
////        if (balance < -1 && getBalanceFactor(node.getRight()) > 0)
////        {
////        	node.setRight(right(node.getRight()));
////            return left(node);
////        }
//// 
////        return node;
////	}
//	
//	private BinaryTreeNode<T> left(BinaryTreeNode<T> x) {
//		BinaryTreeNode<T> y = x.getRight();
//		BinaryTreeNode<T> T2 = y.getLeft();
// 
//        
//		y.setLeft(x);
//        x.setRight(T2);
//        
//		return y;
//	}
//
//	private BinaryTreeNode<T> right(BinaryTreeNode<T> y) {
//		BinaryTreeNode<T> x = y.getLeft();
//		BinaryTreeNode<T> T2 = x.getRight();
//	 
//			x.setRight(y);
//	        y.setLeft(T2);
//	        
//	 
//		return x;
//	}
//
//	private BinaryTreeNode<T> minValueNode(BinaryTreeNode<T> node) {
//		BinaryTreeNode<T> current = node;
//		 
//        while (current.getLeft() != null)
//        	current = current.getLeft();
// 
//        return current;
//	}
//
//	@Override
//	public BinaryTreeNode<T> search(BinaryTreeNode<T> keyNode) {
//		
//		return searchRecursive(root,keyNode);
//	}
//	
//	private BinaryTreeNode<T> searchRecursive(BinaryTreeNode<T> node, BinaryTreeNode<T> key){
//		if (key.equals(node))
//	        return node;
//		if(node==null)
//			return null;
//		//key>node
//	    if (key.compareTo(node)>0)
//	       return searchRecursive(node.getRight(), key);
//	 
//	    //key<node
//	    return searchRecursive(node.getLeft(), key);
//		
//	}
//
//	
//	public int getBalanceFactor(BinaryTreeNode<T> node)
//	{
////		if(node==null) return 0;
//		return (getHeight(node.getLeft())-1) - (getHeight(node.getRight())-1);
//	}
//
//	public int getHeight(BinaryTreeNode<T> node) {
//		if(node==null) return 0;
//		
//		int lh=0,rh=0;
//		
//		if(node.getLeft()!=null)
//			lh=getHeight(node.getLeft());
//		
//		if(node.getRight()!=null)
//			lh=getHeight(node.getRight());
//		
//		return Math.max(lh,rh)+1;
//	}
//
//	@Override
//	public String toString() {
//		return "AVLTree [root=" + root + "]";
//	}
//
//	public Map<Integer,List<BinaryTreeNode<T>>> levelOrderTraversal()
//	{
//		if (root != null) {
//			if (root != null) {
//				Map<Integer, List<BinaryTreeNode<T>>> map = new HashMap<>();
//				
//				Queue<BinaryTreeNode<T>> q = new ArrayDeque<>();
//				
//				final BinaryTreeNode<T> NULLNODE = new BinaryTreeNode<>(null);
//				
//				q.add(root);
//				
//				int current = 1,next = 0,level = 0;
//				
//				while (!q.isEmpty()) {
//					BinaryTreeNode<T> removed = q.remove();
//
//					List<BinaryTreeNode<T>> list = map.get(level);
//					if (list == null) {
//						map.put(level, new ArrayList<>());
//					}
//					map.get(level).add(removed);
//					current--;
//					
//					if (removed == NULLNODE)
//						continue;
//					
//					if (removed.getLeft() != null) {
//						q.add(removed.getLeft());
//					} 
//					else {
//						q.add(NULLNODE);
//					}
//					next++;
//
//					if (removed.getRight() != null) {
//						q.add(removed.getRight());
//					}
//					else {
//						q.add(NULLNODE);
//					}
//					next++;
//
//					if (current == 0) {
//						current = next;
//						next = 0;
//						level++;
//					}
//
//				}
//				map.forEach((k,v) -> System.out.println(k+ "<>"+v));
//				return map;
//			}
//			return null;
//		}
//		return null;
//	}
//	
//	
//}
////package implementations;
////
////import java.util.ArrayDeque;
////import java.util.ArrayList;
////import java.util.HashMap;
////import java.util.List;
////import java.util.Map;
////import java.util.Queue;
////import java.util.Stack;
////
////import interfaces.AVLTreeIface;
////import models.BinaryTreeNode;
////
////public class AVLTree<T extends Comparable<? super T>> implements AVLTreeIface<T> {
////
////	private BinaryTreeNode<T> root;
////	private int size;
////	
////	public AVLTree() {
////		this.root=null;
////		this.size=0;
////	}
////	
////	public BinaryTreeNode<T> getRoot() {
////		return root;
////	}
////
////	
//////	public void setRoot(BinaryTreeNode<T> root) {
//////		this.root = root;
//////	}
////
////	public int getSize() {
////		return size;
////	}
////
////	public void setSize(int size) {
////		this.size = size;
////	}
////
////	@Override
////	public void add(BinaryTreeNode<T> keyNode) {
////		if(root==null)
////		{
////			root=keyNode;
////			size++;
////			return;
////		}
////		BinaryTreeNode<T> cur=root;
////		Stack<BinaryTreeNode<T>> stack= new Stack<>();
////		while(cur!=null)
////		{
////			stack.push(cur);
////			if(keyNode.compareTo(cur)<=0)
////				cur=cur.getLeft();
////			else
////				cur=cur.getRight();
////		}
////		if(keyNode.compareTo(stack.peek())<=0)
////			stack.peek().setLeft(keyNode);
////		else
////			stack.peek().setRight(keyNode);
////			
////		size++;
////		while(!stack.isEmpty())
////		{
////			int balanceFactor = getBalanceFactor(stack.peek());
////			if(balanceFactor>1 || balanceFactor<-1) break;
////			stack.pop();
////		}
////		if(stack.isEmpty()) return;
////		BinaryTreeNode<T> g=stack.pop();
////		BinaryTreeNode<T> p=null;
////		BinaryTreeNode<T> x=null;
////		BinaryTreeNode<T> gparent=null;
////		if(!stack.isEmpty())
////			gparent=stack.pop();
////		
////		StringBuilder imbalanceType=new StringBuilder("");
////		
////		if(keyNode.compareTo(g)<=0)
////		{
////			p=g.getLeft();
////			imbalanceType.append("L");
////		}
////		else 
////		{
////			p=g.getRight();
////			imbalanceType.append("R");
////		}
////			
////		if(keyNode.compareTo(p)<=0)
////		{
////			x=p.getLeft();
////			imbalanceType.append("L");
////		}
////		else
////		{
////			x=p.getRight();
////			imbalanceType.append("R");
////		}
////		
////		switch(imbalanceType.toString())
////		{
////			case "LL":
////				rightRotate(gparent,g,p);
////				break;
////			
////			case "LR":
////				leftRotate(g,p,x);
////				rightRotate(gparent,g,x);
////				break;
////				
////			case "RL":
////				rightRotate(g,p,x);
////				leftRotate(gparent,g,x);
////				break;
////			
////			case "RR":
////				leftRotate(gparent,g,p);
////				break;
////		}
////		
////	}
////	
////
////	public void leftRotate(BinaryTreeNode<T> gparent, BinaryTreeNode<T> g, BinaryTreeNode<T> p) {
////		g.setRight(p.getLeft());
////		p.setLeft(g);
////		if(gparent==null)
////			root=p;
////		else
////		{
////			if(g.compareTo(gparent)<=0)
////				gparent.setLeft(p);
////			else
////				gparent.setRight(p);
////		}
////
////		
////	}
////
////	public void rightRotate(BinaryTreeNode<T> gparent, BinaryTreeNode<T> g, BinaryTreeNode<T> p) {
////	
////		g.setLeft(p.getRight());
////		p.setRight(g);
////		if(gparent==null)
////			root=p;
////		else
////		{
////			if(g.compareTo(gparent)<=0)
////				gparent.setLeft(p);
////			else
////				gparent.setRight(p);
////		}
////
////	}
////
////	@Override
////	public BinaryTreeNode<T> delete(BinaryTreeNode<T> keyNode) {
////		
////
////		
////		if(keyNode==null || root==null) return null;
////		
////		Stack<BinaryTreeNode<T>> stack= new Stack<>();
////		BinaryTreeNode<T> current=root;
////		while(current!=null)
////		{
////			if(keyNode.equals(current)) break;
////			stack.push(current);
////			if(keyNode.compareTo(current)<=0)
////				current=current.getLeft();
////			else
////				current=current.getRight();
////				
////		}
////		
////		if(current==null) return null;
////		BinaryTreeNode<T> retVal=current;
////		size--;
////		if(current.getLeft()==null && current.getRight()==null)
////		{
////			if(current==root)
////			{
//////				retVal=current;
////				root=null;
//////				return retVal;
////			}
////			else
////			{
//////				retVal=current;
////				if(current.compareTo(stack.peek())<=0)
////					stack.peek().setLeft(null);
////				else
////					stack.peek().setRight(null);
////			}
////			
//////			while(!stack.isEmpty())
//////			{
//////				int bf=getBalanceFactor(stack.peek());
//////				if(bf>1 || bf<-1) break;
//////				
//////				stack.pop();			
//////			}
//////			if(stack.isEmpty()) return retVal;
////			
////			
////		}
////		else
////		{
////			BinaryTreeNode<T> successor=current.getRight();
////			BinaryTreeNode<T> successorParent=null;
////			while(successor!=null)
////			{
////				if(successor.getLeft()==null) break;
////				successorParent=successor;
////				successor=successor.getLeft();
////			}
////			
////			if(successor==null)
////			{
////				if(current==root)
////				{
////					root=root.getLeft();
////				}
////				else
////				{
////					if(current.compareTo(stack.peek())<=0)
////						stack.peek().setLeft(current.getLeft());
////					else
////						stack.peek().setRight(current.getLeft());
////				}
////			}
////			else
////			{
////				swapDataOfNodes(current,successor);
////				if(successorParent==null)
////				{
////					current.setRight(successor.getRight());
////				}
////				else
////				{
////					successorParent.setLeft(successor.getRight());
////				}
////				
////			}
////		}
////	
////		
////		
////		
////		
////		
////		return retVal;
//////		return deleteRecursive(root, keyNode);
////		
////	
////		
////		
////		
//////		return deleteRecursive(root, keyNode);
////		
////	}
////	
////	
////private void swapDataOfNodes(BinaryTreeNode<T> current, BinaryTreeNode<T> successor) {
////	T temp=current.getData();
////	current.setData(successor.getData());
////	successor.setData(temp);
////}
////
//////	private BinaryTreeNode<T> deleteRecursive(BinaryTreeNode<T> node,BinaryTreeNode<T> keyNode) { 
//////		
//////		if(node==null)
//////			return node;
//////		
//////		//key<node.data
//////		if(keyNode.compareTo(node)<0) {
//////			node.setLeft(deleteRecursive(node.getLeft(),keyNode));
//////		}
//////		//key>node.data
//////		else if(keyNode.compareTo(node)>0) {
//////			node.setRight(deleteRecursive(node.getRight(),keyNode));
//////		}
//////		//key==node.data
//////		else {
//////			if ((node.getLeft() == null) || (node.getRight() == null))
//////            {
//////				BinaryTreeNode<T> temp = null;
//////                if (temp == node.getLeft())
//////                    temp = node.getRight();
//////                else
//////                    temp = node.getRight();
////// 
//////                
//////                if (temp == null)
//////                {
//////                    temp = node;
//////                    node = null;
//////                }
//////                else 
//////                    node = temp; 
//////            }else
//////            {           
//////            	BinaryTreeNode<T> temp = minValueNode(node.getRight());
////// 
//////               node.setData(temp.getData());
//////               node.setRight(deleteRecursive(node.getRight(), temp));
//////            }
//////		}
//////		
//////		if (node == null)
//////            return node;
////// 
//////       
//////        int balance = getBalanceFactor(node);
////// 
//////        
//////        // Left Left Case
//////        if (balance > 1 && getBalanceFactor(node.getLeft()) >= 0)
//////            return right(node);
////// 
//////        // Left Right Case
//////        if (balance > 1 && getBalanceFactor(node.getLeft()) < 0)
//////        {
//////        	node.setLeft(left(node.getLeft()));
//////            return right(node);
//////        }
////// 
//////        // Right Right Case
//////        if (balance < -1 && getBalanceFactor(node.getRight()) <= 0)
//////            return left(node);
////// 
//////        // Right Left Case
//////        if (balance < -1 && getBalanceFactor(node.getRight()) > 0)
//////        {
//////        	node.setRight(right(node.getRight()));
//////            return left(node);
//////        }
////// 
//////        return node;
//////	}
////	
////	private BinaryTreeNode<T> left(BinaryTreeNode<T> x) {
////		BinaryTreeNode<T> y = x.getRight();
////		BinaryTreeNode<T> T2 = y.getLeft();
//// 
////        
////		y.setLeft(x);
////        x.setRight(T2);
////        
////		return y;
////	}
////
////	private BinaryTreeNode<T> right(BinaryTreeNode<T> y) {
////		BinaryTreeNode<T> x = y.getLeft();
////		BinaryTreeNode<T> T2 = x.getRight();
////	 
////			x.setRight(y);
////	        y.setLeft(T2);
////	        
////	 
////		return x;
////	}
////
////	private BinaryTreeNode<T> minValueNode(BinaryTreeNode<T> node) {
////		BinaryTreeNode<T> current = node;
////		 
////        while (current.getLeft() != null)
////        	current = current.getLeft();
//// 
////        return current;
////	}
////
////	@Override
////	public BinaryTreeNode<T> search(BinaryTreeNode<T> keyNode) {
////		
////		return searchRecursive(root,keyNode);
////	}
////	
////	private BinaryTreeNode<T> searchRecursive(BinaryTreeNode<T> node, BinaryTreeNode<T> key){
////		if (key.equals(node))
////	        return node;
////		if(node==null)
////			return null;
////		//key>node
////	    if (key.compareTo(node)>0)
////	       return searchRecursive(node.getRight(), key);
////	 
////	    //key<node
////	    return searchRecursive(node.getLeft(), key);
////		
////	}
////
////	
////	public int getBalanceFactor(BinaryTreeNode<T> node)
////	{
////		return (getHeight(node.getLeft())-1) - (getHeight(node.getRight())-1);
////	}
////
////	public int getHeight(BinaryTreeNode<T> node) {
////		if(node==null) return 0;
////		
////		int lh=0,rh=0;
////		
////		if(node.getLeft()!=null)
////			lh=getHeight(node.getLeft());
////		
////		if(node.getRight()!=null)
////			rh=getHeight(node.getRight());
////		
////		return Math.max(lh,rh)+1;
////	}
////
////	@Override
////	public String toString() {
////		return "AVLTree [root=" + root + "]";
////	}
////
////	public Map<Integer,List<BinaryTreeNode<T>>> levelOrderTraversal()
////	{
////		if (root != null) {
////			Map<Integer, List<BinaryTreeNode<T>>> map = new HashMap<>();
////			
////			Queue<BinaryTreeNode<T>> q = new ArrayDeque<>();
////			
////			final BinaryTreeNode<T> NULLNODE = new BinaryTreeNode<>(null);
////			
////			q.add(root);
////			
////			int current = 1,next = 0,level = 0;
////			
////			while (!q.isEmpty()) {
////				BinaryTreeNode<T> removed = q.remove();
////
////				List<BinaryTreeNode<T>> list = map.get(level);
////				if (list == null) {
////					map.put(level, new ArrayList<>());
////				}
////				map.get(level).add(removed);
////				current--;
////				
////				if (removed == NULLNODE)
////					continue;
////				
////				if (removed.getLeft() != null) {
////					q.add(removed.getLeft());
////				} 
////				else {
////					q.add(NULLNODE);
////				}
////				next++;
////
////				if (removed.getRight() != null) {
////					q.add(removed.getRight());
////				}
////				else {
////					q.add(NULLNODE);
////				}
////				next++;
////
////				if (current == 0) {
////					current = next;
////					next = 0;
////					level++;
////				}
////
////			}
////			return map;
////		}
////		return null;
////	}
////	
////	
////}