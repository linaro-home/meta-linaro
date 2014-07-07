FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# ODP patches for libpcap maintained in odp-apps.git:
# https://git.linaro.org/lng/odp-apps.git/tree/HEAD:/libpcap/patches

SRC_URI += "file://0001-patch-libpcap-for-odp.patch \
	file://0002-use-ODP_PKTIO_TYPE_SOCKET_BASIC.patch \
	file://0003-ODP-API-Updates.patch"

DEPENDS += "odp"

EXTRA_OECONF +="--with-odp-includes=${STAGING_INCLUDEDIR} --with-odp-libraries=${STAGING_LIBDIR}/libodp.a"
