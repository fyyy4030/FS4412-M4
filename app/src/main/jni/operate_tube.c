//
// Created by jiyangkang on 2016/5/22 0022.
//


#include "operate.h"


void set_tube(int operate){
    char buf[8];

    int fd = open(TUBE_FILE, O_RDWR);
    if(fd < 0){
        LOGI("ERROR OPEN: %s", TUBE_FILE);
        return;
    }
    int i;
    bzero(buf, sizeof(buf));
    for( i = 0; i < 8; i++){
        char get = (char)(((operate >> (i * 4)) & 0x0f));

        if (get ==0x0f){
            get =' ';
        } else {
            get = get + '0';
        }

        buf[7 - i] = get;
    }

    ioctl(fd, SET_VAL, buf);

    close(fd);
}
