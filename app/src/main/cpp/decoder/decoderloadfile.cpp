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
#include <string>
#include <vector>
#include "convert.h"
#include <iostream>

#include "../global.h"
using namespace std;

void SplitString(const string& s, vector<string>& v, const string& c);

jlongArray getPixelsData(JNIEnv *env, jobject  obj, jstring data) {




    char *b=jstring2char(env,data);
long a[15];
for(int i=0;i<15;i++){
    a[i]=1000+i;
}
auto allstr=getPixels(b);
//SplitString(res,allstr,",");
    int size= allstr->size();
printf("allstr size : %d\n",size);


    long long arr[size];

int i=0;
    char *str = new char[20];


    for(std::vector<long long>::iterator it=allstr->begin();it!=allstr->end();it++,i++) {

     //   strcpy(str, *it);
       arr[i]=*it;

#ifdef DEBUG

        std::cout <<  arr[i]<< std::endl;

#endif

    }
    allstr->clear();

#ifdef DEBUG

    fflush(stdout);

#endif


   // fclose(fd);

    return longlong2jlong(env,arr,size);
    //longlong2jlong(env,arr);

    //int2jint(env,getPixels(b));
    //;

}



 void SplitString(const string& s, vector<string>& v, const string& c)
 {
     string::size_type pos1, pos2;
     pos2 = s.find(c);
     pos1 = 0;
     while(string::npos != pos2)
     {
             v.push_back(s.substr(pos1, pos2-pos1));

         pos1 = pos2 + c.size();
         pos2 = s.find(c, pos1);
     }
     if(pos1 != s.length())
         v.push_back(s.substr(pos1));
 }




/*
 jbyteArray getPixelsData(JNIEnv *env, jobject  obj, jstring data) {

char *a=jstring2char(env,data);
char *s=getPixels(a);

     jbyteArray bytes = (env)->NewByteArray(strlen(s));
     //将char* 转换为byte数组
     LOGD(std::to_string(strlen(s)).c_str(),"len");
     (env)->SetByteArrayRegion(bytes, 0, strlen(s), (jbyte*) s);

     return  bytes;


}
 */

