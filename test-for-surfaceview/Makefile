all:	compile install run

run:
	adb shell am start -n com.cos.test_for_surfaceview/.test_for_surfaceview

install:
	adb install -r bin/test_for_surfaceview-debug.apk

compile:
	ant debug