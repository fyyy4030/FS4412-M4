//
// Created by jiyangkang on 2016/5/19 0019.
//

#include "operate.h"

int fd_steeper = -1;

void start_steeper(int speed){
    int delay = 0;
    if(fd_steeper == -1){
        fd_steeper = open(STEEPER_FILE, O_RDWR);
        if(fd_steeper < 0){
            LOGI("ERROR OPEN:%s", "STEEPER");
            return;
        }
    }

    delay = (10-speed) * 1000;
    //LOGI("%d", delay);
    ioctl(fd_steeper, STEEPER_ON, 1);
    ioctl(fd_steeper, STEEPER_ON, 2);
    ioctl(fd_steeper, STEEPER_OFF, 3);
    ioctl(fd_steeper, STEEPER_OFF, 4);
    usleep(delay);
    ioctl(fd_steeper, STEEPER_ON, 3);
    ioctl(fd_steeper, STEEPER_OFF, 1);
    usleep(delay);
    ioctl(fd_steeper, STEEPER_ON, 4);
    ioctl(fd_steeper, STEEPER_OFF, 2);
    usleep(delay);
    ioctl(fd_steeper, STEEPER_ON, 1);
    ioctl(fd_steeper, STEEPER_OFF, 3);
    usleep(delay);
}

void close_steeper(){

    if(fd_steeper != -1){
        ioctl(fd_steeper, STEEPER_OFF, 1);
        ioctl(fd_steeper, STEEPER_OFF, 2);
        ioctl(fd_steeper, STEEPER_OFF, 3);
        ioctl(fd_steeper, STEEPER_OFF, 4);
        close(fd_steeper);
        fd_steeper = -1;
    }
}
