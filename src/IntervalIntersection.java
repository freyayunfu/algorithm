import java.util.ArrayList;
import java.util.List;

public class IntervalIntersection {

    public static void main(String[] args){
        int[][] nums1 = {{1,3},{5,9},{10,18},{24,30}};
        int[][] nums2 = {{6,10},{29,50}};
//        int[][] nums2 = {{1,3},{5,9},{10,18},{24,30}};
//        int[][] nums1 = {{3,5},{40,50}};
        Solution solution = new Solution();
        List<int[]> ans = solution.getIntersections(nums1,nums2);
        for(int[] interval:ans){
            System.out.println(interval[0]+", "+interval[1]);
        }

    }
}

class Solution{
    // intervalA = {a1,a2} intervalB = {b1,b2} if b1 <= a2 && b2 >=a1  or b1 >= a1 && b2 <= a2, there is an intersection.
    // And intersection will be {max(a1,b1),min(a2,b2)}

    public List<int[]> getIntersections(int[][] nums1, int[][] nums2){
        List<int[]> ans = new ArrayList<>();
        int p1 = 0; int p2 = 0;
        while(p1 < nums1.length && p2 < nums2.length){
            if(hasInterval(nums1[p1],nums2[p2])){
                ans.add(getInterval(nums1[p1],nums2[p2]));
            }
            if(nums1[p1][1] < nums2[p2][1]) p1++;
            else p2++;

        }

        return mergeIntervals(ans);
    }

    private boolean hasInterval(int[] a, int[] b){
        if(b[0] <= a[1] && b[1] >= a[0]){
            return true;
        }else if(b[0] >= a[0] && b[1] <= a[1]){
            return true;
        }
        return false;
    }

    private int[] getInterval(int[] a, int[]b){
        int[] ans = new int[2];
        ans[0] = Math.max(a[0],b[0]);
        ans[1] = Math.min(a[1],b[1]);
        return ans;
    }

    private List<int[]> mergeIntervals(List<int[]> input){
        if(input==null || input.size()==0) return input;
        List<int[]> ans = new ArrayList<>();
        int start = input.get(0)[0]; int end = input.get(0)[1];
        for(int i = 1; i<input.size();i++){
            int newStart = input.get(i)[0];
            int newEnd = input.get(i)[1];
            if(end == newStart || end+1==newStart){
                end = newEnd;
            }else{
                ans.add(new int[]{start, end});
            }

            if(i==input.size()-1 && end != newEnd) ans.add(new int[]{newStart,newEnd});
        }

        return ans;
    }
}
