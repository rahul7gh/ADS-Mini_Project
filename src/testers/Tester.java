package testers;

import java.util.Random;

import implementations.AVLTree;
import implementations.HashTable;
import implementations.hashFuntions.ModNHashFunction;
import models.BinaryTreeNode;

public class Tester {

	public static void main(String[] args) {
		
		AVLTree<Integer> avl= new AVLTree<>();
//		avl.add(new BinaryTreeNode<Integer>(79));
//		avl.add(new BinaryTreeNode<Integer>(59));
//		System.out.println(avl.levelOrderTraversal());
//		
////		System.out.println(avl.search(new BinaryTreeNode<Integer>(10)));
//		avl.delete(new BinaryTreeNode<Integer>(79));
//		System.out.println(avl.levelOrderTraversal());
////		System.out.println(avl.search(new BinaryTreeNode<Integer>(10)));
////		System.out.println(avl.search(new BinaryTreeNode<Integer>(20)));
////		
//		System.exit(0);
		HashTable<Integer> intHash=new HashTable<>(new ModNHashFunction<Integer>());
		Random r = new Random();
		int size=10;
		int[] arr= new int[size];
//		arr=new int[] {86,241,194,785,597,848,424,704,689,1004};
		arr=new int[] {604,927,100,590,922,382,428,290,494,243};
		
		
		//,29,30,98,49,59};
//		,102,114,123,131,140,159,166,172,185,197,200,217,226,238,241,258,261,275,281,294,304,317,324,337,348,358,368,371,383,393};
		for (int i = 0; i < arr.length; i++) {
			arr[i]=i*10+r.nextInt(10*100);
			System.out.print(arr[i]+",");
//			intHash.add(arr[i]);	
			avl.add(new BinaryTreeNode<Integer>(arr[i]));
		}
		System.out.println();
		avl.levelOrderTraversal();
		
		for (int i = 0; i < arr.length; i++) {
			System.out.println(avl.search(new BinaryTreeNode<Integer>(arr[i])));
			avl.delete(new BinaryTreeNode<Integer>(arr[i]));
			System.out.println(avl.search(new BinaryTreeNode<Integer>(arr[i])));
		
			//			System.out.println(avl.getSize()+"  "+arr[i]);
//			System.out.println(intHash.search(arr[i]));
//			intHash.delete(arr[i]);
//			System.out.println(intHash.search(arr[i]));
//			System.out.println(intHash.getSize());
//			System.out.println(avl.levelOrderTraversal());
		}
		avl.heightAndBalanceFactorOfEveryNode(avl.getRoot());
		
//		for (int i = 0; i < 10; i++) {
//		
//			avl.add(new BinaryTreeNode<Integer>(i*10+r.nextInt(10*100)));
//		}
//		avl.heightAndBalanceFactorOfEveryNode(avl.getRoot());
//		avl.levelOrderTraversal();
		
//		avl.heightAndBalanceFactorOfEveryNode(avl.getRoot());
//		System.out.println(avl.levelOrderTraversal());
//		intHash.debug();
//		for (int i = 0; i < arr.length; i++) {
//			System.out.println("<-->");
//			System.out.println(intHash.search(arr[i]));
//			intHash.delete(arr[i]);
//			System.out.println(intHash.search(arr[i]));
//			System.out.println("<-->");
//		}
//		intHash.debug();
//		intHash.add(40);
		
//		HashTable<String> strHash=new HashTable<>(new ModNHashFunction<String>());
//		
//		HashTable<Double> dobHash=new HashTable<>(new ModNHashFunction<Double>());
//		intHash.debug();
//		System.out.println(intHash.toString());
//		System.out.println(intHash.search(29));
//		intHash.delete(29);
//		System.out.println(intHash.search(29));
		
//		System.out.println(intHash.search(79));
//		intHash.delete(79);
//		System.out.println(intHash.search(79));
//		System.out.println("hshshsh");
//		intHash.debug();
//		System.out.println(intHash.search(79));
//		intHash.delete(79);
//		System.out.println(intHash.search(79));

//		System.out.println(intHash.search(6200));
//		intHash.delete(6200);
//		System.out.println(intHash.search(6200));
//		strHash.toString();
		
		
//		AVLTree<Integer> avt= new AVLTree<>();
//		Random r= new Random();
//		int size=50;
//		int [] arr= new int[size];
////		arr=new int[] {130,187,195,168,19,197,97,70,244,139,14,182,49,188,74,220,213,147,163,139,26,241,115,220,23,56,222,137,220,181,108,175,85,83,118,0,188,238,74,126,170,53,150,70,92,229,25,210,88,179};
//		for(int i=0;i<size;i++)
//			arr[i]=r.nextInt(5*size);
////			arr[i]=i;
//		for(int i=0;i<size;i++)
//		{
//			System.out.print(arr[i]+",");
//			avt.add(new BinaryTreeNode<Integer>(arr[i]));
//		}
//		System.out.println();
//		Map<Integer, List<BinaryTreeNode<Integer>>> map = avt.levelOrderTraversal();
//		
//		map.entrySet().stream().forEach(
//				entry-> entry.getValue().stream().filter(node-> node.getData()!=null)
//				.forEach(node-> System.out.println(entry.getKey()+"<>"+node+"<>"+avt.getBalanceFactor(node)))
//				);
		
		
//		map.values().stream().forEach(
//				
//				list-> list.stream().filter(v -> v.getData()!=null)
//											.forEach(node -> 
//											System.out
//											.println(node+"<>"+(avt.getBalanceFactor(node))) )
//				
//				);
	}
}
