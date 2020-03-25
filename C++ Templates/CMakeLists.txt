cmake_minimum_required(VERSION 3.10)
project(QuickBuild)

set(CMAKE_CXX_STANDARD 11)

add_executable(QuickBuild treatment.cpp)

set(CMAKE_CXX_FLAGS "-Wreturn-type -Wall -Wextra -Wshadow -Wfloat-equal -Wconversion -Wlogical-op -Wshift-overflow=2 -Wduplicated-cond")

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wno-sign-conversion -Wno-sign-compare")

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O2")

# libstdc++ debug mode and sanitizer
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fvisibility=hidden -D_GLIBCXX_DEBUG -D_GLIBCXX_DEBUG_PEDANTIC")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fsanitize=address -fsanitize=undefined")
