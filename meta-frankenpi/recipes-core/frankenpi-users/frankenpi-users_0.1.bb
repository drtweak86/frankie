SUMMARY = "Frankenpi user setup"
LICENSE = "MIT"
PR = "r0"

SRC_URI = "file://shadow.frankenpi                    file://passwd.frankenpi                    file://frankie.sudo"

do_install() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/passwd.frankenpi ${D}${sysconfdir}/passwd.frankenpi
    install -m 0644 ${WORKDIR}/shadow.frankenpi ${D}${sysconfdir}/shadow.frankenpi

    if [ ! -f ${D}${sysconfdir}/passwd ]; then
        install -m 0644 ${WORKDIR}/passwd.frankenpi ${D}${sysconfdir}/passwd
    else
        grep -q '^frankie:' ${D}${sysconfdir}/passwd || cat ${WORKDIR}/passwd.frankenpi >> ${D}${sysconfdir}/passwd
    fi

    if [ ! -f ${D}${sysconfdir}/shadow ]; then
        install -m 0600 ${WORKDIR}/shadow.frankenpi ${D}${sysconfdir}/shadow
    else
        grep -q '^root:' ${WORKDIR}/shadow.frankenpi &&                 sed -n '1p' ${WORKDIR}/shadow.frankenpi | awk -F: '{print $2}' | xargs -I{}                   sh -c "sed -i -E 's#^root:[^:]*:#root:{}:#' ${D}${sysconfdir}/shadow" || true
        grep -q '^frankie:' ${D}${sysconfdir}/shadow || cat ${WORKDIR}/shadow.frankenpi >> ${D}${sysconfdir}/shadow
    fi

    install -d ${D}${sysconfdir}/sudoers.d
    install -m 0440 ${WORKDIR}/frankie.sudo ${D}${sysconfdir}/sudoers.d/frankie

    install -d -m 0755 ${D}/home/frankie
    chown 1000:1000 ${D}/home/frankie || true
}

FILES_${PN} += "${sysconfdir}/passwd ${sysconfdir}/shadow ${sysconfdir}/sudoers.d/frankie"
