#include <string.h>
#include <jni.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "input.h"
#include "uinput.h"
#include "vektor.h"

static int fd;
static struct uinput_user_dev dev;

jint Java_com_vektor_amapper_util_InputDeviceManager_CreateVirtualDevice(
		JNIEnv* env, jobject thiz, jstring param) {
	fd = open("/dev/uinput", O_WRONLY | O_NONBLOCK);
	if (fd < 0)
		return -1;
	if (ioctl(fd, UI_SET_EVBIT, EV_KEY)<0)
		return -2;
	if (ioctl(fd, UI_SET_EVBIT, EV_REL)<0)
		return -3;
	if (ioctl(fd, UI_SET_EVBIT, EV_ABS)<0)
		return -4;
	if (ioctl(fd, UI_SET_EVBIT, EV_SYN)<0)
		return -5;
	memset(&dev, 0, sizeof(dev));
	const char *cparam = (*env)->GetStringUTFChars(env,param, 0);
	snprintf(dev.name, UINPUT_MAX_NAME_SIZE, cparam);
	(*env)->ReleaseStringUTFChars(env,param, cparam);
	dev.id.bustype = BUS_USB;
	dev.id.vendor = 0x1;
	dev.id.product = 0x1;
	dev.id.version = 1;
	if (write(fd, &dev, sizeof(dev)) < 0)
		return -6;

	if (ioctl(fd, UI_DEV_CREATE) < 0)
		return -7;

	return 0;

}

jint Java_com_vektor_amapper_util_InputDeviceManager_DestroyVirtualDevice(JNIEnv* env, jobject thiz){
	if(ioctl(fd, UI_DEV_DESTROY)<0) return -1;
	close(fd);
	return 0;
}

