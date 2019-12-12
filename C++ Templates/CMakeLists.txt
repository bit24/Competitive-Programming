cmake_minimum_required(VERSION 3.10)
project(QuickBuild)

set(CMAKE_CXX_STANDARD 11)

add_executable(QuickBuild )

set(CMAKE_CXX_FLAGS "-Wall -Wextra -Wno-sign-compare -Wshadow -Wfloat-equal -Wconversion -Wlogical-op -Wshift-overflow=2")
set(CMAKE_CXX_FLAGS "-D_GLIBCXX_DEBUG -D_GLIBCXX_DEBUG_PEDANTIC")
set(CMAKE_CXX_FLAGS "-fsanitize=address -fsanitize=undefined")
