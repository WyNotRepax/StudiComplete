#include <iostream>
using namespace std;

int eingabeanalyse(double &, double &, double &);

int main()
{
    double max = -100;
    double min = 100;
    double avg = 0;
    int n = eingabeanalyse(max, min, avg);
    return 0;
}

int eingabeanalyse(double &max, double &min, double &avg)
{
    int n = 0;
    while (true)
    {
        cout << "Zahl zwischen -100 und 100 eingeben: ";
        double input;
        cin >> input;
        if (input == -1)
        {
            break;
        }
        else if (input > -100 && input < 100)
        {
            if (input > max)
            {
                max = input;
            }
            if (input < min)
            {
                min = input;
            }
            avg = (avg * n) + input;
            n++;
            avg /= n;
            cout << "Max: " << max << " | Min:" << min << " | Avg: " << avg << " | Anzahl der gueltigen Eingaben: " << n << endl;
        }
    }
    return n;
}