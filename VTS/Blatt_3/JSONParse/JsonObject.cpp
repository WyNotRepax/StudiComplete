//
// Created by benno on 22.07.2020.
//

#include "JsonObject.h"

std::vector<std::string> JsonObject::keys() const {
    std::vector<std::string> ret(properties.size());
    for(const auto & property : properties){
        ret.push_back(property.key);
    }
    return ret;
}

void JsonObject::appendProperty(JsonProperty jsonProperty) {
    jsonProperty.value->setParent()
}

void JsonObject::appendProperty(std::string key, JsonValue* value) {
    JsonProperty jsonProperty;
    jsonProperty.key = key;
    jsonProperty.value = value;

}

