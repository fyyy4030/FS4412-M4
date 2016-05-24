//
// Created by jiyangkang on 2016/5/9 0009.
//

#include "jni.h"
#include "operate.h"
#include "com_hqyj_dev_procedurem4_modules_Operations_NativieOperate.h"


//operate driver
JNIEXPORT jboolean JNICALL Java_com_hqyj_dev_procedurem4_modules_Operations_NativieOperate_writeModule
  (JNIEnv *env, jclass thiz, jint which, jint operate){
    switch(which){
        case DC_MOTOR:
            switch(operate){
                case 0:
                    stop_motor();
                  break;
                case 1:
                    start_right_motor();
                  break;
                case 2:
                    start_left_motor();
                  break;
                case 3:
                    close_motor();
                  break;
            }
         break;
        case STEEPER:
            if(operate == 0){
                close_steeper();
            }else{
                start_steeper(operate);
            }
        break;
        case BUZZER:
            if(operate == 0){
                stop_buzzer();
            }else{
                write_buzzer(operate);
            }
        break;
        case RELAY:

            write_relay(operate);

        break;

        case TUBE:
            set_tube(operate);
        break;

        case SERVO:
            if(operate >= 0){
                trun_angle(operate);
            } else{
                close_servo();
            }
        break;

    }

}



//read from driver
jintArray JNICALL Java_com_hqyj_dev_procedurem4_modules_Operations_NativieOperate_readModule
  (JNIEnv *env, jclass thiz, jint which){

    jintArray value = NULL;
    jint buf[32];

  switch(which){
      case ADC_ALCOHOL:
            memset(buf, 0, sizeof(buf));
            value = (*env)->NewIntArray(env, 1);
            int adc_alcohol = read_adc(which);
            buf[0] = adc_alcohol;
            (*env)->SetIntArrayRegion(env, value, 0, 1, buf);
        break;
      case ADC_LIGHT:
            memset(buf, 0, sizeof(buf));
            value = (*env)->NewIntArray(env, 1);
            int adc_light = read_adc(which);
            buf[0] = adc_light;
            (*env)->SetIntArrayRegion(env, value, 0, 1, buf);
        break;
      case ADC_SENSITIVE:
            memset(buf, 0, sizeof(buf));
            value = (*env)->NewIntArray(env, 1);
            int adc_sensitive = read_adc(which);
            buf[0] = adc_sensitive;
            (*env)->SetIntArrayRegion(env, value, 0, 1, buf);
        break;
      case ADC_GAS:
            memset(buf, 0, sizeof(buf));
            value = (*env)->NewIntArray(env, 1);
            int adc_gas = read_adc(which);
            buf[0] = adc_gas;
            (*env)->SetIntArrayRegion(env, value, 0, 1, buf);
        break;
      case MATRIX:
            memset(buf, 0, sizeof(buf));

            value = (*env)->NewIntArray(env, 2);
            int zlg_key[2];
            memset(zlg_key, 0, sizeof(zlg_key));
            read_zlg(zlg_key);
            buf[0] = zlg_key[0];
            buf[1] = zlg_key[1];
            (*env)->SetIntArrayRegion(env, value, 0, 2, buf);
        break;
      case COMPASS:
            memset(buf, 0, sizeof(buf));
            value = (*env)->NewIntArray(env, 1);
            int compass;
            compass = read_angle();
            buf[0] = compass;
            (*env)->SetIntArrayRegion(env, value, 0, 1, buf);
        break;
      case BRAKE:
            memset(buf, 0, sizeof(buf));
            value = (*env)->NewIntArray(env, 1);
            int brake;
            brake = read_brake_state();
            buf[0] = brake;
            (*env)->SetIntArrayRegion(env, value, 0, 1, buf);
        break;
      case RFID:
            memset(buf, 0, sizeof(buf));
            value = (*env)->NewIntArray(env, 1);
            int rfid;
            rfid = read_rfid(rfid);
            buf[0] = rfid;
            (*env)->SetIntArrayRegion(env, value, 0, 1, buf);
        break;

      default:
            memset(buf, 0, sizeof(buf));
            value = (*env)->NewIntArray(env, 1);
            buf[0] = 0xffffffff;
            (*env)->SetIntArrayRegion(env, value, 0, 1, buf);
        break;
    }

    return value;
  }
