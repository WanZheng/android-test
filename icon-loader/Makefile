all:	log

PACKAGE=icon_loader.com
ACTIVITY=icon_loader
TAG=icon_loader
APK=icon_loader-debug.apk
#DEVICE=emulator-5554
#DEVICE=Blade-CM7-Harley
DEVICE=0A3AAA1E0200E01F

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
