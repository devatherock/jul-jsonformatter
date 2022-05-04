upload-snapshot:
	./gradlew build uploadArchives -PnexusUsername=$SONATYPE_USERNAME -PnexusPassword=$SONATYPE_PASSWORD -Dsnapshot=true -x test