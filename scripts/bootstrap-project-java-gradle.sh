#!/bin/sh

LATEST_VERSION=$(curl -s "https://mifos.jfrog.io/artifactory/mifosx-gradle-local/org/mifos/mifos-conventions-gradle-project-java/maven-metadata.xml" | grep -oP '(?<=<latest>)[^<]+')

curl -H "Cache-Control: no-cache, no-store, must-revalidate" \
          -H "Pragma: no-cache" \
          -H "Expires: 0"  \
          -O "https://mifos.jfrog.io/artifactory/mifosx-gradle-local/org/mifos/mifos-conventions-gradle-project-java/${LATEST_VERSION}/mifos-conventions-gradle-project-java-${LATEST_VERSION}-template.tgz"

mkdir mifos-dummy

tar -xvzf mifos-conventions-gradle-project-java-$LATEST_VERSION-template.tgz --strip-components=2 -C mifos-dummy

rm mifos-conventions-gradle-project-java-$LATEST_VERSION-template.tgz

cd mifos-dummy

git init
# replace with your own details!
git config user.email "aleks@mifos.org"
git config user.username "Aleksandar Vidakovic"
git add .
git commit -m "chore: Initial commit"

chmod +x gradlew

./gradlew mifosConfigUnzip
./gradlew clean build publish
