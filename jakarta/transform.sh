#!/bin/bash

set -e -u -o pipefail
shopt -s failglob

if [ ! -f LICENSE.md ]; then
    echo "ERROR: This script has to be run from the root of the quarkus-http project"
    exit 1
fi

# Set up jbang alias, we are using latest released transformer version
jbang alias add --name transform org.eclipse.transformer:org.eclipse.transformer.cli:0.4.0

# Function to help transform a particular Maven module using Eclipse Transformer
transform_module () {
  local modulePath="$1"
  local transformationTemp="JAKARTA_TEMP"
  rm -Rf $transformationTemp
  mkdir $transformationTemp
  echo "  - Transforming $modulePath"
  jbang transform -o $modulePath $transformationTemp
  rm -Rf "$modulePath"
  mv "$transformationTemp" "$modulePath"
  echo "    > Transformation done"
}

# Rewrite a module with OpenRewrite
rewrite_module () {
  local modulePath="$1"
  echo "  - Rewriting $modulePath"
  mvn -B rewrite:run -f "${modulePath}/pom.xml" -N
  echo "    > Rewriting done"
}

convert_service_file () {
  local newName=${1/javax/jakarta}
  mv "$1" "$newName"
}

sed -i 's@<quarkus.platform.version>999-SNAPSHOT</quarkus.platform.version>@<quarkus.platform.version>999-jakarta-SNAPSHOT</quarkus.platform.version>@g' pom.xml
sed -i 's@<quarkus-plugin.version>999-SNAPSHOT</quarkus-plugin.version>@<quarkus-plugin.version>999-jakarta-SNAPSHOT</quarkus-plugin.version>@g' pom.xml
sed -i 's@<quarkus.platform.version>999-SNAPSHOT</quarkus.platform.version>@<quarkus.platform.version>999-jakarta-SNAPSHOT</quarkus.platform.version>@g' framework/pom.xml
sed -i 's@<quarkus-plugin.version>999-SNAPSHOT</quarkus-plugin.version>@<quarkus-plugin.version>999-jakarta-SNAPSHOT</quarkus-plugin.version>@g' framework/pom.xml
sed -i 's@<quarkus.platform.version>999-SNAPSHOT</quarkus.platform.version>@<quarkus.platform.version>999-jakarta-SNAPSHOT</quarkus.platform.version>@g' tests/pom.xml
sed -i 's@<quarkus-plugin.version>999-SNAPSHOT</quarkus-plugin.version>@<quarkus-plugin.version>999-jakarta-SNAPSHOT</quarkus-plugin.version>@g' tests/pom.xml
sed -s 's/org\.jboss\.spec\.javax\.ws\.rs/jakarta.ws.rs/g' -i framework/pom.xml
sed -s 's/jboss-jaxrs-api_2\.1_spec/jakarta.ws.rs-api/g' -i framework/pom.xml
sed -s 's/org\.jboss\.spec\.javax\.xml\.bind/jakarta.xml.bind/g' -i framework/pom.xml
sed -s 's/jboss-jaxb-api_2\.3_spec/jakarta.xml.bind-api/g' -i framework/pom.xml

transform_module framework/src
transform_module tests/src

sed -i '/import jakarta.xml.bind.Validator;/d' tests/src/test/java/com/sun/ts/tests/jaxrs/spec/provider/jaxbcontext/SomeJaxbContext.java
sed -i '37,41d' tests/src/test/java/com/sun/ts/tests/jaxrs/spec/provider/jaxbcontext/SomeJaxbContext.java
sed -i 's/<A extends XmlAdapter>/<A extends XmlAdapter<?, ?>>/g' tests/src/test/java/com/sun/ts/tests/jaxrs/spec/provider/jaxbcontext/SomeMarshaller.java
sed -i 's/<A extends XmlAdapter>/<A extends XmlAdapter<?, ?>>/g' tests/src/test/java/com/sun/ts/tests/jaxrs/spec/provider/jaxbcontext/SomeUnmarshaller.java
sed -i '118,121d' tests/src/test/java/com/sun/ts/tests/jaxrs/spec/provider/jaxbcontext/SomeUnmarshaller.java
sed -i '84,88d' tests/src/test/java/com/sun/ts/tests/jaxrs/spec/provider/jaxbcontext/SomeUnmarshaller.java

# Upgrade to EE 10
# TODO: maybe we should move the other changes to the branch now that we have a branch
# let's do it if main changes too much
git fetch origin jakarta-10-jaxrs
git rev-list c5785f3465fa87395574fde1274c1712b3aa728b..origin/jakarta-10-jaxrs | tac | xargs git cherry-pick -x
