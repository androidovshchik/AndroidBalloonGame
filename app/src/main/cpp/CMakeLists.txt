cmake_minimum_required(VERSION 2.8)

include_directories(/home/vlad/skia)

file(GLOB GAME_SRC "*.h" "*.cpp")

add_library(main SHARED ${GAME_SRC})

add_library(libviewer SHARED IMPORTED)
set_target_properties(libviewer PROPERTIES IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libviewer.so)

target_link_libraries(main libviewer -ljnigraphics)
