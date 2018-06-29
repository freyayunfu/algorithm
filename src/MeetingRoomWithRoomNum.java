import java.util.*;

public class MeetingRoomWithRoomNum {
    public static void main(String[] args){
        Interval[] input = {new Interval(1,3),new Interval(10,20),new Interval(5,8),new Interval(2,6)};
        Interval[] input2 = {new Interval(1,3),new Interval(10,20),new Interval(5,8),new Interval(2,6),new Interval(5,7)};
        meetingRoom meetingRoom = new meetingRoom();
        Interval[] ans = meetingRoom.getMeetingRoomNum(input2);
        for(Interval in:ans){
            System.out.println(in);
        }

    }
}

class meetingRoom{
    public Interval[] getMeetingRoomNum(Interval[] intervals){
        Interval[] ans = new Interval[intervals.length];
        Arrays.sort(intervals,(a, b) -> (a.start-b.start));
        PriorityQueue<Interval> heap = new PriorityQueue<Interval>(intervals.length,(a,b)->(a.end-b.end));
        Stack<Integer> roomNum = new Stack<>();
        for(int k = intervals.length; k>=1; k--){
            roomNum.push(k);
        }
        int i = 0;
        int max = 0;
        for(Interval interval:intervals){
            while(!heap.isEmpty() && heap.peek().end < interval.start){
                Interval in = heap.poll();
                int lastMeetingRoom = in.roomNum;
                roomNum.push(lastMeetingRoom);
            }


            heap.offer(interval);
            interval.roomNum = roomNum.pop();
            ans[i++] = interval;

            max =  Math.max(max,heap.size());
        }

        System.out.println("max number of rooms needed = "+max);
        return ans;
    }
}

class Interval{
    int start;
    int end;
    int roomNum = -1;

    public Interval(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString(){
        return "start = "+start+" end = "+end+" meetingroom = "+roomNum;
    }
}
