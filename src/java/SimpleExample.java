package com.mynotes.examples;

/*
A simple example class with a basic main method
that instantiates several objects of the class,
manipulates the objects, and outputs information
about the object
*/
public class SimpleExample {
    private int number;
    public SimpleExample() {
        System.out.println("Constructor called.");
        }
    public void setValue(int val) {
        number = val;
    }
    public int getNumber() {
        return number;
    }
    public static void main(String[] args) {
        SimpleExample example = new SimpleExample();
        for(int i=0;i<10;i++) {
            if(i/2 <= 2) {
                example.setValue(i);
            } else {
                example.setValue(i*10);
            }
            System.out.println("SimpleExample #" + i + "'s value is "+ example.getNumber());
        }
    }
}


