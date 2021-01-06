#include "main.h"

#include <iostream>
#include "inkrement.h"

int zaeler = 0;

int main()
{
    cout << "Wie viele Male Aufrufen? ";
    int input;
    cin >> input;
    while (input > 0)
    {
        inkrement();
        input--;
    }

    return 0;
}
