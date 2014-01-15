require linaro-image-lamp.bb

DESCRIPTION = "A Lamp-based image for Linaro Enterprise validation."

IMAGE_INSTALL += " \
    ganglia \
    git \
    grub \
    htop \
    links \
    sed \
    tmux \
    vim \
    x11vnc \
    xauth \
    xserver-xorg-xvfb \
    zip \
    "
