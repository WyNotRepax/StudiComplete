/*
 * -----------------------------------------------------------------------------
 *
 * Programm: FifoTest.cpp
 *
 * Demonstration und Pruefung der Klasse Fifo
 *
 * Ursprung: Prof. Dr. B. Lang
 *
 * Praktikum Programmierung 2, O. Henkel, HS Osnabrueck
 * -----------------------------------------------------------------------------
 */

#include <iostream>
#include <string>
#include <fstream>
using namespace std;
#include "Fifo.h"



/*
 * Testprogramm: Textdatei bitte als Argument uebergeben
 *               Als Default wird die Datei zitat verwendet
*/
int main(int nParameter, char *parameter[]) {

    //
    // Normaler Betrieb
    //
    cout << "*** Zunaechst wird der normale Betrieb der Fifo-Klasse abgetestet ***" << endl;
    try {
        Fifo<string> s; // Leeres Fifo erstellen
        cout << "> Fifo aus Textdatei fuellen" << endl;
        {
            string token;
            ifstream in((nParameter>1) ? parameter[1] : "zitat");
            if (!in) throw "Kann Datei nicht oeffnen";
            while (1) {
                in >> token;
                if (in.eof()) break;
                s << token;
            }
        }

        cout << "> Fifo ausgeben" << endl;
        while (s>0) { // Elemente ausgeben
            cout << s.pop() << endl;
        }

        cout << "> Fifo mittels Ein- und Ausgabeoperator fuellen" << endl;
        s << "One"; s << "Two"; s << "Three";

        cout << "> tiefe Kopie anlegen und zuweisen" << endl;
        Fifo<string> kopie1(s);
        Fifo<string> kopie2;
        kopie2=s;
        kopie2=kopie2;
        kopie1 << "Four";
        kopie2.push("Five");
        cout << "> Fifo-Kopien ausgeben (die erste drei Werte muessen jeweils gleich sein)" << endl;
        string help;
        cout << "  s:"       << endl;
        s      >> help; cout << help << endl;
        s      >> help; cout << help << endl;
        s      >> help; cout << help << endl;
        cout << "Fifo sollte nun leer sein - tatsaechliche Anzahl der Elemente: " << s.size() << endl;
        cout << "  kopie1:" << endl;
        kopie1 >> help; cout << help << endl;
        kopie1 >> help; cout << help << endl;
        kopie1 >> help; cout << help << endl;
        kopie1 >> help; cout << help << endl;
        cout << "Fifo sollte nun leer sein - tatsaechliche Anzahl der Elemente: " << kopie1.size() << endl;
        cout << "  kopie2:" << endl;
        kopie2.info();
        cout << "Fifo sollte noch alle Elemente enthalten - tatsaechliche Anzahl der Elemente: " << kopie2.size() << endl;
    } catch (const char* error) {
        cout << "> Failure: Fifo Objekt sollte hier keine Ausnahme werfen:" << endl;
        cout << " >>> " << error << endl;
        return 3;
    } catch (...) {
        cout << "> Failure: Unbekannte Ausnahme" << endl;
        return 3;
    }

    //
    // Fifo Unterlauf testen
    //
    cout << "*** Es werden nun Fifo-Unterlaeufe passieren ***" << endl;
    {
        Fifo<string> s; // Leeres Fifo einrichten
        try {
            cout << s.pop() << endl;
        } catch (const char* error) {
            cout << "> Erwartete Ausnahme wegen Fifo-Unterlauf:" << endl;
            cout << " >>> " << error << endl;
        } catch (...) {
            cout << "> Failure: Unbekannte Ausnahme" << endl;
            return 3;
        }
        try {
            string help;
            cout << "> Fifo mittels Ein- und Ausgabeoperator fuellen" << endl;
            s << "Eins"; s << "Zwei"; s << "Drei";
            cout << "> Fifo ausgeben und Unterlauf herbeifuehren" << endl;
            s >> help; cout << help << endl;
            s >> help; cout << help << endl;
            s >> help; cout << help << endl;
            s >> help; cout << help << endl; // Hier muss ein Unterlauf passieren
        } catch (const char* error) {
            cout << "> Erwartete Ausnahme wegen Fifo-Unterlauf:" << endl;
            cout << " >>> " << error << endl;
        } catch (...) {
            cout << "> Failure: Unbekannte Ausnahme" << endl;
            return 3;
        }
    }
    return 0;

}