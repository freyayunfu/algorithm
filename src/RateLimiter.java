/*
 Distributed system rate limiter:
 http://blog.gainlo.co/index.php/2016/09/12/dropbox-interview-design-hit-counter/?utm_source=glassdoor&utm_campaign=glassdoor&utm_medium=92116
 In our case, we can get a hash of users email and distribute by the hash (it’s not a good idea to use email directly as
 some letter may appear much more frequent than the others).
 其他各种解法： http://massivetechinterview.blogspot.com/2015/10/develop-api-rate-limit-throttling-client.html
 这道题可以用list，或者queue来做的。那其实就是counter，详情见上面link
 https://classroom.udacity.com/courses/ud436/lessons/3630539349/concepts/4286285720923
 */
public class RateLimiter {

    public static void main(String[] args){

    }
}


//brute force: maintain a counter within an specific interval of time. When the request come in, increase the counter;
// When the new interval starts, reset the counter.
//Cons: Does not take the time of processing for each request into count. And it's not sliding window, waste the resources.
class rateLimiterCounter {
    int rate;
    long interval;
    long lastRequestTime;
    int counter;

    public rateLimiterCounter(int rate, int interval){
        this.rate = rate;
        this.interval = interval;
        this.lastRequestTime = System.currentTimeMillis();
        this.counter = 0;
    }

    public boolean grant(){
        synchronized((Integer)counter){
        // code to access/modify counter here
            long now = System.currentTimeMillis();
            if(now < lastRequestTime + interval){
                counter++;
                return counter <= rate;
            }else{
                counter = 0;
                lastRequestTime = now;
                return true;
            }
        }

    }
}

//Leaky bucket and token bucket are essentially mirror images of one another
//Smooth traffic flows
class leakyBucket{
    int water; // how much water in the bucket right now
    int rate; // the speed of the water leaking out the bucket
    int capacity;
    long lastRequestTime;

    public leakyBucket(int capacity, int rps){
        this.capacity = capacity;
        this.rate = rps*1000;
        this.lastRequestTime = System.currentTimeMillis();
        this.water = 0;
    }

    public boolean grant(){
        long now = System.currentTimeMillis();
        // leak the water first. Because the rate is fixed, we can take time*rate as the amount of water get leaked out
        water = Math.max(0, water -  (int) (now-lastRequestTime)*rate);
        lastRequestTime = now;
        if(water < capacity){
            water++;
            return true;
        }else{
            return false;
        }
    }
}

//Guava有两种限流模式，一种为稳定模式(SmoothBursty:令牌生成速度恒定)，一种为渐进模式(SmoothWarmingUp:令牌生成速度缓慢提升直到维持在一个稳定值)
//控制令牌生成的速度来决定periodic的，不同时间段的流量限制。
class tokenBucket{
    int bucketSize;
    int addKTokensPerSecond;
    int tokens;
    long lastRequestTime;

    public tokenBucket(int bucketSize,int addKTokensPerSecond){
        this.bucketSize = bucketSize;
        this.addKTokensPerSecond = addKTokensPerSecond;
        this.tokens = 0;
        this.lastRequestTime = System.currentTimeMillis();
    }

    public boolean grant(){
        long now = System.currentTimeMillis();
        /*
        http://massivetechinterview.blogspot.com/2015/08/ratelimiter-discovering-google-guava.html
        一种解法是，开启一个定时任务，由定时任务持续生成令牌。这样的问题在于会极大的消耗系统资源，
        如，某接口需要分别对每个用户做访问频率限制，假设系统中存在6W用户，则至多需要开启6W个定时任务来维持每个桶中的令牌数，这样的开销是巨大的。
        另一种解法则是延迟计算，如上resync函数。该函数会在每次获取令牌之前调用，其实现思路为，若当前时间晚于nextFreeTicketMicros，
        则计算该段时间内可以生成多少令牌，将生成的令牌加入令牌桶中并更新数据。这样一来，只需要在获取令牌时计算一次即可。
         */
        tokens = Math.min(bucketSize,tokens+(int)(now-lastRequestTime)*addKTokensPerSecond/1000);
        lastRequestTime = now;

        if(tokens < 1){
            return false;
        }else{
            tokens--;
            return true;
        }
    }
}

