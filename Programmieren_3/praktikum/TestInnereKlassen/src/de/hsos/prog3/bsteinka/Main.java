package de.hsos.prog3.bsteinka;

public class Main {

    public static void main(String[] args) {
	// write your code here
        K k = new K();
        K.InStatic test1 = new K.InStatic();
        K.InMember test2 = k.inMember;
        test2.test();
        Integer a = 100;
    }
    
}
