//
// Created by jiyangkang on 2016/5/20 0020.
//

#include "operate.h"

int fd_relay = -1;
int gpio_i = 1;

#ifndef FS210
void write_relay(int num){
    if(fd_relay == -1){
        fd_relay = open(BUZZER_FILE, O_RDWR);
        if(fd_relay < 0){
            LOGI("ERROR OPEN : %s", BUZZER_FILE);
            return;
        }
    }

    switch(num){
        case 0:
            ioctl(fd_relay, BUZZER_OFF, &gpio_i);
        break;
        case 1:
            ioctl(fd_relay, BUZZER_ON, &gpio_i);
        break;
        default:
            close_relay();
        break;
    }
    close(fd_relay);
    fd_relay = -1;
}

void close_relay(){
    if(fd_relay != -1){
        close(fd_relay);
    }
}

#else
void write_relay(int num){
    if(fd_relay == -1){
        fd_relay = open(RELAY_PATH, O_RDWR);
        if(fd_relay < 0){
            LOGI("ERROR OPEN : %s", RELAY_PATH);
            return;
        }
    }

    switch(num){
        case 0:
            ioctl(fd_relay, RELAY_ON, 1);
        break;
        case 1:
            ioctl(fd_relay, RELAY_OFF, 1);
        break;
        default:
            close_relay();
        break;
    }
}
void close_relay(){
    if(fd_relay != -1){
        close(fd_relay);
    }
}
#endif
