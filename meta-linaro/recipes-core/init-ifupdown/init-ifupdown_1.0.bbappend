do_install_append() {
	case "${MACHINE}" in
		"lng-x86-64" | "lng-rt-x86-64" )
			sed -i 's|auto eth0|auto eth1|' ${D}${sysconfdir}/network/interfaces
		;;
		*)
		;;
	esac
}
