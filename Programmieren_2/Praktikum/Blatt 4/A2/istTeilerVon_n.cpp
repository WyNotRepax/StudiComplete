#include "istTeilerVon_n.h"

istTeilerVon_n::istTeilerVon_n(int n) : n(n) {}

bool istTeilerVon_n::operator()(int i)
{
    if (i == 0)
    {
        return false;
    }
    return (n % i == 0);
}