#include "A1.h"

bool allDifferent(vector<int> v1, vector<int> v2){
    if(v1.empty() && v2.empty()){
        return false;
    }
    return sharedElements(v1,v2).empty();
}