#include <iostream>
#include <iomanip>
using namespace std;

bool f1(int);
float f2(float, int);
void f3(int &, int);
int f4(int);
void f5(float &, float);
bool f6(int);

int main()
{
    cout << boolalpha << f6(-2598) << endl;
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

float f2(float f, int n)
{
    for (int i = 0; i < n; i++)
    {
        f *= 10;
    }
    f = (float)((int)f + 0.5F) - 0.5F;
    for (int i = 0; i < n; i++)
    {
        f /= 10;
    }
    return f;
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

void f5(float &f1, float f2)
{
    int hours = ((int)f1 + (int)f2) % 24;
    int mins = ((int)(f1 * 100) % 100) + ((int)(f2 * 100) % 100);
    if (mins > 60)
    {
        mins = mins % 60;
        hours++;
    }
    f1 = (float)hours + ((float)mins / 100);
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

