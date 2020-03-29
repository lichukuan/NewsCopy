LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := javaUtil
LOCAL_SRC_FILES := javaUtil.cpp

include $(BUILD_SHARED_LIBRARY)