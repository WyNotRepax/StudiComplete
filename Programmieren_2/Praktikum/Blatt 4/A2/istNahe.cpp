#include "istNahe.h"
#include "math.h"

istNahe::istNahe(double d, double tolerance) : d(d), tolerance(tolerance) {}

bool istNahe::operator()(double z)
{
    return abs(d - z) < tolerance;
}