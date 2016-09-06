LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS        := -lm -llog
LOCAL_MODULE        := operate
LOCAL_SRC_FILES     := operate.c operate_adc.c operate_brake.c operate_zlg.c
LOCAL_SRC_FILES     += operate_dc.c operate_steeper.c
LOCAL_SRC_FILES     += operate_buzzer.c operate_relay.c operate_tube.c
LOCAL_SRC_FILES     += operate_rfid.c operate_servo.c operate_temp.c

include $(BUILD_SHARED_LIBRARY)
