require linaro-image-common.inc

IMAGE_INSTALL += " \
    arndale-pre-boot \
    bridge-utils \
    calibrator \
    curl \
    fping \
    latency-test \
    lmbench \
    ltp \
    netperf \
    openvswitch \
    procps \
    qemu \
    rt-tests \
    trinity-example \
    tunctl \
    "

IMAGE_INSTALL_append_armv7a = " \
    systemtap \
    valgrind \
    "

IMAGE_INSTALL_append_genericarmv7a = "python-numpy"

IMAGE_FEATURES += "\
    dev-pkgs \
    staticdev-pkgs \
    tools-debug \
    tools-sdk \
    "
