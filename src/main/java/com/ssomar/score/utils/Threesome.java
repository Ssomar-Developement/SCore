package com.ssomar.score.utils;

public class Threesome<T, Y, Z> {

    private T elem1;

    private Y elem2;

    private Z elem3;

    public Threesome(T elem1, Y elem2, Z elem3) {
        this.elem1 = elem1;
        this.elem2 = elem2;
        this.elem3 = elem3;
    }

    public T getElem1() {
        return elem1;
    }

    public void setElem1(T elem1) {
        this.elem1 = elem1;
    }

    public Y getElem2() {
        return elem2;
    }

    public void setElem2(Y elem2) {
        this.elem2 = elem2;
    }

    public Z getElem3() {
        return elem3;
    }

    public void setElem3(Z elem3) {
        this.elem3 = elem3;
    }


    public static void main(String[] args){
        String s = "mmof";
        if(s.endsWith("of")){
            s = s.substring(0, s.length()-2);
        }
        System.out.println(s);
    }
}
