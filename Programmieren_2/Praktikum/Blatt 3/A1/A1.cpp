#include "math.h"

int ggT(int &a, int b)
{
    int h;
    if (a == 0)
    {
        a = b;
        return a;
    }
    else if (b != 0)
    {
        do
        {
            h = a % b;
            a = b;
            b = h;
        } while (b != 0);
    }
    a = abs(a);
    return a;
}