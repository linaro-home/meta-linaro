SUMMARRY = "rt-app is a test application that starts multiple periodic threads in order to simulate a real-time periodic load. "

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.in;md5=e43fc16fccd8519fba405f0a0ff6e8a3"

PV = "0.1+0.2-alpha2"
SRCREV = "745599092cc229213cd5d5d0e2c221cf35932fef"
SRC_URI = "git://git.linaro.org/git-ro/people/vincent.guittot/rt-app.git;protocol=http"

S = "${WORKDIR}/git"

inherit autotools

