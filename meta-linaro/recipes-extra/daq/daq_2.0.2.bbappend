PR = "r1"

DEPENDS += "odp"

SRC_URI_append  = " file://0001-implement-odp-daq-module.patch;md5=689064cefd46175a00f8d548974fb9e6"

do_install_prepend() {
        mkdir -p ${D}/${includedir}
        cp -a ${WORKDIR}/daq-2.0.2 ${D}/${includedir}
}

FILES_DAQ += "{includedir}/daq-2.0.2"
