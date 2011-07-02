
BUILDS=dist
LIBS=.
for jar in lib/*
do
	LIBS=${LIBS}:${jar}
done

#echo $LIBS

if [ ! -d $BUILDS ]; then
	mkdir $BUILDS
fi

javac -Xlint:unchecked -cp $LIBS Arguments.java RhinoTool.java -d $BUILDS

if [ $? -ne 0 ]
then
	echo "Compile Error";
	exit 1;
fi

cd $BUILDS
jar -cfM RhinoTool.jar *
if [ $? -ne 0 ]
then
	echo "Jar Error";
	exit 2;
fi

echo

