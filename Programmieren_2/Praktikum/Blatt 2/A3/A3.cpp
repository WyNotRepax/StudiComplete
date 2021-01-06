#include <iostream>
#include "A3.h"
using namespace std;

int main(){
    cout <<"Wieviele Male aufrufen? ";
    int n;
    cin >> n;
    for (int i = 0; i < n ; i++){
        aufruf();
    }
}

void aufruf(){
    static int n = 0;
    n++;
    cout << n <<".Aufruf" << endl;

}