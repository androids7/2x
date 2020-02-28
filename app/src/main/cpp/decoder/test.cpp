//
// Created by Administrator on 2020\2\26 0026.
//

#include "../../../../src/svgren/render.hpp"

#include <chrono>

#include <utki/debug.hpp>
#include <utki/config.hpp>

#include <papki/FSFile.hpp>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "../png.h"
#include "../androidlog.h"

#include <iostream>
#include <svgdom/dom.hpp>
#include "../global.h"
void write_png(const char* filename, int width, int height, std::uint32_t *buffer){
    FILE *fp = NULL;
    png_structp png_ptr = NULL;
    png_infop info_ptr = NULL;

    // Open file for writing (binary mode)
    fp = fopen(filename, "w+b");
    if (fp == NULL) {
        fprintf(stderr, "Could not open file %s for writing\n", filename);
        std::stringstream ss;
        ss << "could not open file '" << filename << "' for writing";
        throw std::system_error(errno, std::generic_category(), ss.str());
    }

    // Initialize write structure
    png_ptr = png_create_write_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);
    if (png_ptr == NULL) {
        fprintf(stderr, "Could not allocate write struct\n");
        throw std::runtime_error("Could not allocate write struct");
    }

    // Initialize info structure
    info_ptr = png_create_info_struct(png_ptr);
    if (info_ptr == NULL) {
        fprintf(stderr, "Could not allocate info struct\n");
        throw std::runtime_error("Could not allocate info struct");
    }

    png_init_io(png_ptr, fp);

    // Write header (8 bit colour depth)
    png_set_IHDR(png_ptr, info_ptr, width, height,
                 8, PNG_COLOR_TYPE_RGBA, PNG_INTERLACE_NONE,
                 PNG_COMPRESSION_TYPE_BASE, PNG_FILTER_TYPE_BASE);


    png_write_info(png_ptr, info_ptr);

    // Write image data
    int y;
    auto p = buffer;
    for (y=0 ; y<height ; y++, p += width) {
        png_write_row(png_ptr, reinterpret_cast<png_bytep>(p));
    }

    // End write
    png_write_end(png_ptr, NULL);

    if (fp != NULL) fclose(fp);
    if (info_ptr != NULL) png_free_data(png_ptr, info_ptr, PNG_FREE_ALL, -1);
    if (png_ptr != NULL) png_destroy_write_struct(&png_ptr, (png_infopp)NULL);
}

namespace{
    std::uint32_t getTicks(){
        return std::uint32_t(std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now().time_since_epoch()).count());
    }
}

std::vector <long long> strcache;

std::vector <long long>  *getPixels(const char * data){


#ifdef DEBUG


    std::string filename="/sdcard/.cc/tiger.svg";


     char dfile[] = "/sdcard/.cc/testapp.txt";



     // 将文件指针与标准输出流绑定

     FILE *fd = freopen(dfile, "w", stdout);


#endif

    auto loadStart = getTicks();
    std::string datastr=data;

     auto dom= svgdom::load(datastr);
    char *cachestr=(char*)malloc(100);
    sprintf(cachestr,"%f  sec",float(getTicks() - loadStart) / 1000.0f);
    auto renderStart = getTicks();

    auto img = svgren::render(*dom);
    write_png("/sdcard/.cc/1.png", img.width, img.height,&*img.pixels.begin());


    // Write image data
    int y=0;
    auto p = &*img.pixels.begin();
    //unsigned char *res=(unsigned char*)malloc(img.height*img.width);

    int s=img.height*img.width;
   //  char * r=(char*)malloc((s*sizeof( int)+10*2));
    //for (y=0 ; y<img.height ; y++, p += img.width) {

      // LOGD("",std::to_string((char)r[y]).c_str());
    // }

    //cache str
    char *ca=(char*)malloc((10*2));

printf("uint32_t size: %d\n",sizeof(uint32_t));
for(std::vector<uint32_t>::iterator it=img.pixels.begin();it!=img.pixels.end();it++,y++) {

    //sprintf(ca, "%u",* it);
    strcache.push_back(*it);
  // std::cout <<  ca<< std::endl;
  //  std::cout <<  strcache[y]<< std::endl;
}
    // std::cout <<  r<< std::endl;
   // y++;

   LOGD("char_size","");

#ifdef DEBUG

fflush(stdout);
//fclose(fd);

#endif




return  &strcache;
    //&*img.pixels.begin();

}
