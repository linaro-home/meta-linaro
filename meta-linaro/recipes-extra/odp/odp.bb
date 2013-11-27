SUMMARY = "OpenDataPlane"
DESCRIPTION = "Open Data Plane"
HOMEPAGE = "https://wiki.linaro.org/LNG/Engineering/OpenDataPlane"
SECTION = "console/tools"

LICENSE = "BSD | GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dd8b05a36dd5410381ad0679f9151808"
SRCREV = "HEAD"
PV = "odp-${SRCREV}"
PR = "r3"

SRC_URI = "git://git.linaro.org/lng/odp.git"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CC=`echo ${CC} | cut -d " " -f 1`  LD=`echo ${LD} | cut -d " " -f 1` AR=`echo ${AR} | cut -d " " -f 1`"

do_compile() {
	git submodule update --init
	unset prefix
	oe_runmake dpdk
	#unset parallel make for openem!
	oe_runmake -j1 openem
	oe_runmake odp
}

do_install() {
	oe_runmake install DEST=${D}

	#copy dpdk files to image
	install -d ${D}/${libdir}/dpdk
	cp -a dpdk/generic_32-default-linuxapp-gcc-openem/lib/* ${D}/${libdir}/dpdk/
	install -d ${D}/${includedir}/dpdk
	cp -rL dpdk/generic_32-default-linuxapp-gcc-openem/include ${D}/${includedir}/dpdk/
	
	#copy openem
	install -d ${D}/${datadir}/openem
	find openem -type f -perm -o+rx  -exec cp {} ${D}/${datadir}/openem/ \;
}

# ODP
PACKAGES =+ "${PN}-example"
PACKAGES =+ "${PN}-example-dbg"
FILES_${PN}-example-dbg += "${datadir}/odp/.debug"
FILES_${PN}-example += "${datadir}/odp/odp_app"

# DPDK
PACKAGES =+ "${PN}-dpdk-staticdev"
FILES_${PN}-dpdk-staticdev += "${libdir}/dpdk"
FILES_${PN}-dpdk-staticdev += "${includedir}/dpdk"

# OPENEM
PACKAGES =+ "${PN}-openem-example"
PACKAGES =+ "${PN}-openem-example-dbg"
FILES_${PN}-openem-example-dbg += "${datadir}/openem/.debug"
FILES_${PN}-openem-example += "${datadir}/openem"

COMPATIBLE_HOST = "(i.86|x86_64|arm|aarch64).*-linux"

