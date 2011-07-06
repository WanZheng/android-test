all:	log

PACKAGE=icon_loader.com
ACTIVITY=icon_loader
TAG=loader
APK=icon_loader-debug.apk

bin/${APK}:	force
	ant debug

install:	bin/${APK}
	adb install -r $<

run:	install
	adb logcat -c # clear the logs
	adb shell am start -n ${PACKAGE}/.${ACTIVITY}

log:	run
	adb logcat -s ${TAG}:* *:e

force:
