#!/usr/bin/env bash

echo "
 _____         _           _____ ____  _____
|_   _|__  ___| |_ _   _  | ____|___ \| ____|
  | |/ _ \/ __| __| | | | |  _|   __) |  _|
  | |  __/\__ \ |_| |_| | | |___ / __/| |___
  |_|\___||___/\__|\__, | |_____|_____|_____|
                   |___/
          Testy ścieżek krytycznych
"
java -jar e2e/sql/db-util.jar e2e/sql/update-invoice-payment-date.sql
ng e2e --specs=./e2e/critical/**/*.e2e-spec.ts --base-href=http://localhost:4200/ --serve=false
