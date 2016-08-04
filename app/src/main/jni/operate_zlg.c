//
// Created by jiyangkang on 2016/5/17 0017.
//

#include "operate.h"
//#include "linux/input.h"

//char file_path[128];
//int find_zlg();
//struct input_event ev;
//int* result;


void read_zlg(int value[2]){

    //find_zlg();
    //int fd = open(file_path, O_RDONLY);
    int key = 0;
    int fd = open(TUBE_FILE, O_RDWR);
    if(fd < 0){
        value = NULL;
    } else{
        ioctl(fd, GET_KEY, &key);
        value[0] = key;
        value[1] = key;
        LOGI("value[0]: %d", value[0]);
        LOGI("value[1]: %d", value[1]);
    }
    close(fd);
}
/*
int find_zlg(){
    int fd;
    int i = 0;
    for(i = 0; i < 10; i++){
        char path[128];
        char buf[128];
        bzero(path, sizeof(path));
        sprintf(path, "/sys/class/input/input%d/name", i);

        if((fd = open(path, O_RDONLY)) < 0){
            continue;
        }
        int ret = 0;
        bzero(buf, sizeof(buf));
        ret = read(fd, buf, sizeof(buf));
        LOGI("buf = %s", buf);
        bzero(file_path, sizeof(file_path));
        if(strncmp(ZLG_NAME, buf, 7) == 0){
            sprintf(file_path, "/dev/input/event%d", i);
            LOGI("file_path ======= %s", file_path);
            close(fd);
            return 0;
        }else{
            close(fd);
            continue;
        }
        if(i == 9){
            close(fd);
            return -1;
        }
    }
    close(fd);
    return 0;
}

*/
