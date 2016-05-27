//
// Created by jiyangkang on 2016/5/9 0009.
//

#include "operate.h"

int read_adc(int which){
    int fd;
    int value;

    fd = open(ADC_FILE, O_RDWR);

    if (fd < 0){
        LOGI("Open ADC error");
        return fd;
    }

    switch(which){
        case ADC_ALCOHOL:
            ioctl(fd, SET_CHANNEL, ALCOHOL_CHANNEL);
            break;
        case ADC_LIGHT:
            ioctl(fd, SET_CHANNEL, LIGHT_CHANNEL);
            break;
        case ADC_SENSITIVE:
            ioctl(fd, SET_CHANNEL, SENSITIVE_CHANNEL);
            break;
        case ADC_GAS:
            ioctl(fd, SET_CHANNEL, GAS_CHANNEL);
            break;
        default:
            //LOGI("ERROR ADC");
            break;
    }

    read(fd, &value, sizeof(value));

    //LOGI("value : %d\n", value );

    close(fd);

    return value;

}
