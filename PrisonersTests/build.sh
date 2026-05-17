
set -e

BIN="bin"
JUNIT_JAR="lib/junit-platform-console-standalone.jar"

mkdir -p "$BIN"

echo "[1/3] Compiling source files..."
javac -d "$BIN" \
  src/container/MyLinkedList.java \
  src/prisoners/Prisoner.java \
  src/prisoners/PrisonerAnalyzer.java \
  src/prisoners/PrisonerUtils.java

echo "[2/3] Compiling test files..."
javac -cp "$BIN:$JUNIT_JAR" -d "$BIN" \
  test/java/container/MyLinkedListTest.java \
  test/java/prisoners/PrisonerUtilsTest.java \
  test/java/prisoners/PrisonerAnalyzerTest.java

echo "[3/3] Running tests..."
java -jar "$JUNIT_JAR" \
  --class-path "$BIN" \
  --scan-class-path \
  --details verbose
