package de.hsos.prog3.ab03;

import java.util.Scanner;

public class WarteschlangeTest {
    public static void main(String[] args) {
        Ringpuffer<String> warteschlange = new Ringpuffer<>(2,false,false);
        System.out.println("Start");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Stellt sich Jemand an?");
            String next = scanner.nextLine();
            if(next.equals("Ja") || next.equals("ja")){
                System.out.println("Wer?");
                next = scanner.nextLine();
                warteschlange.push(next);
            }
            else if (next.length() == 0) {
                break;
            }
            System.out.println("Wird Jemand bedient?");
            next = scanner.nextLine();
            if(next.equals("Ja") || next.equals("ja")){
                System.out.println(warteschlange.pop() + " wird bedient.");
            }
            else if (next.length() == 0) {
                break;
            }
        }
        System.out.println("Ende der Bearbeitung.");

        System.out.println("In der Schlange stehen noch:");
        for(String s : warteschlange){
            System.out.println(s);
        }
        System.out.println("Insgesamt " + warteschlange.size() + " Leute.");
    }


}
