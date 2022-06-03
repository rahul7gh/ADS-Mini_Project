package testers;

import java.util.Random;

import implementations.HashTable;
import implementations.hashFuntions.ModNHashFunction;

public class Tester {

	public static void main(String[] args) {
		HashTable<Integer> intHash=new HashTable<>(new ModNHashFunction<Integer>());
		Random r = new Random();
		int size=40;
		for (int i = 1; i <= size; i++) {
			
			intHash.add(i*10+r.nextInt(10));
			
				
		}
		intHash.add(40);
		
//		HashTable<String> strHash=new HashTable<>(new ModNHashFunction<String>());
//		
//		HashTable<Double> dobHash=new HashTable<>(new ModNHashFunction<Double>());
		intHash.debug();
//		System.out.println(intHash.toString());
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
