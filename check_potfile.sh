#!/bin/bash

cat potfile | grep $1 | cut -d':' -f2
