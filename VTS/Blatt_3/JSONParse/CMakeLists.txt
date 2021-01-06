# cmake_minimum_required(VERSION <specify CMake version here>)
project(JSONParse)

set(CMAKE_CXX_STANDARD 11)

add_library(JSONParse library.cpp library.h JsonValue.h JsonObject.cpp JsonObject.h JsonProperty.h)