package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            //taking input
            int a = 0;
            File file = new File("G:\\L-4 T-1\\AI sessional\\Offline1\\src\\input.txt");
            int gameboard[][] = new int[100][100];
            Scanner sc = new Scanner(System.in);
            System.out.println("Heuristic 1 is non admissible and 2 is admissible.Enter 1 or 2 to select");
            int heuristic = sc.nextInt();
            sc.close();
            sc = null;
            try {
                sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    if (sc.hasNextInt()) a = sc.nextInt();
                    //System.out.println("printing a "+a);
                    if (a == 0) {
                        sc.close();
                        break;
                    }
                    //gameboard = new int[a+1][a+1];
                    for (int i = 1; i <= a; i++) {
                        for (int j = 1; j <= a; j++) {
                            if (sc.hasNextInt()) gameboard[i][j] = sc.nextInt();
                        }
                    }
                    //calling a* search
                    aStarSearch aStSrc = new aStarSearch(a, heuristic); //initialize astar
                    node resultNode = aStSrc.aStarSearchAlgo(gameboard); //starting astar algorithm

                    //output the result
                    ArrayList<node> resultNodes = new ArrayList<node>();//arraylist to sequentially store state boards
                    aStSrc.getResult(resultNode, resultNodes); //get arraylist ending in result node

                    System.out.println("Number Of moves : " + aStSrc.numberOfMoves);
                    for (node n : resultNodes)
                        if (n.parent != null) System.out.println("Color : " + n.color);//output colors
                    int bn = 1;
                    for (node n : resultNodes) {//output state boards
                        System.out.println("Board No : " + bn);
                        bn++;
                        for (int i = 1; i <= a; i++) {
                            for (int j = 1; j <= a; j++) {
                                System.out.print(n.gameBoard[i][j] + " ");
                            }
                            System.out.println("");
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
