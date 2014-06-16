DESCRIPTION = "OpenDataPlane (ODP) provides a data plane application programming \
	environment that is easy to use, high performance, and portable between networking SoCs."
HOMEPAGE = "http://www.opendataplane.org"
SECTION = "console/network"

LICENSE = "BSD | GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ccfa994aa96974cfcd39a59faee20a2"
PV = "20140616+git${SRCPV}"

SRC_URI = "git://git.linaro.org/lng/odp.git;name=odp"

SRCREV_odp = "7a4d8abacd7535c4caa8913a5f4a6d295973b2b8"
SRCREV_FORMAT = "odp"

S = "${WORKDIR}/git"

CFLAGS += "-fPIC"

PARALLEL_MAKE = ""
EXTRA_OEMAKE = "CC='${CC}'"

do_install() {
	oe_runmake tests_install DESTDIR=${D}${prefix}
}

# ODP
# ODP primary shipped as static library plus some API test and samples/
# Current makefile installs it to /usr/share/odp so use this folder here.
FILES_${PN}-dbg += "${datadir}/odp/.debug"

