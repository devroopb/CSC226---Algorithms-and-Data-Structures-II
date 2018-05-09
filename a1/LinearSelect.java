/*
  Devroop Banerjee
  V00837869
  CSC 226
  Assignment 1 - LinearSelect.java
*/


import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;
import java.util.Random;

public class LinearSelect{
	//Function to invoke LinearSelect
	public static int LS(int[] S, int k){
        if (S.length==1){
				return S[0];
		}
        int result[]= new int[k];
        for(int i=0; i<result.length; i++){
        	result[i]=0;
        }
        return LinearSelect(0,S.length-1,S,k);
	}

	//do LinearSelect in a recursive way 
    private static int LinearSelect(int left,int right, int[] array, int k){
    	//if there is only one element now, just record.
    	if (left>=right){
    		return array[left];
    	}

		int p=pickCleverPivot(left,right,array);
    	int eIndex=partition(left,right,array,p);
    	//after the partition, do following ops
    	if (k<=eIndex){
    		return LinearSelect(left,eIndex-1,array,k);
    	}else if(k==eIndex+1){
    		return array[eIndex];
    	}else{
    		return LinearSelect(eIndex+1,right,array,k);
    	}
	}	

    //do Partition with a pivot
	private static int partition(int left, int right, int[] array, int pIndex){
    	//move pivot to last index of the array
    	swap(array,pIndex,right);

    	int p=array[right];
    	int l=left;
    	int r=right-1;
  
    	while(l<=r){
    		while(l<=r && array[l]<=p){
    			l++;
    		}
    		while(l<=r && array[r]>=p){
    			r--;
    		}
    		if (l<r){
    			swap(array,l,r);
    		}
    	}

        swap(array,l,right);
    	return l;
    }

    //Pick a pivot using median of medians algorithm
    private static int pickCleverPivot(int left, int right, int[] array){
		int n = right-left+1;
    	if(n <= 5){
			return partition(left, right, array, 5);
		}else{
			for(int i = left; i <= right; i=i+5){
				int r = i+4;
				if(r > right){
					r = right;
				}
				int median = partition(i, r, array, 5);
				swap(array, median, left+(int)Math.floor(i-left)/5);
			}
			return pickCleverPivot(left, left + (int)Math.ceil(n/5)-1, array);
		}
	}

	//swap two elements in the array
	private static void swap(int[]array, int a, int b){
 		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}

	/* main()
	   Contains code to test the LinearSelect. Nothing in this function
	   will be marked. You are free to change the provided code to test your
	   implementation, but only the contents of the LinearSelect class above
	   will be considered during marking.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);
		
		int k = inputVector.get(0);

		int[] array = new int[inputVector.size()-1];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i+1);


		System.out.printf("Read %d values.\n",array.length);


		long startTime = System.nanoTime();

		int kthsmallest = LS(array,k);

		long endTime = System.nanoTime();

		long totalTime = (endTime-startTime);

		System.out.printf("The %d-th smallest element in the input list of size %d is %d.\n",k,array.length,kthsmallest);
		System.out.printf("Total Time (nanoseconds): %d\n",totalTime);
	}
}