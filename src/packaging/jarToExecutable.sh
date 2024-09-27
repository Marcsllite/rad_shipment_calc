#!/bin/sh

# check_java_in_path - Check if java in PATH
#
# This function checks if java is in PATH
# and it is java 17
#
# Returns:
#   0 if java in PATH and is java 17 (true)
#   1 if java in PATH and is not java 17 (false)
#   1 if java not in PATH (false)
#
# Usage:
#   check_java_in_path
check_java_in_path() {
  if command -v java >/dev/null 2>&1; then
    if is_java_17 "$(command -v java)"; then
      javaCommand=${javaCommand:-"$(command -v java)"}
      return 0
    else
      echo "Java in PATH is not Java 17"
    fi
  else
    echo "Java is not in PATH"
    return 1
  fi
}

# check_java_home - Check if JAVA_HOME is set
#
# This function checks if JAVA_HOME is set
# and it is java 17
#
# Returns:
#   0 if JAVA_HOME is set to java 17 (true)
#   1 if JAVA_HOME is not set to java 17 (true)
#   1 if JAVA_HOME is not set (false)
#
# Usage:
#   check_java_home
check_java_home() {
  if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
    if is_java_17 "$JAVA_HOME/bin/java"; then
      javaCommand=${javaCommand:-"$JAVA_HOME/bin/java"}
      return 0
    else
      echo "Java located at '$JAVA_HOME' is not Java 17"
    fi
  else
    echo "JAVA_HOME is not set or in not executable"
  fi
  return 1
}

# is_java_17 - Check if the version of java
#
# This function checks if the provided path to the java binary
# points to a java 17 version
#
# Parameters:
#   path - The path to the java binary
#
# Returns:
#   0 if the path points to java 17 (true)
#   1 if the path does not point to java 17 (false)
#
# Usage:
#   is_java_17 <path>
#   if is_java_17 /path/to/java/17; then
#       return true
#   else
#       return false
#   fi
is_java_17() {
  JAVA_VERSION=$($1 -version 2>&1 | awk -F[\"_] '/version/ {print $2}')

  if [ -n "$JAVA_VERSION" ] && [[ "$JAVA_VERSION" == 17* ]]; then
      return 0
  else
    echo "'$1' is not a valid path to Java 17"
    return 1
  fi
}

# check_javafx_sdk - Check if JAVAFX_HOME is set
#
# This function checks if JAVAFX_HOME is set
# and it is java 17
#
# Returns:
#   0 if JAVAFX_HOME is set and is valid (true)
#   1 if JAVAFX_HOME is set and is not valid (true)
#   1 if JAVAFX_HOME is not set (false)
#
# Usage:
#   check_javafx_sdk
check_javafx_sdk() {
  if [ -n "$JAVAFX_HOME" ] &&
     [ -f "$JAVAFX_HOME/lib/javafx.controls.jar" ] &&
     [ -f "$JAVAFX_HOME/lib/javafx.fxml.jar" ] &&
     check_javafx_version; then
      return 0
  else
    echo "JAVAFX_HOME is not set to a valid JavaFX 17 SDK"
    return 1
  fi
}

check_javafx_version() {
  local property="javafx.version"
  local file=""

  if [ -f "$JAVAFX_HOME/lib/javafx.properties" ]; then
    file="$JAVAFX_HOME/lib/javafx.properties"
  else
    echo "JavaFX properties file not found"
    return 1
  fi

  local value=$(grep "^$property=" "$file" | cut -d'=' -f2- | tr -d ' ')

  if [[ "$value" == 17* ]]; then
    return 0
  else
    echo "The Java SDK at '$JAVAFX_HOME' is not version 17"
    return 1
  fi
}

runJar() {
  exec "javaCommand" --module-path "$JAVAFX_HOME/lib" --add-modules=javafx.controls,javafx.fxml "$1" "$2"
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

