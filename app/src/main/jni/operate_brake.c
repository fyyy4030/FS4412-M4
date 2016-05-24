//
// Created by jiyangkang on 2016/5/9 0009.
//

#include "operate.h"

int read_brake_state(){
    int fd;
    int state;
    int data;

    fd = open(BRAKE_FILE, O_RDWR);

    if(fd < 0){
        LOGI("Open Error : %s", "Brake");
        return -1;
    }

    state = read(fd, &data, 1);
    close(fd);

    return state;
}
