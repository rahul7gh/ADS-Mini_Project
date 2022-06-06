package testers;

import java.util.Random;

import implementations.AVLTree;
import implementations.HashTable;
import implementations.hashFuntions.ModNHashFunction;
import models.BinaryTreeNode;

public class Tester {

	public static void main(String[] args) {
		HashTable<Integer> intHash=new HashTable<>(new ModNHashFunction<Integer>());
		Random r = new Random();
		int size=10;
		int[] arr= new int[size];

//		arr=new int[] {604,927,100,590,922,382,428,290,494,243};
		for (int i = 0; i < arr.length; i++) {
			arr[i]=i*10+r.nextInt(10*100);
			System.out.print(arr[i]+",");
			intHash.add(arr[i]);	

		}
		System.out.println();
		for (int i = 0; i < arr.length; i++) {
		
			System.out.println(intHash.search(arr[i]));
			intHash.delete(arr[i]);
			System.out.println(intHash.search(arr[i]));
		}
		
		for (int i = 0; i < arr.length; i++) {
			arr[i]=i*10+r.nextInt(10*100);
			System.out.print(arr[i]+",");
			intHash.add(arr[i]);	

		}
		intHash.debug();
	}
}
