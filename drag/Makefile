all:	log

bin/drag-debug.apk:	force
	ant debug

install:	bin/drag-debug.apk
	adb install -r $<

run:	install
	adb shell am start -n drag.cos/.drag

log:	run
	adb logcat -s cos:*

force:
