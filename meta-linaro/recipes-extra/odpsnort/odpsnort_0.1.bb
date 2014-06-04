DESCRIPTION = "Library for parsing configuration files."
HOMEPAGE = "http://www.nongnu.org/confuse/"
SECTION = "libs"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=1d4b0366557951c84a94fabe3529f867"

DEPENDS = "odp daq snortlib libdnet"

PR = "r3"

SRC_URI = "git://git.linaro.org/lng/odp-apps.git \
	   file://LICENSE"
SRCREV = "111dbe65221f53f2cf7cb2d9a3e02ff884cf2d3a"

do_configure() {
}

inherit autotools

do_compile() {
	cd ${WORKDIR}/git/snort/odpsnort
	oe_runmake ODP_LIB=${STAGING_LIBDIR}/libodp.a \
		SNORT=${STAGING_DIR_TARGET}${includedir}/snort-2.9.6.0 \
		LIBSNORT=${STAGING_DIR_TARGET}${libdir}/libsnort.a \
		DAQ=${STAGING_DIR_TARGET}${includedir}/daq-2.0.2 \
		DAQ_LIB="${STAGING_DIR_TARGET}${libdir}/libdaq.a ${STAGING_DIR_TARGET}${libdir}/libdaq_static_modules.a \
		${STAGING_DIR_TARGET}${libdir}/libdaq_static.a ${STAGING_DIR_TARGET}${libdir}/libsfbpf.a" \
		LIBDNET=${STAGING_DIR_TARGET}${libdir}/libdnet.a
}

do_install() {
	mkdir -p ${D}${bindir}
	cp ${WORKDIR}/git/snort/odpsnort/odp-snort ${D}${bindir}
}
