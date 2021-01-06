//
// Created by benno on 22.07.2020.
//

#ifndef JSONPARSE_JSONVALUE_H
#define JSONPARSE_JSONVALUE_H


class JsonValue {
protected:
    explicit JsonValue(JsonValue* parent):parent(parent){};
    JsonValue* parent;
    virtual void setParent(JsonValue* parent) = 0;
public:
    virtual ~JsonValue() = 0;
};


#endif //JSONPARSE_JSONVALUE_H
