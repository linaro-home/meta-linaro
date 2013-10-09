DEFAULTTUNE ??= "aarch64"

TUNEVALID[aarch64] = "Enable instructions for aarch64"
TUNEVALID[bigendian] = "Enable big-endian mode."
TUNECONFLICTS[aarch64] = ""
MACHINEOVERRIDES .= "${@bb.utils.contains("TUNE_FEATURES", "aarch64", ":aarch64", "" ,d)}"

AVAILTUNES += "aarch64 aarch64eb"

# Little Endian base configs
TUNE_FEATURES_tune-aarch64 = "aarch64"

# Big Endian
TUNE_FEATURES_tune-aarch64eb = "${TUNE_FEATURES_tune-aarch64} bigendian"

ARMPKGARCH = "${@bb.utils.contains("TUNE_FEATURES", "bigendian", "aarch64eb", "aarch64", d)}""
TUNE_ARCH = "${@bb.utils.contains("TUNE_FEATURES", "bigendian", "aarch64eb", "aarch64", d)}"
TUNE_PKGARCH = "${@bb.utils.contains("TUNE_FEATURES", "bigendian", "aarch64eb", "aarch64", d)}"
PACKAGE_EXTRA_ARCHS += "${@bb.utils.contains("TUNE_FEATURES", "bigendian", "aarch64eb", "aarch64", d)}"

# toolchain bits which were in conf/site.conf
# These do not belong here, they belong in the DISTRO config

GCCVERSION       ?= "linaro-4.7"
SDKGCCVERSION    ?= "linaro-4.7"
BINUVERSION      ?= "2.23.51%"
