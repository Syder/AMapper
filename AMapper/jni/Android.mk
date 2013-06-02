LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog
LOCAL_MODULE    := EventInjector
LOCAL_SRC_FILES := EventInjector.c
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
 
# Here we give our module name and source file(s)
LOCAL_MODULE    := Vektor
LOCAL_SRC_FILES := vektor.c
 
include $(BUILD_SHARED_LIBRARY)