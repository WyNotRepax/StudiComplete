#include "A1.h"
#include <iostream>

void printVector(vector<int> v)
{
    cout << "[";
    if (!v.empty())
    {
        cout << *v.begin();
        for (vector<int>::iterator i = v.begin() + 1; i != v.end(); i++)
        {
            cout << ",";
            cout << *i;
        }
    }
    cout << "]";
}

int main()
{
    {
        cout << "Teste sharedElements(vector <int> v1, vector <int> v2)" << endl;
        {

            vector<int> v1;
            vector<int> v2;

            cout << "v1 = ";
            printVector(v1);
            cout << endl;

            cout << "v2 = ";
            printVector(v2);
            cout << endl;

            cout << "return = ";
            printVector(sharedElements(v1, v2));
            cout << endl;
        }
        cout << endl;
        {

            vector<int> v1;
            vector<int> v2;

            v1.push_back(1);
            v1.push_back(2);
            v2.push_back(2);
            v2.push_back(3);

            cout << "v1 = ";
            printVector(v1);
            cout << endl;

            cout << "v2 = ";
            printVector(v2);
            cout << endl;

            cout << "return = ";
            printVector(sharedElements(v1, v2));
            cout << endl;
        }
        cout << endl;
        {

            vector<int> v1;
            vector<int> v2;

            v1.push_back(1);
            v1.push_back(2);
            v2.push_back(3);
            v2.push_back(4);

                cout
                << "v1 = ";
            printVector(v1);
            cout << endl;

            cout << "v2 = ";
            printVector(v2);
            cout << endl;

            cout << "return = ";
            printVector(sharedElements(v1, v2));
            cout << endl << endl;
        }
        cout << endl;
        {

            vector<int> v1;
            vector<int> v2;

            v1.push_back(1);
            v1.push_back(1);
            v1.push_back(2);
            v1.push_back(2);
            v1.push_back(3);
            v1.push_back(3);
            v2.push_back(1);
            v2.push_back(2);
            v2.push_back(2);

                cout
                << "v1 = ";
            printVector(v1);
            cout << endl;

            cout << "v2 = ";
            printVector(v2);
            cout << endl;

            cout << "return = ";
            printVector(sharedElements(v1, v2));
            cout << endl << endl;
        }
        cout << endl;
    }
    cout << endl;
    {
        cout << "Teste allDifferent(vector <int> v1, vector <int> v2)" << endl;
        {

            vector<int> v1;
            vector<int> v2;

            cout << "v1 = ";
            printVector(v1);
            cout << endl;

            cout << "v2 = ";
            printVector(v2);
            cout << endl;

            cout << "return = ";
            cout << boolalpha << allDifferent(v1, v2);
            cout << endl;
        }
        cout << endl;
        {

            vector<int> v1;
            vector<int> v2;

            v1.push_back(1);
            v1.push_back(2);
            v2.push_back(2);
            v2.push_back(3);

            cout << "v1 = ";
            printVector(v1);
            cout << endl;

            cout << "v2 = ";
            printVector(v2);
            cout << endl;

            cout << "return = ";
            cout << boolalpha << allDifferent(v1, v2);
            cout << endl;
        }
        cout << endl;
        {

            vector<int> v1;
            vector<int> v2;

            v1.push_back(1);
            v1.push_back(2);
            v2.push_back(3);
            v2.push_back(4);

                cout
                << "v1 = ";
            printVector(v1);
            cout << endl;

            cout << "v2 = ";
            printVector(v2);
            cout << endl;

            cout << "return = ";
            cout << boolalpha << allDifferent(v1, v2);
            cout << endl << endl;
        }
        cout << endl;
        {

            vector<int> v1;
            vector<int> v2;

            v1.push_back(1);
            v1.push_back(1);
            v1.push_back(2);
            v1.push_back(2);
            v1.push_back(3);
            v1.push_back(3);
            v2.push_back(1);
            v2.push_back(2);
            v2.push_back(2);

                cout
                << "v1 = ";
            printVector(v1);
            cout << endl;

            cout << "v2 = ";
            printVector(v2);
            cout << endl;

            cout << "return = ";
            cout << boolalpha << allDifferent(v1, v2);
            cout << endl << endl;
        }
        cout << endl;
    }
    return 0;
}
