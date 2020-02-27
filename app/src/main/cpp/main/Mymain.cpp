//
// Created by Administrator on 2020\2\24 0024.
//

#include "Mymain.h"
#include <string.h>

#include "../androidlog.h"

const char hexcode[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
class Mymain;
//签名信息
static const char *app_sha1 = "A37DAC3A758D8726A7BD3EAA2FF2D1C1973257CA";
static const char *app_debug_sha1 = "CE2CC8D0F3DC60D8143059F6641141E05F1B52F7";

char* Mymain::getSha1(JNIEnv *env){
    jobject context_object = getApplication(env);
    // 上下文对象
    jclass context_class = env -> GetObjectClass(context_object);
    // 反射获取PackageManager
    jmethodID methodId = env -> GetMethodID(context_class, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject package_manager = env -> CallObjectMethod(context_object, methodId);
    if (package_manager == NULL) {
        LOGD("package_manager is NULL!!!");
        return NULL;
    }
    // 反射获取包名
    methodId = env -> GetMethodID(context_class, "getPackageName", "()Ljava/lang/String;");
    jstring package_name = (jstring)env -> CallObjectMethod(context_object, methodId);
    if (package_name == NULL) {
        LOGD("package_name is NULL!!!");
        return NULL;
    }
    env -> DeleteLocalRef(context_class);
    // 获取 PackageInfo 对象
    jclass pack_manager_class = env -> GetObjectClass(package_manager);
    methodId = env -> GetMethodID(pack_manager_class, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    env -> DeleteLocalRef(pack_manager_class);
    jobject package_info = env -> CallObjectMethod(package_manager, methodId, package_name, 0x40);
    if (package_info == NULL) {
        LOGD("getPackageInfo() is NULL!!!");
        return NULL;
    }
    env -> DeleteLocalRef(package_manager);
    // 获取签名信息
    jclass package_info_class = env -> GetObjectClass(package_info);
    jfieldID fieldId = env -> GetFieldID(package_info_class, "signatures", "[Landroid/content/pm/Signature;");
    env -> DeleteLocalRef(package_info_class);
    jobjectArray signature_object_array = (jobjectArray)env -> GetObjectField(package_info, fieldId);
    if (signature_object_array == NULL) {
        LOGD("signature is NULL!!!");
        return NULL;
    }
    jobject signature_object = env -> GetObjectArrayElement(signature_object_array, 0);
    env -> DeleteLocalRef(package_info);
    // 签名信息转换成 sha1 值
    jclass signature_class = env -> GetObjectClass(signature_object);
    methodId = env -> GetMethodID(signature_class, "toByteArray", "()[B");
    env -> DeleteLocalRef(signature_class);
    jbyteArray signature_byte = (jbyteArray) env -> CallObjectMethod(signature_object, methodId);
    jclass byte_array_input_class = env -> FindClass("java/io/ByteArrayInputStream");
    methodId = env -> GetMethodID(byte_array_input_class,"<init>", "([B)V");
    jobject byte_array_input = env -> NewObject(byte_array_input_class,methodId,signature_byte);
    jclass certificate_factory_class = env -> FindClass("java/security/cert/CertificateFactory");
    methodId = env -> GetStaticMethodID(certificate_factory_class, "getInstance", "(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;");
    jstring x_509_jstring = env -> NewStringUTF("X.509");
    jobject cert_factory = env -> CallStaticObjectMethod(certificate_factory_class,methodId,x_509_jstring);
    methodId = env -> GetMethodID(certificate_factory_class, "generateCertificate", ("(Ljava/io/InputStream;)Ljava/security/cert/Certificate;"));
    jobject x509_cert = env -> CallObjectMethod(cert_factory, methodId,byte_array_input);
    env -> DeleteLocalRef(certificate_factory_class);
    jclass x509_cert_class = env -> GetObjectClass(x509_cert);
    methodId = env -> GetMethodID(x509_cert_class,"getEncoded", "()[B");
    jbyteArray cert_byte = (jbyteArray)env -> CallObjectMethod(x509_cert, methodId);
    env -> DeleteLocalRef(x509_cert_class);
    jclass message_digest_class = env -> FindClass("java/security/MessageDigest");
    methodId = env -> GetStaticMethodID(message_digest_class,"getInstance", "(Ljava/lang/String;)Ljava/security/MessageDigest;");
    jstring sha1_jstring = env -> NewStringUTF("SHA1");
    jobject sha1_digest = env -> CallStaticObjectMethod(message_digest_class, methodId, sha1_jstring);
    methodId = env -> GetMethodID(message_digest_class,"digest", "([B)[B");
    jbyteArray sha1_byte = (jbyteArray)env -> CallObjectMethod(sha1_digest, methodId, cert_byte);
    env -> DeleteLocalRef(message_digest_class);
    // 转换成char
    jsize array_size = env -> GetArrayLength(sha1_byte);
    jbyte* sha1 = env -> GetByteArrayElements(sha1_byte, NULL);
    char *hex_sha = new char[array_size * 2 + 1];
    for (int i = 0; i <array_size ; ++i) {
        hex_sha[2 * i] = hexcode[((unsigned char)sha1[i]) / 16];
        hex_sha[2 * i + 1] = hexcode[((unsigned char)sha1[i]) % 16];
    }
    hex_sha[array_size * 2] = '\0';
    LOGD("hex_sha %s ",hex_sha);
    return hex_sha;
}
// 获取 Context 上下文方法：
jobject Mymain::getApplication(JNIEnv *env) {
    jclass localClass = env -> FindClass("android/app/ActivityThread");
    if (localClass != NULL) {
        LOGI("class have find");
        jmethodID getApplication = env -> GetStaticMethodID(localClass, "currentApplication", "()Landroid/app/Application;");
        if (getApplication != NULL) {
            jobject application = env -> CallStaticObjectMethod(localClass, getApplication);
            return application;
        }
        return nullptr;
    }
    return NULL;
}
// 直接退出应用（java/lang/Systemz）
 void Mymain::exitApplication(JNIEnv *env, jint flag) {
    jclass temp_clazz = env -> FindClass("java/lang/System");
    // 从 classpath 路径下搜索 ClassMethod ，并返回该类的 Class 对象
    jmethodID mid_static_method = env -> GetStaticMethodID(temp_clazz, "exit", "(I)V");
    env -> CallStaticVoidMethod(temp_clazz, mid_static_method, flag);
    env -> DeleteLocalRef(temp_clazz);
}
/*
jboolean main::checkValidity(JNIEnv *env){
    char *sha1 = getSha1(env);
    // 比较签名
    return static_cast<jboolean>((strcmp(sha1, app_sha1) == 0 ||
                                  strcmp(sha1, app_debug_sha1) == 0));
}
 */

 int Mymain::checkValidity(JNIEnv *env){
    char *sha1 = getSha1(env);
    // 比较签名
    return ((strcmp(sha1, app_sha1) == 0 ||
                                 strcmp(sha1, app_debug_sha1) == 0));
}