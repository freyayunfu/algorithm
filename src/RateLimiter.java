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

