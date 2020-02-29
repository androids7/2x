//
// Created by Peter1303 on 2019/12/31.
//

#include <jni.h>
#ifndef DIRT_CONVER_H
#define DIRT_CONVER_H

#endif //DIRT_CONVER_H

jstring char2jstring(JNIEnv* env, const char* pat) {
    //定义java String类 strClass
    jclass strClass = (env)->FindClass("Ljava/lang/String;");
    //获取String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String
    jmethodID ctorID = (env)->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    //建立byte数组
    jbyteArray bytes = (env)->NewByteArray(strlen(pat));
    //将char* 转换为byte数组
    (env)->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*) pat);
    // 设置String, 保存语言类型,用于byte数组转换至String时的参数
    jstring encoding = (env)->NewStringUTF("GB2312");
    //将byte数组转换为java String,并输出
    return (jstring) (env)->NewObject(strClass, ctorID, bytes, encoding);
}

char* jstring2char(JNIEnv* env, jstring jstr) {
    char* rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("GB2312");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char*) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

/**
 * 模拟处理一个数组，常用于对图片的处理
 */

//动态数组必须传入数组大小
 jintArray int2jintArray(JNIEnv * env, jint *array,int lengths){
     //jsize       (*GetArrayLength)(JNIEnv*, jarray);
    jint length =(jint)lengths;
    jintArray jarray= env->NewIntArray(length);
    env->SetIntArrayRegion(jarray,0,length,array);
    //jboolean iscopy;
    //jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);

    return jarray;

}
//获得数组的长度
template <class T>
int getStaticArrayLen(T& array)
{//使用模板定义一 个函数getArrayLen,该函数将返回数组array的长度
    return (sizeof(array) / sizeof(array[0]));
}
/*
jlongArray long2jlong(JNIEnv * env,  long *array){
    //jsize       (*GetArrayLength)(JNIEnv*, jarray);
    jint length = (jint) getArrayCount(array);


    jlongArray jarray= env->NewLongArray(length);
    env->SetLongArrayRegion(jarray,0,length,(jlong *)array);
    //jboolean iscopy;
    //jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);

    return jarray;

}
 */
jlongArray long2jlong(JNIEnv * env, long  *array,int lengths){
    //jsize       (*GetArrayLength)(JNIEnv*, jarray);
    jsize length = (jint) lengths;


    jlongArray jarray= env->NewLongArray(length);
    env->SetLongArrayRegion(jarray,0,length,(jlong *)array);
    //jboolean iscopy;
    //jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);

    return jarray;

}


jlongArray longlong2jlong(JNIEnv * env, long long *array,int lengths){
    //jsize       (*GetArrayLength)(JNIEnv*, jarray);
    jsize length = (jint) lengths;


    jlongArray jarray= env->NewLongArray(length);
    env->SetLongArrayRegion(jarray,0,length,(jlong *)array);
    //jboolean iscopy;
    //jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);

    return jarray;

}


