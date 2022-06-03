package testers;

import implementations.HashTable;
import implementations.hashFuntions.MidSquareHashFunction;
import implementations.hashFuntions.ModNHashFunction;

public class Tester {

	public static void main(String[] args) {
		HashTable<Integer> intHash=new HashTable<>(new ModNHashFunction<Integer>());
		
		int size=20;
		for (int i = 0; i < size; i++) {
			
			intHash.add(i*10+i);
			if(i==6)
			{
				System.out.println("YOHOHO!");
			}
				
		}
		
		
//		HashTable<String> strHash=new HashTable<>(new ModNHashFunction<String>());
//		
//		HashTable<Double> dobHash=new HashTable<>(new ModNHashFunction<Double>());
		intHash.debug();
		System.out.println(intHash.toString());
//		strHash.toString();
		
		
//		AVLTree<Integer> avt= new AVLTree<>();
//		Random r= new Random();
//		int size=50;
//		int [] arr= new int[size];
////		arr=new int[] {5,2,18,14,22};
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
//		
//		
////		map.values().stream().forEach(
////				
////				list-> list.stream().filter(v -> v.getData()!=null)
////											.forEach(node -> 
////											System.out
////											.println(node+"<>"+(avt.getBalanceFactor(node))) )
////				
////				);
	}
}
