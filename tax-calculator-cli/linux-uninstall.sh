#!/usr/bin/env bash
set -euo pipefail

APP_NAME="capital-gains"
INSTALL_DIR="/opt/${APP_NAME}"
WRAPPER="/usr/local/bin/${APP_NAME}"

DRY_RUN=0
if [[ "${1:-}" == "--dry-run" ]]; then
  DRY_RUN=1
fi

run() {
  if [[ "$DRY_RUN" -eq 1 ]]; then
    echo "[dry-run] $*"
  else
    eval "$@"
  fi
}

echo "[uninstall] Uninstalling ${APP_NAME}..."

echo "[uninstall] Removing wrapper: ${WRAPPER}"
if [[ -e "${WRAPPER}" ]]; then
  run "rm -f \"${WRAPPER}\""
else
  echo "[uninstall] Wrapper not found (ok)."
fi

echo "[uninstall] Removing install directory: ${INSTALL_DIR}"
if [[ -d "${INSTALL_DIR}" ]]; then
  run "rm -rf \"${INSTALL_DIR}\""
else
  echo "[uninstall] Install dir not found (ok)."
fi

echo "[uninstall] Done."
