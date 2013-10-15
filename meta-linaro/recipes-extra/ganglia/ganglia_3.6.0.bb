DESCRIPTION = "Ganglia is a scalable distributed monitoring \
system for high-performance computing systems such as \
clusters and Grids."
HOMEPAGE = "http://ganglia.sourceforge.net/"
SECTION = "console/utils"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://NEWS;md5=ff8c91481123c7d3be4e31fcac997747"
DEPENDS = "apr confuse pcre python"

SRC_URI = "\
    ${SOURCEFORGE_MIRROR}/${BPN}/${BPN}-${PV}.tar.gz \
    file://gmetad-example.conf \
    file://gmetad.init \
    file://gmond-host-example.conf \
    file://gmond-multicast-example.conf \
    file://gmond.init \
    "
SRC_URI[md5sum] = "05926bb18c22af508a3718a90b2e9a2c"
SRC_URI[sha256sum] = "89eae02e1a117040d60b3b561fe55f88d7f8cf41b94af1492969ef68e6797886"

inherit autotools pythonnative update-rc.d

# The ganglia autoconf setup doesn't include libmetrics in its
# AC_OUTPUT list -- it reconfigures libmetrics using its own rules.
# Unfortunately this means an OE autoreconf will not regenerate
# ltmain.sh (and others) in libmetrics and as such the build will
# fail.  We explicitly force regeneration of that directory.

do_configure_append() {
       autoreconf -fvi
       (cd libmetrics; autoreconf -fvi)
}

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    # Example configurations
    install -m 0644 ${WORKDIR}/gmetad-example.conf ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/gmond-host-example.conf ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/gmond-multicast-example.conf ${D}${sysconfdir}
    # gmond and gmetad configurations
    install -m 0644 ${WORKDIR}/gmetad-example.conf ${D}${sysconfdir}/gmetad.conf
    install -m 0644 ${WORKDIR}/gmond-host-example.conf ${D}${sysconfdir}/gmond.conf
    # Init scripts
    install -m 0755 ${WORKDIR}/gmetad.init ${D}${sysconfdir}/init.d/gmetad
    install -m 0755 ${WORKDIR}/gmond.init ${D}${sysconfdir}/init.d/gmond
    update-rc.d -r ${D} gmond start 66 2 3 4 5 . stop 66 0 1 6 .
    update-rc.d -r ${D} gmetad start 66 2 3 4 5 . stop 66 0 1 6 .
}

BBCLASSEXTEND = "native"

INITSCRIPT_PACKAGES = "${PN}-gmond ${PN}-gmetad"
INITSCRIPT_NAME_${PN}-gmond = "gmond"
INITSCRIPT_NAME_${PN}-gmetad = "gmetad"
INITSCRIPT_PARAMS_${PN}-gmond = "defaults 66"
INITSCRIPT_PARAMS_${PN}-gmetad = "defaults 66"
