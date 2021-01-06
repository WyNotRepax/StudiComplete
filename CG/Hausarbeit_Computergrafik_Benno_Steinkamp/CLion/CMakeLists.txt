cmake_minimum_required(VERSION 3.15)
project(CLion)


set(CMAKE_CXX_STANDARD 14)

file(GLOB_RECURSE SOURCE src/*.cpp )
add_executable(CLion ${SOURCE})
target_include_directories(CLion PUBLIC ../thirdparty/glew/include ../thirdparty/glfw/include ../thirdparty/freeimage/include ../thirdparty/assimp/include)
target_link_libraries(CLion
        opengl32
        ${PROJECT_SOURCE_DIR}/../thirdparty/freeimage/lib/FreeImage.lib
        ${PROJECT_SOURCE_DIR}/../thirdparty/assimp/lib/assimp-vc140-mt.lib
        ${PROJECT_SOURCE_DIR}/../thirdparty/glfw/lib/glfw3dll.lib
        ${PROJECT_SOURCE_DIR}/../thirdparty/glew/lib/glew32.lib
        )