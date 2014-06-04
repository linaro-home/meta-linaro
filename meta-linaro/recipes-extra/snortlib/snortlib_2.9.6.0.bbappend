PR = "r1"
SRC_URI += "file://0001-add-dependancy-for-lrt.patch;striplevel=2 \
	   file://0001-Compile-Snort-as-static-library.patch \
	   file://0002-remove-static-from-needed-functions.patch "
LDFLAGS += "-lrt"
