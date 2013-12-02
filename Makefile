JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

run: all
	java -cp .:db4o.jar evidence

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) -cp .:db4o.jar $<
