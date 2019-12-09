#include <jni.h>
#include <string>
extern "C"
JNIEXPORT jstring JNICALL
Java_com_person_newscopy_WelcomeActivity_decode(JNIEnv *env, jobject instance, jstring code_) {
    const char *code = env->GetStringUTFChars(code_, 0);

    // TODO

    env->ReleaseStringUTFChars(code_, code);

    return env->NewStringUTF(code);
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_person_newscopy_WelcomeActivity_encode(JNIEnv *env, jobject instance, jstring code_) {
    const char *code = env->GetStringUTFChars(code_, 0);

    // TODO

    env->ReleaseStringUTFChars(code_, code);

    return env->NewStringUTF(code);
}