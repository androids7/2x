
cmake_minimum_required(VERSION 3.4.1)

#link_directories( "../../../jniLIbs/armeabi-v7a/ ")

file(GLOB_RECURSE srcs "*.cpp")

set(LD_LIBRARY_PATH  ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/)
include_directories( F:/Game/2x/svgren/build/ndkLibs/include\ )
link_directories(  ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/)




add_library( # Sets the name of the library.
        decoder-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        #native-lib.cpp
        #register.cpp
         ${srcs}
        ../main/Mymain.cpp
        )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log )



add_library(
        svgren
        SHARED
        IMPORTED
)
set_target_properties(
        svgren
        PROPERTIES IMPORTED_LOCATION
        #    ${CMAKE_SOURCE_DIR}/libs/arm64-v8a/libsvgren.so
        ../../../../libs/${ANDROID_ABI}/libsvgren.so
)


find_library( # Sets the name of the path variable.
        svgren-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        svgren )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

#include_directories( "build/ndkLibs/include/" )

target_link_libraries( # Specifies the target library.
        decoder-lib

        # Links the target library to the log library
        # included in the NDK.
      svgren
      ${log-lib}
        )