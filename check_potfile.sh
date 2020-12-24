#!/bin/bash

cat /home/daisy/.hashcat/hashcat.potfile | grep $1 | cut -d':' -f2
