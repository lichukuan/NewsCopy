//
// Created by 李楚宽 on 2020/2/26.
//
#include <jni.h>
#include "com_person_newscopy_ndk_JavaUtil.h"

JNIEXPORT jint JNICALL Java_com_person_newscopy_ndk_JavaUtil_add
        (JNIEnv *, jobject, jint a, jint b){
    return a+b;
}
