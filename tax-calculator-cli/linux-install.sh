#!/usr/bin/env bash
set -euo pipefail

APP_NAME="capital-gains"
JAR_SOURCE="./target/tax-calculator-cli-1.0-SNAPSHOT.jar"
INSTALL_DIR="/opt/${APP_NAME}"
JAR_TARGET="${INSTALL_DIR}/tax-calculator-cli-1.0-SNAPSHOT.jar"
WRAPPER="/usr/local/bin/${APP_NAME}"

echo "[install] Installing ${APP_NAME}..."

if [ ! -f "${JAR_SOURCE}" ]; then
  echo "[install] ERROR: Jar not found at ${JAR_SOURCE}"
  echo "[install] Build the project first, e.g.: mvn clean package"
  exit 1
fi

echo "[install] Creating install directory: ${INSTALL_DIR}"
mkdir -p "${INSTALL_DIR}"

echo "[install] Copying jar to ${JAR_TARGET}"
cp "${JAR_SOURCE}" "${JAR_TARGET}"

echo "[install] Creating wrapper command: ${WRAPPER}"

cat > "${WRAPPER}" << EOF
#!/usr/bin/env bash
# Wrapper script for ${APP_NAME}

JAR="${JAR_TARGET}"

if [ ! -f "\${JAR}" ]; then
  echo "ERROR: Jar not found at \${JAR}" >&2
  exit 1
fi

# Forward stdin/stdout/stderr and any args
exec java -jar "\${JAR}" "\$@"
EOF

chmod +x "${WRAPPER}"
