#!/bin/bash

cat /home/daisy/hashcat-GUI/modes.txt | grep $1 | cut -d' ' -f2-
