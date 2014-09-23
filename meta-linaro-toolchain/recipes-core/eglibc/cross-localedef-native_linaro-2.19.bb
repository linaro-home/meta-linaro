SUMMARY = "Cross locale generation tool for eglibc"
HOMEPAGE = "http://www.eglibc.org/home"
SECTION = "libs"
LICENSE = "LGPL-2.1"

LIC_DIR = "${WORKDIR}/eglibc-${PV}/libc"
LIC_FILES_CHKSUM = "file://${LIC_DIR}/LICENSES;md5=e9a558e243b36d3209f380deb394b213 \
      file://${LIC_DIR}/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
      file://${LIC_DIR}/posix/rxspencer/COPYRIGHT;md5=dc5485bb394a13b2332ec1c785f5d83a \
      file://${LIC_DIR}/COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"


inherit native
inherit autotools

FILESEXTRAPATHS =. "${FILE_DIRNAME}/${P}:"

MMYY = "14.08"
RELEASE = "20${MMYY}"
PR = "r${RELEASE}"

SRC_URI = "http://cbuild.validation.linaro.org/snapshots/eglibc-${PV}-${RELEASE}.tar.bz2 \
          "

SRC_URI[md5sum] = "f4f66db3745273367d9f2901d449e3f4"
SRC_URI[sha256sum] = "d792b859c356d99a49b94042cb7aa71758b840173d85286d5e66d994ce036384"

S = "${WORKDIR}/eglibc-${PV}/localedef"

do_unpack_append() {
    bb.build.exec_func('do_move_ports', d)
}

do_move_ports() {
        if test -d ${WORKDIR}/eglibc-${PV}/ports ; then
	    rm -rf ${WORKDIR}/libc/ports
	    mv ${WORKDIR}/eglibc-${PV}/ports ${WORKDIR}/libc/
	fi
}

EXTRA_OECONF = "--with-glibc=${WORKDIR}/eglibc-${PV}/libc"
CFLAGS += "-DNOT_IN_libc=1"

do_configure () {
	${S}/configure ${EXTRA_OECONF}
}


do_install() {
	install -d ${D}${bindir} 
	install -m 0755 ${B}/localedef ${D}${bindir}/cross-localedef
}
