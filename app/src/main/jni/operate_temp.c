//
// Created by Administrator on 2016/8/25.
//
#include "operate.h"
#define I2C_RETRIES 0x0701
#define I2C_TIMEOUT 0x0702
#define I2C_RDWR 0x0707

struct i2c_msg
{
	unsigned short addr;
	unsigned short flags;
#define I2C_M_TEN 0x0010
#define I2C_M_RD 0x0001
	unsigned short len;
	unsigned char *buf;
};
struct i2c_rdwr_ioctl_data
{
	struct i2c_msg *msgs;
	int nmsgs;
};

int read_temp(){
    int fd,ret;
	short temp_val = 0;
	struct i2c_rdwr_ioctl_data lm75_data;
	fd=open("/dev/i2c-1",O_RDWR);
    if(fd<0)
    {
		LOGI("open temp failed");
		return -1;
	}
    lm75_data.nmsgs=2;
    lm75_data.msgs=(struct i2c_msg*)malloc(lm75_data.nmsgs*sizeof(struct i2c_msg));
    if(!lm75_data.msgs)
    {
    	LOGI("lm75_data.msgs = false");
    	return -1;
    }
    ioctl(fd,I2C_TIMEOUT,1);/*超时时间*/
    ioctl(fd,I2C_RETRIES,2);/*重复次数*/
    sleep(1);
    lm75_data.nmsgs=2;
    (lm75_data.msgs[0]).len=1; //lm75 目标数据的地址
    (lm75_data.msgs[0]).addr=0x4f; // lm75 设备地址
    (lm75_data.msgs[0]).flags=0;//write
    (lm75_data.msgs[0]).buf=(unsigned char*)malloc(2);
    (lm75_data.msgs[0]).buf[0]=0x0;//lm75数据地址
    (lm75_data.msgs[1]).len=2;//读出的数据
    (lm75_data.msgs[1]).addr=0x4f;// lm75 设备地址
    (lm75_data.msgs[1]).flags=I2C_M_RD;//read
    (lm75_data.msgs[1]).buf=(unsigned char*)malloc(2);//存放返回值的地址。
    (lm75_data.msgs[1]).buf[0]=0;//初始化读缓冲
    (lm75_data.msgs[1]).buf[1]=0;//初始化读缓冲
    ret=ioctl(fd,I2C_RDWR,(unsigned long)&lm75_data);
    if(ret<0)
    {
    	LOGI("ret open failed");
    	return -1;
    }
    temp_val = (lm75_data.msgs[1]).buf[0] << 8 | (lm75_data.msgs[1]).buf[1];

	if(temp_val >> 15)
		temp_val = (~(temp_val - 0x80) >> 7);
	else
		temp_val = temp_val >> 7;
    LOGI("short %d\n",temp_val);
	close(fd);
	return (int)temp_val;
}
