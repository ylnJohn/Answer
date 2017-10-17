package com.yln.question.util;

public class Random {
    private static final java.util.Random RANDOM = new java.util.Random();

    public float getRandom(float lower, float upper) {
        float min = Math.min(lower, upper);
        float max = Math.max(lower, upper);
        return getRandom(max - min) + min;
    }

    public float getRandom(float upper) {
        return RANDOM.nextFloat() * upper;
    }
    

    public int getRandom(int upper) {
        return RANDOM.nextInt(upper);
    }
    
    public double getRandom(){
    	return RANDOM.nextDouble();
    }
    
    public int getRandom(int lower, int upper) {
        int min = Math.min(lower, upper);
        int max = Math.max(lower, upper);
        return getRandom(max - min) + min;
    }

}
