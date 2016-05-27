//
// Created by jiyangkang on 2016/5/22 0022.
//

#include "operate.h"

int fd_servo = -1;
void trun_angle(int operate){
    if (fd_servo == -1){
        fd_servo = open(SERVO_FILE, O_RDWR);
        if(fd_servo < 0){
            LOGI("OPEN ERROR:%s", SERVO_FILE);
            return;
        }
    }

    if(operate >=0 && operate <= 180){
            ioctl(fd_servo, SET_ANGLE, operate);
        }

}

void close_servo(){
    if(fd_servo > 0){
        ioctl(fd_servo, SERVO_OFF, 1);
        close(fd_servo);
        fd_servo = -1;
    }
}
