#!/bin/bash
in=$1
set -o errexit # -e does not work in Shebang-line!
set -o pipefail
set -o nounset

head -n1 $in
grep "^S" $in
grep "^P" $in
grep "^L" $in
