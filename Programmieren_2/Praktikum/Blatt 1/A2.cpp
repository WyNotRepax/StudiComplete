#include <fstream>
#include <string>
#include <iomanip>
using namespace std;

int main()
{
    ifstream ein("eingabe.txt");
    ofstream aus("ausgabe.txt");
    int n = 1;
    int i;
    while (!(ein >> i).eof())
    {
        double d;
        ein >> d;
        string s;
        ein >> s;
        aus << n << ". | " << setfill('0') << setw(4) << setbase(16) << i << " | " << setbase(10) << setprecision(6) << d << " | " << setfill('-') << setw(15) << s << endl;
        n++;
    }
    ein.close();
    aus.close();
    return 0;
}
