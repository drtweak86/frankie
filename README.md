Frankenpi Yocto GitHub Actions scaffold
======================================

How to use
1. Create a new GitHub repository and upload the contents of this tarball.
2. In the repo settings -> Secrets -> Actions add:
   - FRANKENPI_ROOT_PW (e.g. raspberry)
   - (optional) FRANKIE_PUBKEY (your SSH public key)
3. Push to main or run the workflow manually in Actions -> Workflows -> Yocto build.
4. Download the `yocto-image` artifact when the workflow completes and flash it to an SD card.

Notes
- First build may take a long time (hours). Use core-image-minimal by changing IMAGE_NAME in the workflow if builds time out.
- The workflow caches DL and SSTATE directories between runs, speeding up future builds.
- No secrets are stored in the repo. The workflow will inject the hashed root password from your secret at build time.
