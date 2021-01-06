#include <iostream>
using namespace std;

void eingabe(int *, int *);
void swap(int *, int *);

int main(int argc, char const *argv[])
{
    int i1 = 0;
    int i2 = 0;
    int *ip1 = &i1;
    int *ip2 = &i2;
    cout << "Adresse:\n";
    cout << "ip1: " << &ip1 << "\nip2: " << &ip2 << "\ni1: " << &i1 << "\ni2: " << &i2 << "\n\n";
    cout << "t1:\nip1: " << ip1 << "\nip2: " << ip2 << "\ni1: " << i1 << "\ni2: " << i2 << "\n\n";
    eingabe(ip1, ip2);
    cout << "t2:\nip1: " << ip1 << "\nip2: " << ip2 << "\ni1: " << i1 << "\ni2: " << i2 << "\n\n";
    swap(ip1, ip2);
    cout << "t3:\nip1: " << ip1 << "\nip2: " << ip2 << "\ni1: " << i1 << "\ni2: " << i2 << "\n\n";
}

void eingabe(int *x, int *y)
{
    cout << "Ersten Wert Eingeben:";
    cin >> *x;

    cout << "Zweiten Wert Eingeben:";
    cin >> *y;
}

void swap(int *a, int *b)
{
    int temp = *a;
    *a = *b;
    *b = temp;
}