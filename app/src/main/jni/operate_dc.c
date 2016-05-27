//
// Created by jiyangkang on 2016/5/19 0019.
//

#include "operate.h"

int fd = -1;


void stop_motor(){
    if(fd == -1){
        fd = open(DC_MOTOR_FILE, O_RDWR);
        if(fd < 0){
            LOGI("OPEN ERROR： %s", DC_MOTOR_FILE);
            return;
        }
    }

    ioctl(fd, DC_DYNAMO_OFF, 1);
    ioctl(fd, DC_DYNAMO_OFF, 2);
}

void start_right_motor(){
    if(fd == -1){
        fd = open(DC_MOTOR_FILE, O_RDWR);
        if(fd < 0){
            LOGI("OPEN ERROR： %s", DC_MOTOR_FILE);
            return;
        }
    }

    ioctl(fd, DC_DYNAMO_ON, 1);
    ioctl(fd, DC_DYNAMO_OFF, 2);

    //LOGI("################send left");
}
void start_left_motor(){
    if(fd == -1){
        fd = open(DC_MOTOR_FILE, O_RDWR);
        if(fd < 0){
            LOGI("OPEN ERROR： %s", DC_MOTOR_FILE);
            return;
        }
    }

    ioctl(fd, DC_DYNAMO_OFF, 1);
    ioctl(fd, DC_DYNAMO_ON, 2);

    //LOGI("################send right");
}

void close_motor(){
    if(fd != -1){
        close(fd);
        fd = -1;
    }
}
