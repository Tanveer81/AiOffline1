package com.company;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class aStarSearch {
    int n = 0;
    int numberOfMoves = 0;
    int heuristic = 0;
    ArrayList<node> children = new ArrayList<node>();
    ArrayList<node> previouslyVisited = new ArrayList<node>();
    PriorityQueue<node> heap=new PriorityQueue<node>(10, new Comparator<node>() {
        public int compare(node node1, node node2) {
            if (node1.f < node2.f) return -1;
            if (node1.f > node2.f) return 1;
            return 0;
        }
    });

    public aStarSearch(int n , int heuristic) {
        this.n = n;
        this.heuristic = heuristic;
    }

    public node aStarSearchAlgo(int[][] gameboard ) {
        //System.out.println("entering a star search");
        node initialNode = new node(gameboard , n , heuristic);
        initialNode.g = 0;
        initialNode.parent = null;
        heap.add(initialNode);
        node currentNode = null;

        while (true) {
           // System.out.println("in infinite loop");
            currentNode = heap.remove();
            previouslyVisited.add(currentNode);

            if (currentNode.checkIfGoal()) {
               // System.out.println("Goal node found");
                return currentNode;
            }
            children = currentNode.getChildren();

            for( node a : children ) {
                node existingSameNode = isNeighbourInHeap(a);
                if(existingSameNode != null && !checkIfPreviouslyVisited(a)){ //if a belongs to heap
                    if(a.f < existingSameNode.f){
                    //replace
                        heap.remove(existingSameNode);
                        heap.add(a);
                    }
                }
                else{
                    heap.add(a);
                }
            }
        }
    }

    public boolean checkIfPreviouslyVisited(node currentChildNode){
        for(node previousNode : previouslyVisited){
            if(isSame(currentChildNode , previousNode))return true;
        }
        return false;
    }

    public boolean isSame(node a , node b){
        for(int i = 1 ; i <= n ; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.gameBoard[i][j] != b.gameBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public node isNeighbourInHeap(node neighbour){
       // System.out.println("in isNeighbourInHeap");
    //returns the node in the heap if matches else returns null
        boolean isSame = true;
        for (node a : heap){
            for( int i = 1 ; i <= n ; i++ ){
                for( int j = 1 ; j <= n ; j++ ){
                    if(neighbour.gameBoard[i][j] != a.gameBoard[i][j]){
                        isSame = false;
                    }
                }
            }
            if(isSame)return a; //match found returning it
        }
        return null;
    }

    public void getResult(node node , ArrayList<node> resultNodes){
            if(node.parent!=null){
                getResult(node.parent, resultNodes);
                numberOfMoves++;
            }
           resultNodes.add(node);

    }
}

