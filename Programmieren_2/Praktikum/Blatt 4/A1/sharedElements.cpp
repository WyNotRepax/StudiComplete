#include "A1.h"
#include <vector>

vector<int> sharedElements(vector<int> v1, vector<int> v2)
{
    vector<int> ret;
    for (vector<int>::iterator it1 = v1.begin(); it1 != v1.end(); it1++)
    {
        for (vector<int>::iterator it2 = v2.begin(); it2 != v2.end(); it2++)
        {
            if (*it1 == *it2)
            {
                bool isDuplicate = false;
                for (vector<int>::iterator it3 = ret.begin(); it3 != ret.end(); it3++)
                {
                    if (*it1 == *it3)
                    {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate)
                {
                    ret.push_back(*it1);
                }
            }
        }
    }
    return ret;
}
