public class MaxDiagnoalSquare {

    public static void main(String[] args){
//        int[][] matrix = {{1,1,0,0},{1,0,1,0},{1,0,0,1},{0,1,1,0}};
        int[][] matrix = {{1,0,0,0,0,0,1,1},{0,1,0,0,0,1,0,0},{0,0,1,0,1,0,1,0},{0,0,0,1,1,0,0,1}};
        SoluMaxDiagnoalSquareSolution solution = new SoluMaxDiagnoalSquareSolution();
        System.out.println("Max Diagnoal Square area is "+solution.getMaxDiagnoalSquare(matrix));
    }
}

class SoluMaxDiagnoalSquareSolution{
//    public int getMaxDiagnoalSquare(int[][] input){
//        int row = input.length;
//        int col = input[0].length;
//        int[][] dp = new int[row][col];
//        int[] left = new int[col];
//        int[] up = new int[col];
//        int area = 0;
//
//        for(int i = 0; i<row; i++) dp[i][0] = input[i][0];
//        for(int j = 0; j<col; j++){
//            dp[0][j] = input[0][j];
//            if(input[0][j]==0){
//                up[j] = 1;
//            }
//        }
//
//        for(int i = 1; i < row; i++){
//            if(input[i][0]==0) left[0] = 1;
//            for(int j = 1; j<col; j++){
//                if(input[i][j]==0){
//                    dp[i][j] = 0;
//                    left[j] = left[j-1]+1;
//                    up[j] = up[j-1]+1;
//                }else{
//                    if(input[i-1][j-1]==0 || input[i-1][j]==1 || input[i][j-1]==1) dp[i][j] = 1;
//                    else dp[i][j] = Math.min(dp[i-1][j-1],Math.min(left[j-1],up[j]))+1;
//                }
//                area = Math.max(area,dp[i][j]*dp[i][j]);
//                System.out.print(dp[i][j]+",");
//
//            }
//            System.out.println();
//        }
//        return area;
//    }

    public int getMaxDiagnoalSquare(int[][] input){
        int row = input.length;
        int col = input[0].length;
        int[][] dp = new int[2][col];
        int[] left = new int[2];
        int[] up = new int[2];
        int area = 0;

        for(int i = 0; i<row; i++) dp[i%2][0] = input[i][0];
        for(int j = 0; j<col; j++){
            dp[0][j] = input[0][j];
            if(input[0][j]==0){
                up[j%2] = 1;
            }
        }

        for(int i = 1; i < row; i++){
            if(input[i][0]==0) left[0] = 1;
            for(int j = 1; j<col; j++){
                if(input[i][j]==0){
                    dp[i%2][j] = 0;
                    left[j%2] = left[(j-1)%2]+1;
                    up[j%2] = up[(j-1)%2]+1;
                }else{
                    if(input[i-1][j-1]==0 || input[i-1][j]==1 || input[i][j-1]==1) dp[i%2][j] = 1;
                    else dp[i%2][j] = Math.min(dp[(i-1)%2][j-1],Math.min(left[(j-1)%2],up[j%2]))+1;
                }
                area = Math.max(area,dp[i%2][j]*dp[i%2][j]);
//                System.out.print(dp[i][j]+",");

            }
//            System.out.println();
        }
        return area;
    }
}
