//
// Created by jiyangkang on 2016/5/20 0020.
//
#include "operate.h"

int fd_gpio = -1;
int buzzer_type = 2;

void write_buzzer(int number){

    if(fd_gpio == -1){
        fd_gpio = open(BUZZER_FILE, O_RDWR);
        if(fd_gpio < 0){
            LOGI("OPEN ERROR:%s", BUZZER_FILE);
            usleep(1000);
            return;
        }
    }
    #ifdef FS210
    usleep(number);
    ioctl(fd_gpio, BUZZER_ON, 1);
    usleep(2000-number);
    ioctl(fd_gpio, BUZZER_OFF, 1);
    #else

    usleep(number);
    ioctl(fd_gpio, BUZZER_ON, &buzzer_type);
    usleep(number);
    ioctl(fd_gpio, BUZZER_OFF, &buzzer_type);
    //LOGI("Sleep: %d", number);
    #endif
}

void stop_buzzer(){
    if (fd_gpio = -1){
        usleep(10000);
    }else{

        usleep(10000);

        ioctl(fd_gpio, BUZZER_OFF, &buzzer_type);

        if (fd_gpio != -1){
            close(fd_gpio);
            fd_gpio = -1;
        }
    }
}
