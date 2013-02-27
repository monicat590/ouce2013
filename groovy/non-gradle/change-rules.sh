#!/bin/sh

BASENAME=`basename $0`
export BASENAME=${BASENAME%.*}
. ../setenv.sh
groovy ./$BASENAME/*/*/*/*/program.groovy
