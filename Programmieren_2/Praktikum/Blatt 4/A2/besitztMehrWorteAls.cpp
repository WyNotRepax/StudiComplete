#include "besitztMehrWorteAls.h"

#include <iostream>

int zaehlWorte(string s)
{
    int worte = 0;
    int wortLaenge = 0;
    for (string::iterator i = s.begin(); i != s.end(); i++)
    {
        if (*i == ' ')
        {
            if (wortLaenge >= 2)
            {
                worte++;
            }
            wortLaenge = 0;
        }
        else
        {
            wortLaenge++;
        }
    }
    if (wortLaenge >= 2)
    {
        worte++;
    }
    return worte;
}

bool besitztMehrWorteAls::operator()(string s1, string s2)
{
    return zaehlWorte(s1) > zaehlWorte(s2);
}

int main(int argc, char const *argv[])
{
    besitztMehrWorteAls test;

    cout << boolalpha << test("Dies ist ein Teststring","Das hier auch nicht.") << endl;
}
