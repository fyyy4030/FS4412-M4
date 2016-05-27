//
// Created by jiyangkang on 2016/5/17 0017.
//

#include "operate.h"



void read_zlg(int value[2]){

    int fd = open(TUBE_FILE, O_RDWR);
    if(fd < 0){
        value = NULL;
    } else{
        int key = 0;
        ioctl(fd, GET_KEY, &key);
        value[0] = key;
        value[1] = key;
        //LOGI("value[0]: %d", value[0]);
        //LOGI("value[1]: %d", value[1]);
    }
    close(fd);
}
