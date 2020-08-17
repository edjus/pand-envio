#!/bin/bash

EXIT_STATUS=0

echo "Running server tests"
cd server/
./grailsw test-app || EXIT_STATUS=$? #

exit $EXIT_STATUS