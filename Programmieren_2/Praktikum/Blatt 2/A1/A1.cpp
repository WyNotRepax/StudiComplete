#include <iostream>
#include <iomanip>
#include <math.h>

using namespace std;

int main()
{
    float n = 1;
    double before;
    while (!isinf(n))
    {
        before = n;
        n *= 2;
    }
    cout << before << endl;
    return 0;
}
