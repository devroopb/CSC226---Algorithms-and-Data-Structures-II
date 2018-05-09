/*
Devroop Banerjee
V00837868
CSC 226
Assignment 3
*/

/* MWST.java
   CSC 226 - Fall 2016
   Assignment 3 - Minimum Weight Spanning Tree Template
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java MWST
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java MWST file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 08/02/2014
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;

//Do not change the name of the MWST class
public class MWST{

	/* mwst(G)
		Given an adjacency matrix for graph G, return the total weight
		of all edges in a minimum weight spanning tree.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static int MWST(int[][] G){
		int numVerts = G.length;

		/* Find a minimum weight spanning tree by any method */
		/* (You may add extra functions if necessary) */
		
		/* ... Your code here ... */

		if(numVerts == 0){																	//checks if there actually is an adjacency matrix
			return 0;
		}

		int[] daddy = new int[numVerts];													//parent array used to extract amd hold minimum edge weights
		int numEdges, megaLoop, minimum, x, y, z, source, destination, weight, temp, fatherForgiveMeForIHaveSinned, track;
		x = y = z = source = destination = weight = track = fatherForgiveMeForIHaveSinned = 0;
		numEdges = numVerts - 1;
		track++;

		for(megaLoop = 1; megaLoop <= numEdges; megaLoop++){								//outermost loop to go through every value and check for various conditions later on in the program
			minimum = (226*226*226);														//could be any number but since it's CSC 226 and assignment 3...
			for(x = 0; x < numVerts; x++){													//following loops do the main extractions
				for(y = x + 1; y < numVerts; y++){
					if((G[x][y] <= minimum) && (G[x][y] > 0)){
						if(((daddy[x] == 0) && (daddy[y] == 0)) || (daddy[x] != daddy[y])){ //saves the values for further fine tuning
							source = x;
							destination = y;
							minimum = G[x][y];
						}
						
						if((daddy[source] == 0) && (daddy[destination] == 0)){
							fatherForgiveMeForIHaveSinned = 3;
						}else{
							if(daddy[source] == 0){
								fatherForgiveMeForIHaveSinned = 1;
							}
							if(daddy[destination] == 0){
								fatherForgiveMeForIHaveSinned = 2;
							}
						}
						if((daddy[source] > 0) && (daddy[destination] > 0)){
							fatherForgiveMeForIHaveSinned = 4;
						}
					}
				}
			}

			/*Refines parent array for further calculations according to various cases*/
			
			switch(fatherForgiveMeForIHaveSinned){
				case 1:
					daddy[source] = daddy[destination];
					break;

				case 2:
					daddy[destination] = daddy[source];
					break;

				case 3:
					daddy[source] = track;
					daddy[destination] = track;
					track++;
					break;

				case 4:
					temp = daddy[destination];
					for(z = 0; z < daddy.length; z++){
						if(daddy[z] == temp){
							daddy[z] = daddy[source];
						}
					}
					break;
			}

		weight += minimum;
		}
		
		/* Add the weight of each edge in the minimum weight spanning tree
		   to totalWeight, which will store the total weight of the tree.
		*/
		int totalWeight = 0;
		totalWeight = weight;
		return totalWeight;
	}
	
		
	/* main()
	   Contains code to test the MWST function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
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
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			
			int totalWeight = MWST(G);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			System.out.printf("Graph %d: Total weight is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}
