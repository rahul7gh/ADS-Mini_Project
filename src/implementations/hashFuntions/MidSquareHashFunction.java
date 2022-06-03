package implementations.hashFuntions;

import interfaces.HashFunctionIface;

public class MidSquareHashFunction <T extends Comparable<? super T>> implements HashFunctionIface<T>  {

	@Override
	public int getHash(T key,int size) {
		int hashCode=key.hashCode();
		Integer sq= hashCode*hashCode;
		
		String seedStr=sq.toString();
		int mid=seedStr.length()/2;
		int end=-1;
		Integer newSeed=null;
		if(mid+4>=seedStr.length())
		{
			newSeed= Integer.parseInt(seedStr.substring(seedStr.length()/2));
		}
		else
		{
			newSeed= Integer.parseInt(seedStr.substring(seedStr.length()/2,(seedStr.length()/2)+4 ));
		}
		
		
		newSeed=newSeed*newSeed;
		seedStr=newSeed.toString();
		
		if(mid+4>=seedStr.length())
		{
			newSeed= Integer.parseInt(seedStr.substring(seedStr.length()/2));
		}
		else
		{
			newSeed= Integer.parseInt(seedStr.substring(seedStr.length()/2,(seedStr.length()/2)+4 ));
		}
		
		return  newSeed% size;
	}

}
