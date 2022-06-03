package implementations.bucketEvaluators;

public class Log2BucketEvaluator {
	
	public boolean isBucketEfficient(int numberOfKeys,int size)
	{
		
		if(numberOfKeys< Math.ceil((Math.log(size)/Math.log(2))))
			return true;
		return false;
	}
	
	
}
