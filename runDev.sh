#!/bin/sh
gradle build --continuous &
gradle bootRun --no-build-cache
