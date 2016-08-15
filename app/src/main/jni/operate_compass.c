//
// Created by jiyangkang on 2016/5/18 0018.
//

#include "operate.h"
#include "math.h"

int read_angle(){
    int fd;
    struct hmc5883_data data;
    int value;

    double angle = 0;

    fd = open(COMPASS_FILE, O_RDWR);

    if(fd < 0){
        return fd;
    }

    memset(&data, 0, sizeof(data));

    ioctl(fd, GET_VAL, &data);

    angle = atan2((double)data.y, (double)data.x) * (180 / 3.14159265) + 180;
    LOGI("angle = %lf", angle);

    value = (int) angle;
    close(fd);

    return value;
}
