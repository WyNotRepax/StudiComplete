package de.hsos.prog3.bsteinka;

public class FunctionsKlassen {

    public static void main(String[] args) {

        TestInterface t = new TestInterface() {
            @Override
            public void doStuff(Object o) {
                System.out.println(toString());
                System.out.println(o.toString());
            }
        };

        t.doStuff("Hallo");
    }
}

@FunctionalInterface
interface TestInterface {
    public void doStuff(Object o);
    public String toString();
}
