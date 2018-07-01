/*
LeetCode 317
Given a matrix, with values 'W', 'G', '0',  'W' means wall, 'G' means Guard, 0 means the way you can go.
Find the distance of all points to nearest guard. 请问Lz W到G的距离需要算吗？多谢~ 不用，只需要G TO G的距离
 */

import java.util.*;

public class NearestGuard {
    public static void main(String[] args){


        String[][] matrix = {{"0","W","G","0"},{"0","G","W","0"},{"0","G","0","G"}};
        NearestGuardSolution nearestGuardSolution = new NearestGuardSolution();
        int[][] dist = nearestGuardSolution.getNearestGuardM2N2(matrix);

        int row = dist.length;
        int col = dist[0].length;
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                System.out.print(dist[i][j]+",");
            }
            System.out.println();
        }

    }

}

class NearestGuardSolution{
    public int[][] getNearestGuardM2N2(String[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int[][] dist = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j].equals("G")) {
                    int[] cell = new int[]{i, j,0};
                    m2n2bfs(matrix,dist,row,col,cell);
                }
            }
        }
        return dist;
    }


    private void m2n2bfs(String[][] matrix, int[][] dist,int row, int col, int[] cell){
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(cell);
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        boolean[][] visited = new boolean[row][col];
        visited[cell[0]][cell[1]] = true;

        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i<size; i++){
                int[] p =  queue.poll();
                int x = p[0];
                int y = p[1];
                int distance=p[2];

                for(int j = 0; j<4; j++){
                    int newx = x+dx[j];
                    int newy = y+dy[j];
                    if (newx >= row || newx < 0 || newy >= col || newy < 0 ||visited[newx][newy]) continue;
                    visited[newx][newy] = true;
                    if (!matrix[newx][newy].equals("W")) {
//                        System.out.println("Offer: " + newx + "," + newy);
                        queue.offer(new int[]{newx, newy,distance+1});
                        if(matrix[newx][newy].equals("G")){
                            dist[cell[0]][cell[1]] = distance+1;
                            return;
                        }
                    }
                }

            }
        }


    }


    public int[][] getNearestGuard(String[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

//        HashMap<int[], Set<int[]>> map = new HashMap<>();
        PriorityQueue<Queue<int[]>> heap = new PriorityQueue<>(new Comparator<Queue<int[]>>() {
            @Override
            public int compare(Queue<int[]> o1, Queue<int[]> o2) {
                return o1.size()-o2.size();
            }
        });

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int[][] dist = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                dist[i][j] = -1;

                if (matrix[i][j].equals("G")) {
                    Queue<int[]> queue = new LinkedList<int[]>();
                    int[] cell = new int[]{i, j,0};
//                    HashSet<int[]> set = new HashSet<>();
//                    set.add(cell);
//                    map.put(cell, set);
                    queue.offer(cell);
                    heap.offer(queue);
                }
            }
        }


        while(!heap.isEmpty()){
            Queue<int[]> queue = heap.poll();
            while (!queue.isEmpty()) {
                int size = queue.size();
                for(int k = 0; k<size; k++){
                    int[] cell = queue.poll();
                    int x = cell[0];
                    int y = cell[1];
                    int distance = cell[2];

                    for (int i = 0; i < 4; i++) {
                        int newx = x + dx[i];
                        int newy = y + dy[i];
                        int[] newcell = {newx, newy};
                        if (newx >= row || newx < 0 || newy >= col || newy < 0 ) continue;

                        if (!matrix[newx][newy].equals("W")) {
                            System.out.println("Offer: " + newx + "," + newy);
                            queue.offer(new int[]{newx, newy,distance+1});
                        }
                    }
                }
            }
        }


        return dist;
    }


}
