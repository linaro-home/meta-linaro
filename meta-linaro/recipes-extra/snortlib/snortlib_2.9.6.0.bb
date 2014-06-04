DESCRIPTION = "snort - a free lightweight network intrusion detection system for UNIX and Windows."
HOMEPAGE = "http://www.snort.org/"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=78fa8ef966b48fbf9095e13cc92377c5"

DEPENDS = "libpcap libpcre daq libdnet"

S = "${WORKDIR}/snort-2.9.6.0"

SRC_URI = " ${GENTOO_MIRROR}/snort-2.9.6.0.tar.gz;name=tarball \
            file://snort.init \
            file://disable-inaddr-none.patch \
            file://disable-dap-address-space-id.patch \
            file://0001-libpcap-search-sysroot-for-headers.patch \
"

SRC_URI[tarball.md5sum] = "18111f6de3989ca89add36077a7c2659"
SRC_URI[tarball.sha256sum] = "3cc6c8a9b52f4c863a5736a73b4012aff340b50b5e002771b04d4877f47cd19e"

inherit autotools  gettext  update-rc.d

INITSCRIPT_NAME = "snort"
INITSCRIPT_PARAMS = "defaults"

EXTRA_OECONF = " \
	--enable-gre \
	--enable-linux-smp-stats \
	--enable-reload \
	--enable-reload-error-restart \
	--enable-targetbased \
	--disable-static-daq \
	--enable-so-with-static-lib \
	--with-gnu-ld \
	--with-dnet-includes=${STAGING_INCDIR} \
	--with-dnet-libraries=${STAGING_LIBDIR} \
	--with-daq-includes=${STAGING_INCDIR} \
	--with-daq-libraries=${STAGING_LIBDIR} \
	"
do_compile_prepend() {
	mkdir -p ${D}/${includedir}
	cp -a ${WORKDIR}/snort-2.9.6.0 ${D}/${includedir}

}

do_install() {
	mkdir -p ${D}/${libdir}
	cp src/libsnort.a ${D}/${libdir}

	mkdir -p ${D}/${includedir}
	cp -a ${WORKDIR}/snort-2.9.6.0 ${D}/${includedir}
	cp config.h ${D}/${includedir}/snort-2.9.6.0/
}
