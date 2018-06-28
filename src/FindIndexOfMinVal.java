/*
Extend of leetcode 154: https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/description/
find the minimum value index
Consider this case: 1 1 1 1 1 1 1 1 2 1 1
the min index returned is 0, while actually it should be 9.
For this case: 2 2 2 2 2 2 2 2 1 2 2
it will return the correct index, which is 8.
 */

public class FindIndexOfMinVal {

    public static void main(String[] args){
        int[] input = {1,1,1,1,1,1,1,1,2,1,1};
        int[] input2 = {2,2,2,2,2,2,2,2,1,2,2};
        FindIndexOfMinValSolution findIndexOfMinVal = new FindIndexOfMinValSolution();
        System.out.println(findIndexOfMinVal.findMin(input));
        System.out.println("Second input:");
        System.out.println(findIndexOfMinVal.findMin(input2));
    }
}

class FindIndexOfMinValSolution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length-1;
        int i = 0;
        while(left+1<right){
            System.out.println("loop"+i);
            if (nums[right - 1] > nums[right]) {
                System.out.println("Break in loop"+i);
                break;
            }
            //保证了不会因为有重复的数字，而一直循环下去。
            right--;
            int mid = left+(right-left)/2;
            if(nums[mid] > nums[right]){
                left = mid;
            }else if(nums[mid] < nums[right]){
                right = mid;
            }
            i++;
        }

        return nums[left] < nums[right]? left: right;
    }
}
