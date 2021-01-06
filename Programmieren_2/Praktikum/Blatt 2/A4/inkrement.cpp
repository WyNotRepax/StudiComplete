#include "inkrement.h"
#include <iostream>

extern int zaeler;

void inkrement(void){
    zaeler ++;
    cout << zaeler << ". Aufruf" << endl;
}