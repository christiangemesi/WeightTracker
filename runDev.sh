#!/bin/sh
gradle build -x test -x jar -x distTar -x distZip --continuous &
gradle bootRun --no-build-cache
