
LIBS=builds/RhinoTool.jar
for jar in lib/*
do
	LIBS=${LIBS}:${jar}
done

echo "java -cp $LIBS:$APP net.global_village.RhinoTool $* -library scripts/common"

java -cp $LIBS:$APP net.global_village.RhinoTool $* -library scripts/common
