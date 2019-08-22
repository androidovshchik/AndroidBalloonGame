cmake_minimum_required(VERSION 3.4.1)

include_directories(/home/vlad/skia)

add_library(main SHARED main.cpp)

add_library(libviewer SHARED IMPORTED)
set_target_properties(libviewer PROPERTIES IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libviewer.so)

target_link_libraries(main libviewer -ljnigraphics)
