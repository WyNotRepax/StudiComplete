//
// Created by benno on 22.07.2020.
//

#ifndef JSONPARSE_JSONOBJECT_H
#define JSONPARSE_JSONOBJECT_H


#include <string>
#include <vector>
#include "JsonValue.h"
#include "JsonProperty.h"

class JsonObject : public JsonValue {
public:
    JsonObject(JsonValue *parent):JsonValue(parent){};

    std::vector<std::string> keys() const;
    void appendProperty(JsonProperty jsonProperty);
    void appendProperty(std::string key, JsonValue* value);
private:
    std::vector<JsonProperty> properties;
};


#endif //JSONPARSE_JSONOBJECT_H
