DESCRIPTION = "Trinity, a Linux System call fuzz tester."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=96094d47cfbd2cc45eb46ce0fc423c04"

SRC_URI = "http://codemonkey.org.uk/projects/trinity/trinity-1.2.tgz \
           file://0001-scripts-TRINITY_PATH-defaults-to.patch \
           file://0002-scripts-change-mkdir-tmp-to-mktemp.patch \
           file://0003-scripts-arch-dependent-to-get-the-syscall_list.patch \
          "

SRC_URI[sha256sum] = "71132fca0ed016dcb39a3f1d9fa16a68971e2e9eff0b94bbacdf3d7ff101b6d5"

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
