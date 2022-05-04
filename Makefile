upload-snapshot:
	./gradlew build publishToSonatype -Dsnapshot=true -x test