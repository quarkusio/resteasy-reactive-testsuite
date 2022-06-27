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

sed -s 's/jboss-jaxrs-api_2\.1_spec/jboss-jaxrs-api_3.0_spec/g' -i framework/pom.xml
sed -s 's/org\.jboss\.spec\.javax\.xml\.bind/jakarta.xml.bind/g' -i framework/pom.xml
sed -s 's/jboss-jaxb-api_2\.3_spec/jakarta.xml.bind-api/g' -i framework/pom.xml
transform_module framework/src
transform_module tests/src
