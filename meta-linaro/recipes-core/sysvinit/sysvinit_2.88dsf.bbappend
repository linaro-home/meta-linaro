do_install_append() {
  sed -i -e 's/VERBOSE=no/VERBOSE=yes/' ${D}${sysconfdir}/default/rcS
}


