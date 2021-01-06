//
// Created by benno on 22.07.2020.
//

#ifndef JSONPARSE_JSONPROPERTY_H
#define JSONPARSE_JSONPROPERTY_H


#include <string>
#include "JsonValue.h"

class JsonProperty {
public:
    std::string key;
    JsonValue* value;
};


#endif //JSONPARSE_JSONPROPERTY_H
