package de.hsos.prog3.ab03;

import java.util.Scanner;

public class DoseTest {
    public static void main(String[] args) {
        Ringpuffer<String> dose = new Ringpuffer<>(2,true,false);
        System.out.println("Start");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Reinlegen?");
            String next = scanner.nextLine();
            if(next.equals("Ja") || next.equals("ja")){
                System.out.println("was?");
                next = scanner.nextLine();
                try{
                    dose.push(next);
                }
                catch(IllegalStateException e){
                    System.out.println("Dose ist Voll");
                }
            }
            else if (next.length() == 0) {
                break;
            }
            System.out.println("Rausnehmen");
            next = scanner.nextLine();
            if(next.equals("Ja") || next.equals("ja")){
                System.out.println(dose.removeLast() + " wird Rausgenommen.");
            }
            else if (next.length() == 0) {
                break;
            }
        }
        System.out.println("Ende der Bearbeitung.");

        System.out.println("In der Dose ist noch:");
        for(String s : dose){
            System.out.println(s);
        }
        System.out.println("Insgesamt " + dose.size() + " Sachen.");
    }
}
