#!/bin/bash

echo
echo "Sample API request http://localhost:8080/api/availability?embedId=1834038832&productId=BUDE15v371&minTime=1595782524&maxTime=1595789064&interval=60"
echo

curl -X GET 'http://localhost:8080/api/availability?embedId=1834038832&productId=BUDE15v371&minTime=1595782524&maxTime=1595789064&interval=60'

echo