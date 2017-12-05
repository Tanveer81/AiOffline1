package com.company;


public class dijsktra {
    int longestShortestPath = 0;
    int n;
    int distance[];
    boolean shortestPathTreeSet[];
    int[][] graph;

    public dijsktra(int n , int[][] graph){ //n = #nodes
        this.n = n;
        this.graph = graph;
        dijsktraAlgo();

    }

    int minumumDistace(){
        int minimumDistance = Integer.MAX_VALUE;
        int minimumIndex = 0;
        for(int i = 1 ; i <= n ; i++){
            if(shortestPathTreeSet[i] == false && distance[i] <= minimumDistance){
                minimumDistance = distance[i];
                minimumIndex = i;
            }
        }
        return minimumIndex;
    }

    public void dijsktraAlgo(){
        distance = new int [n+1];
        shortestPathTreeSet = new boolean[n+1];
        for(int i = 1 ; i <= n ; i++){
            distance[i] = Integer.MAX_VALUE;
            shortestPathTreeSet[i] = false;
        }
        distance[1] = 0;

        for(int i = 0 ; i < n ; i++){
            int minIndex = minumumDistace();
            shortestPathTreeSet[minIndex] = true;

            for(int j = 1 ; j <= n ; j++){
                if(!shortestPathTreeSet[j] && graph[minIndex][j] != -1
                        && distance[minIndex]!= Integer.MAX_VALUE
                        && distance[minIndex] + graph[minIndex][j] < distance[j])
                    distance[j] = distance[minIndex] + graph[minIndex][j];
            }
        }

        for(int i = 1 ; i <= n ; i++){
            if( distance[i] >= longestShortestPath){
                longestShortestPath = distance[i];
            }
        }
    }

}
