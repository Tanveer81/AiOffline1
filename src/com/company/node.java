package com.company;

import java.util.ArrayList;

/**
 * Created by Hp on 10/5/2017.
 */
public class node {
    public int gameBoard[][];
    public int graph[][];
    public int g,f,n,color,a,heuristic;
    public node parent;

    public node(int[][] gameBoard , int n , int heuristic) { //constructor
        //System.out.println("costructor in node");
        this.gameBoard = gameBoard;
        a= n*n + 1;
        graph = new int[a][a];
        this.n = n;
        this.heuristic = heuristic;
        this.makeGraph();
    }

    public boolean checkIfGoal(){
        /*checks if the current node is the goal node,
            if not returns false*/
        int a = gameBoard[1][1];
        //System.out.println("printing current region color " + a);
        for( int i = 1 ; i <= n ; i++ ) {
            for (int j = 1; j <= n; j++) {
                if (gameBoard[i][j] != a) {
                    //System.out.println("not goal found");
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<node> getChildren() {
        /*returns children nodes of current node,
            all the possible moves of the game*/
       // System.out.println("in getchildren");
        ArrayList<node> children = new ArrayList<node>();
        boolean foundColor[] = new boolean[7];

        for( int i = 1 ; i <= n ; i++ ) {
            for (int j = 1; j <= n; j++) {
                foundColor[gameBoard[i][j]] = true;
            }
        }
       // System.out.println("a");
        for(int i = 1 ; i <= 6 ; i++){
            if(!foundColor[i])continue;
            if(i == gameBoard[1][1])continue;

            int[][] boardForTemp = new int[n+1][n+1];//initializing new board for the child
            for( int k = 1 ; k <= n ; k++ ) {
                for (int j = 1; j <= n; j++) {
                    boardForTemp[k][j] = gameBoard[k][j];
                }
            }
           // System.out.println("coloring region with color "+ i);
            colorRegion(1 , 1 , boardForTemp , i , this.gameBoard[1][1]);
            node temp = new node(boardForTemp , n ,heuristic);
            temp.color = i;
            temp.parent = this;
            temp.g = this.g + 1;
            if(heuristic == 1)temp.f = temp.g + heuristic1(temp);
            else temp.f = temp.g + heuristic2(temp);
            children.add(temp);
        }
        //System.out.println("returning children");
        return children;
    }



    public  int colorRegion( int i , int j , int[][] newBoard , int colorToBe , int currentRegionColor){
        if(i <= 0 || i > n || j <= 0 || j > n){
           // System.out.println("haha");
            return 0;
        }
        if(newBoard[i][j] != currentRegionColor){
           // System.out.println("nana");
            return 0;
        }
        newBoard[i][j] = colorToBe;
        colorRegion(i , j+1 , newBoard , colorToBe , currentRegionColor);
        colorRegion(i+1 , j , newBoard , colorToBe , currentRegionColor);
        colorRegion(i , j-1 , newBoard , colorToBe , currentRegionColor);
        colorRegion(i-1 , j , newBoard , colorToBe , currentRegionColor);
        return 0;
    }

    public int heuristic1(node temp){
        boolean visited[][] = new boolean[n+1][n+1];
        int numberOfConnectedComponents = 0;
        for( int i = 1 ; i <= n ; i++ ) {
            for (int j = 1; j <= n; j++) {
                if(!visited[i][j]){
                    connectedComponents(i,j,visited,temp.gameBoard);
                    numberOfConnectedComponents++;
                }
            }
        }
        return numberOfConnectedComponents;
    }

    public int heuristic2(node temp){
        dijsktra d = new dijsktra(n*n , temp.graph);
        return d.longestShortestPath;
    }


    public int connectedComponents(int i , int j , boolean[][] visited , int[][] gameBoard){
        visited[i][j] = true;
        if(i<n)if(gameBoard[i+1][j] == gameBoard[i][j] && !visited[i+1][j])connectedComponents(i+1,j,visited,gameBoard);
        if(j<n)if(gameBoard[i][j+1] == gameBoard[i][j] && !visited[i][j+1])connectedComponents(i,j+1,visited,gameBoard);
        if(i>1)if(gameBoard[i-1][j] == gameBoard[i][j] && !visited[i-1][j])connectedComponents(i-1,j,visited,gameBoard);
        if(j>1)if(gameBoard[i][j-1] == gameBoard[i][j] && !visited[i][j-1])connectedComponents(i,j-1,visited,gameBoard);
        return 0;
    }

    public void makeGraph(){
        for( int i = 1 ; i <a ; i++ ) {//initializing graph edges with -1 , edge to own node is 0
            for (int j = 1; j <a; j++) {
                if(i == j)graph[i][j] = 0;
                else graph[i][j] = -1;
            }
        }

        for( int i = 1 ; i <= n ; i++ ) {
            for (int j = 1; j <= n; j++) {
                int e = (i-1)*n + j;
                int f = (i-2)*n + j;
                int g = i*n + j;
                if(j<n){
                    if(gameBoard[i][j] != gameBoard[i][j+1])graph[e][e+1] = 1;
                    else graph[e][e+1] = 0;
                }
                if(j>1){
                    if(gameBoard[i][j] != gameBoard[i][j-1])graph[e][e-1] = 1;
                    else graph[e][e-1] = 0;
                }
                if(i<n){
                    if(gameBoard[i][j] != gameBoard[i+1][j])graph[e][g] = 1;
                    else graph[e][g] = 0;
                }
                if(i>1){
                    if(gameBoard[i][j] != gameBoard[i-1][j])graph[e][f] = 1;
                    else graph[e][f] = 0;
                }
            }
        }



    }
}
