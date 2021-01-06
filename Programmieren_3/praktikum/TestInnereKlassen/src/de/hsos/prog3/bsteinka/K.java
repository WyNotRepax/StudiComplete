package de.hsos.prog3.bsteinka;

public class K {
    public String zustand = "zustand";
    public InMember inMember = new InMember();

    K() {

    }

    public InMember test(){
        return new InMember();
    }


    public static class InStatic {
        InStatic() {
        }


    }

    public class InMember {
        InMember() {
        }


        public String test() {
            return zustand;
        }
    }
}
