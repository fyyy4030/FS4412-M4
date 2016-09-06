//
// Created by jiyangkang on 2016/5/9 0009.
//

#ifndef PROCEDUREM4_OPERATE_H
#define PROCEDUREM4_OPERATE_H
#endif //PROCEDUREM4_OPERATE_H

#include <android/log.h>
#include "string.h"

#define LOGTAG "JNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOGTAG, __VA_ARGS__)

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/ioctl.h>
#include <linux/types.h>
#include <errno.h>

//#define FS210
//ADC
#ifdef FS210
#define ADC_FILE            "/dev/adc_m4"
#else
#define ADC_FILE            "/dev/adc"
#endif
#define ADC_ALCOHOL         1
#define ADC_LIGHT           2
#define ADC_SENSITIVE       3//thermistor
#define ADC_GAS             4

#define ALCOHOL_CHANNEL     2
#define LIGHT_CHANNEL       1
#define SENSITIVE_CHANNEL   1
#define GAS_CHANNEL         0

#define SET_CHANNEL         _IO('A', 0)

int read_adc(int which);

//Brake
#define BRAKE_FILE          "/dev/light_E"
#define BRAKE               5
int read_brake_state();

//Buzzer
#define BUZZER              6
int fd_gpio;

#ifdef  FS210
#define BUZZER_FILE         "/dev/beep1"
#define BUZZER_ON           _IOW('B', 0, int)
#define BUZZER_OFF          _IOW('B', 1, int)
#else
#define BUZZER_FILE         "/dev/gpio"
#define BUZZER_ON           _IOW('L', 0, int)
#define BUZZER_OFF          _IOW('L', 1, int)
#endif

void write_buzzer(int HZ);
void stop_buzzer();

//Compass
#define COMPASS_FILE        "/dev/hmc5883"
#define COMPASS             7
int read_angle();
struct hmc5883_data{
    short x;
    short y;
    short z;
};
#define GET_VAL             _IOR('H',0,struct hmc5883_data)

//DCMotor
#define DC_MOTOR_FILE       "/dev/dynamo"
#define DC_MOTOR            8
#define DC_DYNAMO_ON        _IOW('D', 0, int)
#define DC_DYNAMO_OFF       _IOW('D', 1, int)
//#define DC_DYNAMO_RATE      _IOR('D', 2, int)
void stop_motor();
void start_right_motor();
void start_left_motor();
//void read_rate(int *rate);
void close_motor();

//Matrix
#define ZLG_NAME            "zlg7290"
#define MATRIX              9
#define GET_KEY             _IO('Z', 1)
void read_zlg(int value[2]);

//Relay
#define RELAY               10
void write_relay(int);
void close_relay();
#ifdef FS210
#define RELAY_PATH          "/dev/relay"
#define RELAY_ON            _IOW('R', 0, int)
#define RELAY_OFF           _IOW('R', 1, int)
#else
#endif

//RFID
#define RFID_FILE           "/dev/rfid"
#define RFID                11
#define SET_VAL_RFID        _IO('R', 0)
int read_rfid();

//SERVO
#define SERVO_FILE          "/dev/servo"
#define SERVO               12
#define SERVO_ON            _IOW('V', 0, int)
#define SERVO_OFF           _IOW('V', 1, int)
void turn_angle(int operate);
#define SET_ANGLE           _IO('K', 5)
void close_servo();

//Steeper
#define STEEPER_FILE        "/dev/stepper"
#define STEEPER             13
#define STEEPER_ON          _IOW('S', 0, int)
#define STEEPER_OFF         _IOW('S', 1, int)
void start_steeper(int speed);
void close_steeper();

//tube
#define TUBE_FILE           "/dev/zlg7290"
#define TUBE                14
#define SET_VAL             _IO('Z', 0)
void set_tube(int operate);

//temp
#define TEMP                15
#define TEMP_FILE           "/dev/i2c-1"
int read_temp();


