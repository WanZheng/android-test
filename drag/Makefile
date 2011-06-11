all:	run

bin/drag-debug.apk:
	ant debug

install:	bin/drag-debug.apk
	adb install -r $<

run:	install
	adb shell am start -n drag.cos/.drag
