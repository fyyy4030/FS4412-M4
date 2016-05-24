//
// Created by jiyangkang on 2016/5/22 0022.
//

#include "operate.h"
#include "stdint.h"

int read_rfid(){

    uint8_t card_data[32] = {0};
    int number_return = 0;

    int fd = open(RFID_FILE, O_RDWR);
    if (fd < 0){
        LOGI("ERROR OPEN : %s", RFID_FILE);
        return;
    }

    int nbyte = 0;
    int i =0;

    nbyte = read(fd, card_data, 4);

    if(nbyte != 4){
        LOGI("READ ERROR: %s", RFID_FILE);
    }



    for(i = 0; i < 4; i++){
        number_return = (number_return << 8) | card_data[i];
    }

    return number_return;
}
