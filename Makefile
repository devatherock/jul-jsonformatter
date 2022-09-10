upload-snapshot:
	./gradlew build publishToSonatype -Dsnapshot=true -x test
clean:
	./gradlew clean
build:
	./gradlew build -x test