SUMMARY = "A full-featured SSL VPN solution via tun device"
HOMEPAGE = "http://openvpn.sourceforge.net"
SECTION = "console/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=5aac200199fde47501876cba7263cb0c"
DEPENDS = "lzo openssl libpam"

inherit autotools

SRC_URI = "git://openvpn.git.sourceforge.net/gitroot/openvpn/openvpn.git \
           file://openvpn \
	   file://0001-openvpn-demo-implement-I-O-from-shared-memory.patch"

SRCREV = "cd6555e0159987ef264789f4976053ce2aa5fc20"

S = "${WORKDIR}/git"

SRC_URI[md5sum] = "c54758b4932576ceb9973f9399d95d88"
SRC_URI[sha256sum] = "d65f701546804920fe484644a42e6c6e2d08f7f420db23eb4ab6d4b324ab5851"

CFLAGS += "-fno-inline"

# I want openvpn to be able to read password from file (hrw)
EXTRA_OECONF += "--enable-password-save --disable-snappy"

do_install_append() {
    install -d ${D}/${sysconfdir}/init.d
    install -d ${D}/${sysconfdir}/openvpn
    install -m 755 ${WORKDIR}/openvpn ${D}/${sysconfdir}/init.d
}

RRECOMMENDS_${PN} = "kernel-module-tun"

FILES_${PN}-dbg += "${libdir}/openvpn/plugins/.debug"
