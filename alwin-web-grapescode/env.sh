#!/usr/bin/env bash

echo "switching to $1"
cp env-specific/$1/env-specific.json src/assets/env-specific.json
