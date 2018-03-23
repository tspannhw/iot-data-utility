package com.streever.iot.data.utility.generator.fields.support;

public class Range<T> {

    private T min;
    private T max;

    public Range() {
    }

    public Range(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

}
