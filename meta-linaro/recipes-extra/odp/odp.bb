DESCRIPTION = "OpenDataPlane (ODP) provides a data plane application programming \
	environment that is easy to use, high performance, and portable between networking SoCs."
HOMEPAGE = "https://wiki.linaro.org/LNG/Engineering/OpenDataPlane"
SECTION = "console/tools"

LICENSE = "BSD | GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ccfa994aa96974cfcd39a59faee20a2"
PV = "20140605+git${SRCPV}"

SRC_URI = "git://git.linaro.org/lng/odp.git;name=odp \
           file://0001-test-don-t-try-to-recurse-into-Makefile-it-s-not-a-d.patch \
          "

SRCREV_odp = "a65917065da23a970f10b591f317c362e50dc36b"
SRCREV_FORMAT = "odp"

S = "${WORKDIR}/git"

PARALLEL_MAKE = ""
EXTRA_OEMAKE = "CC='${CC}'"

do_install() {
	oe_runmake tests_install DESTDIR=${D}${prefix}
}

# ODP
# ODP primary shipped as static library plus some API test and samples/
# Current makefile installs it to /usr/share/odp so use this folder here.
FILES_${PN}-dbg += "${datadir}/odp/.debug"

