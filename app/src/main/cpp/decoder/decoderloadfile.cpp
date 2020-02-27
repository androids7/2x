//
// Created by Administrator on 2020\2\26 0026.
//

#include <cstdlib>
#include <cstring>
#include "decoderloadfile.h"
#include "../utils.h"
#include "test.h"
#include "../androidlog.h"
#include <jni.h>

#include "convert.h"

 int * getPixelsData(JNIEnv *env, jobject  obj, jstring data) {
/*
     const char *str=env->GetStringUTFChars(data,0);
     char *a=(char*)malloc(10000);
     strcpy(a,str);
LOGD("aaaa",a);
     LOGD("len",strlen(a));
env->ReleaseStringUTFChars(data,str);
    return 0;
    //getPixels(str);
*
 */

char *a=jstring2char(env,data);

     return  (int*)getPixels(a);

}

