//
// Created by Administrator on 2020\2\24 0024.
//

#ifndef 吃掉内存2_X_MAIN_H
#define 吃掉内存2_X_MAIN_H

#include <jni.h>
#include <android/log.h>
class main {
public:
    static char* getSha1(JNIEnv *env);
    static jobject getApplication(JNIEnv *env);
    static void exitApplication(JNIEnv *env, jint flag);
    static int checkValidity(JNIEnv *env);

private:

};


#endif //吃掉内存2_X_MAIN_H
