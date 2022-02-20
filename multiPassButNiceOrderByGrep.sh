#!/bin/bash
in=$1
set -o errexit # -e does not work in Shebang-line!
set -o pipefail
set -o nounset

head -n1 $in
grep -a -P "^S" $in
grep -a -P "^P" $in
grep -a -P "^L" $in
