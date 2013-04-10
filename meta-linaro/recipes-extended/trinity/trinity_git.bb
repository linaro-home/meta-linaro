DESCRIPTION = "Trinity, a Linux System call fuzz tester."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=96094d47cfbd2cc45eb46ce0fc423c04"

SRCREV = "bdd0bdcaef753e8d340fc25fbc72a11be47639b0"
SRC_URI = "git://git.codemonkey.org.uk/trinity.git;protocol=git \
           file://0001-scripts-TRINITY_PATH-defaults-to.patch \
           file://0002-scripts-change-mkdir-tmp-to-mktemp.patch \
          "

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-example"
FILES_${PN} = "${bindir}/trinity"
FILES_${PN}-example = "${datadir}/${PN}"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --create-home \
                       --shell /bin/sh ${PN} "

COMPATIBLE_HOST = "(x86_64|arm|aarch64).*-linux*"

do_configure () {
    ./configure.sh
}

do_compile () {
    make
}

do_install () {
    make install DESTDIR=${D}/usr
    install -o ${PN} -d -m 0755 ${D}/${datadir}/${PN}
    install -o ${PN} -m 0755 ${S}/scripts/test-all-syscalls-parallel.sh      ${D}/${datadir}/${PN}
    install -o ${PN} -m 0755 ${S}/scripts/test-all-syscalls-sequentially.sh  ${D}/${datadir}/${PN}
    install -o ${PN} -m 0755 ${S}/scripts/test-multi.sh                      ${D}/${datadir}/${PN}
    install -o ${PN} -m 0755 ${S}/scripts/test-vm.sh                         ${D}/${datadir}/${PN}
}

do_install_append () {

    # $TRINITY_BIN -L | grep entrypoint | grep -v AVOID | awk '{ print $4 }' | sort -u
    # removing grep and change the awk print to make this to work with arm targets
    if [ "${TARGET_ARCH}" = "arm" ] || \
        [ "${TARGET_ARCH}" = "aarch64" ]; then
        sed -r -i 's:\| grep entrypoint::' ${D}/${datadir}/${PN}/test-*.sh
        sed -r -i 's:print \$4:print $2:' ${D}/${datadir}/${PN}/test-*.sh
    fi
}
