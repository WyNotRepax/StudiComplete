#include <iostream>
#include "A2.h"
using namespace std;

int main()
{
    int a = 0;
    int b = 0;
    cout <<"a="<< a << " | b=" << b << endl;
    eingabe(a,b);
    cout <<"a="<< a << " | b=" << b << endl;
    swap(a,b);
    cout <<"a="<< a << " | b=" << b << endl;
    return 0;
}

void eingabe(int &a, int &b)
{
    cin >> a;
    cin >> b;
}

void swap(int &a, int &b)
{
    int temp = a;
    a = b;
    b = temp;
}
