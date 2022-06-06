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
     
     
    BinaryTreeNode<T> rotateRight(BinaryTreeNode<T> g)
    {
        if (g == null) return g;
         
        BinaryTreeNode<T> p  = g.getLeft();
         
        BinaryTreeNode<T> pRight  = p.getRight();
         
        p.setRight(g);
        g.setLeft(pRight);
         
        
        updateHeight(g);
         
        
        updateHeight(p);
         
        return p;
    }
     
     
    BinaryTreeNode<T> rotateLeft(BinaryTreeNode<T> g)
    {
        if (g == null) return g;
         
        BinaryTreeNode<T> p  = g.getRight();
        BinaryTreeNode<T> pLeft  = p.getLeft();
         
        p.setLeft(g);
        g.setRight(pLeft);
         
       
        updateHeight(g);
         
   
        updateHeight(p);
         
        return p;
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
        
        if (node == null) return null;
         

        if (node.getLeft() == null) return node.getData();
         
        return getMinValue(node.getLeft());
    }
     
    BinaryTreeNode<T> reAdjust(BinaryTreeNode<T> g)
    {
    	updateHeight(g);
    	int bf= getBalance(g);
    	
    	BinaryTreeNode<T> p=null,x=null;
    	
    	if(bf>1) // L imbalance
    	{
    		p=g.getLeft();
    		
    		if(getBalance(p)>=0)
    		{
    			g=rotateRight(g);
    		}
    		else
    		{
    			g=rotateRight(rotateLeft(p));
    		}
    	}
    	else if(bf<-1)
    	{
    		p=g.getRight();
    		
    		if(getBalance(p)<=0)
    		{
    			g=rotateLeft(g);
    		}
    		else
    		{
    			g=rotateLeft(rotateRight(p));
    		}
    	}
    	
    	return g;
    }
    public BinaryTreeNode<T> myDelete(BinaryTreeNode<T> current,BinaryTreeNode<T> keyNode)
    {
    	if(current==null|| keyNode==null) return null;
    	
    	if(keyNode.compareTo(current)<0)
    	{
    		current.setLeft(myDelete(current.getLeft(),keyNode ));
    		current=reAdjust(current);
    		return current;
    	}
    	
    	else if(keyNode.compareTo(current)>0)
    	{
    		current.setRight(myDelete(current.getRight(),keyNode ));
    		current=reAdjust(current);
    		return current;
    	}
    	
    	if(current.getLeft()==null && current.getRight()==null)
    	{
    		return null;
    	}
    	else
    	{
    		if(current.getRight()==null)
    		{
    			return current.getLeft();
    		}
    		else
    		{
    			BinaryTreeNode<T> succ= current.getRight();
    			BinaryTreeNode<T> succP=null;
    			while(succ!=null)
    			{
    				if(succ.getLeft()==null) break;
    				succP=succ;
    				succ=succ.getLeft();
    			}
    			current.setData(succ.getData());
    			current.setRight( myDelete(current.getRight(), succ));
    			updateHeight(current);
    			return current;
    		}
    	}
    		
//    	return current;
    }
     
    private BinaryTreeNode<T> delete(BinaryTreeNode<T> node, BinaryTreeNode<T> keyNode)
    {
        
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
         
        if (balance > 1) 
        {
            if (getBalance(node.getLeft()) >= 0) 
            {
                node = rotateRight(node);
            }
            else 
            {
                node.setLeft(rotateLeft(node.getRight()));
                node = rotateRight(node);
            }
        }
         
        else if (balance < -1) 
        {
            if (getBalance(node.getRight()) <= 0) 
            {
                node = rotateLeft(node);
            }
            else 
            {
                node.setRight(rotateRight(node.getRight()));
                node = rotateLeft(node);
            }
        }
        return node;
    }
     
     
    private BinaryTreeNode<T> insert(BinaryTreeNode<T> node, BinaryTreeNode<T> keyNode)
    {
       
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
         
        
        int balance = getBalance(node);
         
        if (balance > 1) 
        {
            if (keyNode.compareTo(node.getLeft())<0) 
            {
                node = rotateRight(node);
            }
            else 
            {
                node.setLeft(rotateLeft(node.getLeft()));
                node = rotateRight(node);
            }
        }
         
        else if (balance < -1) 
        {
            if (keyNode.compareTo(node.getRight())>0) 
            {
                node = rotateLeft(node);
            }
            else 
            {
                node.setRight( rotateRight(node.getRight())); 
                node = rotateLeft(node);
            }
        }
        return node;
    }
     
     
//    public void insert(T key)
//    {
//        root = insert(this.root, new BinaryTreeNode<T>(key));
//        return;
//    }
//     
//     
//    public void delete(T key)
//    {
//        root = delete(this.root, new BinaryTreeNode<T>(key));
//        return;
//    }
     
     
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
		
		if(node==null || key==null)
			return null;
		
		if (key.equals(node))
	        return node;
		
		
	    if (key.compareTo(node)>0)
	       return searchRecursive(node.getRight(), key);
	 
	   
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
