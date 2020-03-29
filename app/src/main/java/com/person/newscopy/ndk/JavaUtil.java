package com.person.newscopy.ndk;

public class JavaUtil {

    public JavaUtil() {
        System.loadLibrary("javaUtil");
    }

    public native int add(int a, int b);
}
