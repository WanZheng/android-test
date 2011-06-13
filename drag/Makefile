all:	log

PACKAGE=drag.cos
ACTIVITY=DragActivity
TAG=drag

bin/drag-debug.apk:	force
	ant debug

install:	bin/drag-debug.apk
	adb install -r $<

run:	install
	adb logcat -c # clear the logs
	adb shell am start -n ${PACKAGE}/.${ACTIVITY}

log:	run
	adb logcat -s ${TAG}:*

force:
