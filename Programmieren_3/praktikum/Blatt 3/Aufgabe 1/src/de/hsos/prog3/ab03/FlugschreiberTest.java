package de.hsos.prog3.ab03;

import java.util.Iterator;
import java.util.Scanner;

public class FlugschreiberTest {
    public static void main(String[] args) {
        Ringpuffer<String> flugschreiber = new Ringpuffer<>(10, true, true);

        System.out.println("Start der Aufnahme");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String next = scanner.nextLine();
            if (next.length() == 0) {
                break;
            }
            flugschreiber.push(next);
        }
        System.out.println("Ende der Aufnahme");

        System.out.println("Start der Wiedergabe");
        for(String s : flugschreiber){
            System.out.println(s);
        }
        System.out.println("Ende der Wiedergabe");
    }
}
