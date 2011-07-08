all:	log

PACKAGE=com.example.android.apis
ACTIVITY=ApiDemos
TAG=icon_loader
APK=ApiDemos-debug.apk
DEVICE=emulator-5554
#DEVICE=Blade-CM7-Harley

ADB=adb -s ${DEVICE}
TAGS=${TAG}:* *:e

bin/${APK}:	force
	ant debug

install:	bin/${APK}
	${ADB} install -r $<

run:	install
	${ADB} logcat -c # clear the logs
	${ADB} shell am start -n ${PACKAGE}/.${ACTIVITY}

log:	run
	${ADB} logcat -s ${TAGS}

force:
