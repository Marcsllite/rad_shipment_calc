#!/bin/bash

# is_at_least_java_17 - Check if the version of java
#
# This function checks if the provided path to the java binary
# points to a java 17 version or higher
#
# Parameters:
#   path - The path to the java binary
#
# Returns:
#   0 if the path points to java >= 17 (true)
#   1 if the path points to java < 17 (false)
#
# Usage:
#   is_at_least_java_17 <path>
is_at_least_java_17() {
  local fullVersion=$($1 -version 2>&1 | awk -F[\"_] '/version/ {print $2}')
  local majorVersion=$(echo "$fullVersion" | cut -d'.' -f1)

  if [ -n "$fullVersion" ] && [ "$majorVersion" -ge 17 ]; then
      return 0
  else
    echo "The Java executable at '$1' is version $fullVersion. The minimum version required is 17"
    return 1
  fi
}

# check_java_in_path - Check if java in PATH
#
# This function checks if java is in PATH
# and it is java 17
#
# Returns:
#   0 if java in PATH and is java >= 17 (true)
#   1 if java in PATH and is java < 17 (false)
#   1 if java not in PATH (false)
#
# Usage:
#   check_java_in_path
check_java_in_path() {
  javaCommand=$(command -v java)
  if [ -n "$javaCommand" ]; then
    if is_at_least_java_17 "$javaCommand"; then
      return 0
    fi
  else
    echo "Java in PATH '$javaCommand' is not found"
    return 1
  fi
}

# check_java_home - Check if JAVA_HOME is set
#
# This function checks if JAVA_HOME is set
# and it is at least java 17
#
# Returns:
#   0 if JAVA_HOME is set to java >= 17 (true)
#   1 if JAVA_HOME is set to java < 17 (false)
#   1 if JAVA_HOME is not set (false)
#
# Usage:
#   check_java_home
check_java_home() {
  if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
    if is_at_least_java_17 "$JAVA_HOME/bin/java"; then
      javaCommand=${javaCommand:-"$JAVA_HOME/bin/java"}
      return 0
    fi
  else
    echo "JAVA_HOME is not set or is not executable"
  fi
  return 1
}

check_javafx_version() {
  local file="$JAVAFX_HOME/lib/javafx.properties"

  if [ -f "$file" ] && grep -q javafx.version "$file"; then
    local fullVersion=$(grep javafx.version "$file" | cut -d'=' -f2 | tr -d ' ')
    local majorVersion=$(echo "$fullVersion" | cut -d'.' -f1)

    if [ "$majorVersion" -ge 17 ]; then
      return 0
    else
      echo "The JavaFX SDK at '$JAVAFX_HOME' is version $fullVersion. The minimum version required is 17"
      return 1
    fi
  else
    echo "JavaFX properties file not found"
    return 1
  fi
}

# check_javafx_sdk - Check if JAVAFX_HOME is set
#
# This function checks if JAVAFX_HOME is set
# and it is at least java 17
#
# Returns:
#   0 if JAVAFX_HOME is set and version >= 17 (true)
#   1 if JAVAFX_HOME is set and version < 17 (false)
#   1 if JAVAFX_HOME is not set (false)
#
# Usage:
#   check_javafx_sdk
check_javafx_sdk() {
  if ! check_javafx_version; then
    echo "JavaFX version check failed."
    return 1
  fi

  if [ ! -f "$JAVAFX_HOME/lib/javafx.controls.jar" ]; then
    echo "JavaFX controls JAR not found."
    return 1
  fi

  if [ ! -f "$JAVAFX_HOME/lib/javafx.fxml.jar" ]; then
    echo "JavaFX FXML JAR not found."
    return 1
  fi

  return 0
}

runJar() {
  echo "Starting application: $1"
  exec "$javaCommand" \
        --module-path "$JAVAFX_HOME/lib" \
        --add-modules javafx.controls,javafx.fxml \
        --add-opens javafx.base/com.sun.javafx.logging=ALL-UNNAMED \
        --add-opens javafx.graphics/com.sun.glass.ui=ALL-UNNAMED \
        --add-opens javafx.graphics/com.sun.javafx.util=ALL-UNNAMED \
        --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED \
        -jar "$1" "$2"
}

# Main script
if check_java_in_path || check_java_home; then
  if check_javafx_sdk; then
    runJar "$0" "$@"
  else
    echo "JavaFX SDK check failed."
    exit 1
  fi
else
  echo "Java check failed."
  exit 1
fi

