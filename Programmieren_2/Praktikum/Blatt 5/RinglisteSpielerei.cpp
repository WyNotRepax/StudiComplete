/*
 * -----------------------------------------------------------------------------
 *
 * Beispielprogramm: RinglisteSpielerei.cpp
 *
 * Verwendungsbeispiel der Klasse Ringliste
 *
 * Praktikum Programmierung 2, O. Henkel, HS Osnabrueck
 * -----------------------------------------------------------------------------
 */

#include "Ringliste.h"
#include <iostream>
#include <iomanip>
using namespace std;


int main() {
 Ringliste rListe(5);               // Kapazitaet=5
 cout << rListe.toString() << endl; // toString-Methode
 
 rListe << 1 << 2 << 3; // operator<< geeignet ueberladen
 cout << rListe.toString() << endl;
 rListe << 4 << 5 << 6;
 cout << rListe.toString() << endl;
 Ringliste vergleich(5);
 vergleich << 2 << 3 << 4 << 5 << 6;
 // erwartet: true
 cout << boolalpha << (rListe==vergleich) << endl;
 
 Ringliste drei; // Kapazitaet=10 (Default)
 drei << 3 << 3 << 3;
 drei+=6;        // operator+= geeignet ueberladen
 cout << drei.toString() << endl;
 
 rListe << drei; // operator<< geeignet ueberladen
 cout << rListe.toString() << endl;
 cout << drei.toString() << endl;
 
 return 0;
}
