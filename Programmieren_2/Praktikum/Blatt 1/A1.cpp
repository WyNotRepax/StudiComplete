#include <iostream>
#include <iomanip>

using namespace std;

bool f1(int);
void f2(float &, int);
void f3(int &, int &);
int f4(int);
float f5(float &, float);
bool f6(int);

int main()
{
    int input = -1;
    while (input != 0)
    {
        cout << "Waele eine Funktion (1, 2, 3, 4, 5, 6) oder 0 zum Beenden!" << endl;
        cin >> input;
        switch (input)
        {
        case 0:
            cout << "Auf Wiedersehen!" << endl;
            return 0;
        case 1:
            cout << "Pruefung ob eingabe Fibbonacci Zahl ist oder nicht." << endl;

            cout << "Eingabe (int): ";
            int f1Input;
            cin >> f1Input;

            cout << "f(" << f1Input << ") == " << boolalpha << f1(f1Input) << endl;

            break;
        case 2:
            cout << "Rundung eines float-Wertes." << endl;

            cout << "Eingabe 1 (float): ";
            float f2Input1;
            cin >> f2Input1;

            cout << "Eingabe 2 (int): ";
            int f2Input2;
            cin >> f2Input2;

            cout << "f(" << f2Input1 << "F," << f2Input2 << ") == ";
            f2(f2Input1, f2Input2);
            cout << f2Input1 << "F" << endl;

            break;
        case 3:
            cout << "Summe und Differenz zweier Werte." << endl;

            cout << "Eingabe 1 (int): ";
            int f3Input1;
            cin >> f3Input1;

            cout << "Eingabe 2 (int): ";
            int f3Input2;
            cin >> f3Input2;

            cout << "f(" << f3Input1 << "," << f3Input2 << ") == ";
            f3(f3Input1, f3Input2);
            cout << "(" << f3Input1 << "," << f3Input2 << ")" << endl;

            break;
        case 4:
            cout << "Summe aller Zahlen bis zu einer Zahl." << endl;

            cout << "Eingabe (int): ";
            int f4Input;
            cin >> f4Input;

            cout << "f(" << f4Input << ") == " << f4(f4Input) << endl;
            break;
        case 5:
            cout << "Uhrzeiten Addieren." << endl;

            cout << "Uhrzeit 1 (float): ";
            float f5Input1;
            cin >> f5Input1;

            cout << "Uhrzeit 2 (float): ";
            float f5Input2;
            cin >> f5Input2;

            cout << setfill('0') << setw(2) << (int)f5Input1;
            cout << ":";
            cout << setfill('0') << setw(2) << ((int)(f5Input1 * 100) % 100);
            cout << " + ";
            cout << setfill('0') << setw(2) << (int)f5Input2;
            cout << ":";
            cout << setfill('0') << setw(2) << ((int)(f5Input2 * 100) % 100);
            cout << " == ";
            f5(f5Input1, f5Input2);
            cout << setfill('0') << setw(2) << (int)f5Input1;
            cout << ":";
            cout << setfill('0') << setw(2) << ((int)(f5Input1 * 100) % 100);
            cout << endl;

            break;
        case 6:
            cout << "Ueberprueft ob in einer Zahl die Ziffer 7 vorkommt!" << endl;
            
            cout << "Eingabe (int): ";
            int f6Input;
            cin >> f6Input;

            cout << "f(" << f6Input << ") == " << boolalpha << f6(f6Input) << endl;

            break;
        default:
            cout << "Eingabe keine moegliche Funktion!" << endl;
            break;
        }
    }
}

bool f1(int n)
{
    int fib1 = 1;
    int fib2 = 1;
    while (fib1 < n)
    {
        int temp = fib1 + fib2;
        fib2 = fib1;
        fib1 = temp;
    }
    return (n == fib1);
}

void f2(float &f, int n)
{
    for (int i = 0; i < n; i++)
    {
        f *= 10;
    }
    f = (float)((int)(f + 0.5F));
    for (int i = 0; i < n; i++)
    {
        f /= 10;
    }
}

void f3(int &n1, int &n2)
{
    int temp1 = n1 + n2;
    int temp2 = n1 - n2;
    n1 = temp1;
    n2 = temp2;
}

int f4(int n)
{
    if (n < 0)
    {
        return -1;
    }
    int sum = 0;
    for (int i = 0; i <= n; i++)
    {
        sum += i;
    }
    return sum;
}

float f5(float &f1, float f2)
{
    int hours = ((int)f1 + (int)f2) % 24;
    int mins = ((int)(f1 * 100) % 100) + ((int)(f2 * 100) % 100);
    if (mins > 60)
    {
        mins = mins % 60;
        hours++;
    }
    f1 = (float)hours + ((float)mins / 100);
    return f1;
}

bool f6(int n)
{
    if (n < 0)
    {
        n *= -1;
    }
    while (n > 0)
    {
        if (n % 10 == 7)
        {
            return true;
        }
        n /= 10;
    }
    return false;
}
