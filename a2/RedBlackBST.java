/*
Devroop Banerjee
V00837868
CSC 226
Assignment 2 - RedBlackBST.java

RedBlackBST code taken from "Algorithms 4th Edition" 
texbook by Sedgewick
Modified by Devroop Banerjee
*/

import java.util.Scanner;
import java.io.File;

public class RedBlackBST{
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	double r;

	private Node root;

	private class Node{
		int key; 								//input values in file
		Node left, right; 						// subtrees
		int N; 									// # nodes in this subtree
		boolean color; 							// color of link from

		Node(int key, int N, boolean color){
			this.key = key;
			this.N = N;
			this.color = color;
		}
	}

	public RedBlackBST(){
	}

	private boolean isRed(Node x){
		if (x == null){
			return false;
		}
	return x.color == RED;
	}
	
	private Node rotateLeft(Node h){
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		return x;
	}

	private Node rotateRight(Node h){
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		return x;
	}

	private void flipColors(Node h){
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}

	private int size(Node h){
		if (h == null){
			return 0;
		}else{
			return h.N;
		}
	}

	public void put(int key){
		root = put(root, key);
		root.color = BLACK;
	}

	private Node put(Node h, int key){
		if (h == null){
			r++;
			return new Node(key, 1, RED);
		}

		if (key < h.key){
			h.left = put(h.left, key);
		}else if (key > h.key){
			h.right = put(h.right, key);
		}else{
			h.key = key;
		}
			
		if(isRed(h.right) && !isRed(h.left)){
			h = rotateLeft(h);
		}

		if(isRed(h.left) && isRed(h.left.left)){
			h = rotateRight(h);
		}

		if(isRed(h.left) && isRed(h.right)){
			r--;
			flipColors(h);
		}

		h.N = size(h.left) + size(h.right) + 1;
		return h;
	}

	public void percentRed(){
		double percent = ((r/(double)size(root))*100);
		System.out.printf("%.2f", percent);
		System.out.print("% of the nodes are red");
	}

	public static void main(String[]args){
		RedBlackBST st = new RedBlackBST();

		Scanner s;
		if(args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			}catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}

		int v = 0;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0){
			st.put(v);
		}

		st.percentRed();
	}
}