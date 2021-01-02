#!/bin/bash

cat modes.txt | grep $1 | cut -d' ' -f2-
