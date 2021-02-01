#!/bin/bash
# find_and_replace.sh

echo "Find and replace in current directory!"
echo "Existing string?"
read existing
echo "Replacement string?"
read replacement
echo "Replacing all occurences of $existing with $replacement in files matching *.java"

find . -name '*.java' -print0 | xargs -0 sed -i'' -e "s/$existing/$replacement/g"
