require linaro-image-common.inc

IMAGE_INSTALL += " \
    arndale-pre-boot \
    bridge-utils \
    calibrator \
    curl \
    cronie \
    fping \
    git \
    lmbench \
    lng-network-config \
    ltp \
    netperf \
    odp \
    openssh-sftp-server \
    openvswitch \
    packagegroup-core-buildessential \
    procps \
    python-numpy \
    qemu \
    rt-tests \
    trace-cmd \
    tunctl \
    usecpu \
    "

IMAGE_INSTALL_append_armv7a = " \
    latency-test \
    systemtap \
    valgrind \
    trinity-example \
    "

IMAGE_INSTALL_append_aarch64 = " \
    trinity-example \
    "

IMAGE_INSTALL_append_qemux86 = " \
    "

IMAGE_FEATURES += "\
    dev-pkgs \
    staticdev-pkgs \
    tools-debug \
    tools-sdk \
    "
IMAGE_FSTYPES_append_qemux86 += "cpio.gz"
IMAGE_FSTYPES_lng-x86-64 = "tar.gz cpio.gz"
IMAGE_FSTYPES_lng-rt-x86-64 = "tar.gz cpio.gz"

EXTRA_IMAGE_FEATURES_append_qemux86 = " autoserial"
EXTRA_IMAGE_FEATURES_append_qemux86-64 = " autoserial"
FEATURE_PACKAGES_autoserial = "auto-serial-console"

IMAGE_PREPROCESS_COMMAND_qemux86 += "qemux86_fixup;"
IMAGE_PREPROCESS_COMMAND_qemux86-64 += "qemux86_fixup;"

qemux86_fixup() {
        # Since we use autoserial, remove serial consoles
        # See sysvinit-inittab recipe
        sed -i '/2345:respawn:\/sbin\/getty/d' ${IMAGE_ROOTFS}/etc/inittab

        # Add a default network interface
        echo "auto eth0" >> ${IMAGE_ROOTFS}/etc/network/interfaces
        echo "iface eth0 inet dhcp" >> ${IMAGE_ROOTFS}/etc/network/interfaces

        # The hostname can be forced globally for all builds containing
        # base-files by using 'hostname_pn-base-files = "linaro"' in a
        # configuration file such as site.conf or local.conf -
        # but this override for the 'base-files' recipe variable
        # cannot be applied from within another recipe.
        # The 'do_mangle_hostname' function below seems to work for
        # everything except these qemu machines - but for some reason
        # the qemu machine images don't end up with the uniform hostname.
        # In any case, forcing the hostname here seems to work.

        echo "linaro" > ${IMAGE_ROOTFS}${sysconfdir}/hostname
}

# Force a uniform hostname so we get a predictable prompt string
# which is easy for LAVA to expect in scripted dialogs.  Doing this
# host name override here confines the change only to builds of this
# root filesystem image.  This seems to work for everything except
# qemu machines above.
#
IMAGE_PREPROCESS_COMMAND += "do_mangle_hostname;" 

do_mangle_hostname() {
    echo "linaro" > ${IMAGE_ROOTFS}${sysconfdir}/hostname
}
