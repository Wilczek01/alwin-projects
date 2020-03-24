# AlwinWeb

Projekt został wygenerowany przy użyciu [Angular CLI](https://github.com/angular/angular-cli) wersja 1.2.0.

## Uruchamianie projektu do celów developmentu

Projekt uruchamiany jest poprzez `ng serve` i domyślnie łączy się z backendem pod adresem `localhost:8080`. Aby zmienić środowisko na inne należy odpalić 
skrypt przekazując jako parametr nazwę środowiska np. `sh env.sh mock` zmieni endpoint na adres mocka `https://dev.grapescode.pl/alwin-rest-mock/`. Plik z 
konfiguracją został dodany do `.gitignore`, ale żeby git całkowicie go ignorował należy odpalić `git update-index --assume-unchanged src/assets/env-specific.json`.
Po zmianie środowiska ponowne odpalenie `ng serve` spowoduje uruchomienie aplikacji połączonej do wybranego środowiska. Wszytstkie zmiany wprowdzone w 
kogdzie źródłowym ładują się automatycznie. Aplikacja uruchamia się pod adresem `http://localhost:4200/`. Dostępne środowiska:
- local
- mock
- mock-localhost
- prod
- qa
- test
- uat

## Generowanie kodu

Uruchomienie `ng generate component component-name` tworzy nowy komponent. Można również użyć `ng generate directive|pipe|service|class|module`.

## Budowanie artefaktu

Uruchomienie `ng build` buduje projekt. Zbudowany artefakt znajduje się w katalogu `dist/`. Użycie flagi `-prod` utworzy artefakt produkcyjny. UWAGA: 
uruchamianie `ng serve --prod` może skutkować brakiem automatycznego przeładowania zmian w kodzie źródłowym.

## Uruchamianie testów jednostkowych

Uruchomienie `ng test` odpala testy jednostkowe przy użyciu [Karma](https://karma-runner.github.io).

## Uruchamianie testów e2e

Uruchomienie `ng e2e` odpala testy end-to-end podpięte to środowiska ustawionego poprzez wcześniejsze odpalenie skryptu `sh env.sh mock` podobnie jak w 
przypadku `ng serve`. Zalecane środowisko do testów to `mock` Testy e2e uruchamiane są poprzez [Protractor](http://www.protractortest.org/).

## Uruchamianie testów krytycznych e2e
Testy krytyczne zawierają zestaw testów, które uruchamiają automatyczne przejścia ścieżek najważniejszych dla aplikacji łącząc się do gotowego działającego 
środowiska. Uruchomienie następuje przez uruchomienie skryptu z głównego poziomu aplikacji `./run-critical-tests.sh`.

## Pomoc

Aby uzyskać więcje informacji na temat Angular CLI można użyć `ng help` lub sprawdzić stronę [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
