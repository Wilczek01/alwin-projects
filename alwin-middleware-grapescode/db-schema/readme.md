## Utworzenie bazy oraz użytkownika
Należy wykonać sql'e z 
```
db-schema/dcl/alwin.sql
```

## Inkrementalne nałożenie skryptów
```
mvn clean package
``` 
w projekcie 
```
db-schema
```

## Inkrementalne dodanie danych testowych
```
mvn clean package -Ptest
``` 
w projekcie 
```
db-schema
```

## Dodawanie nowych skryptów
Dodajemy nowy plik `sql` w odpowienim katalogu `db-schema/src/main/dbschema` (`ddl` lub `dml`).
W pliku `db.changelog-x.x.x.xml` uzupełniamy wpis dla nowej sqlki.
Done.

## Dodawanie nowych danych testowych
Dodajemy sql z danymi testowymi w `db-schema/src/test/dbschema/dml` i uzupełniamy `db.changelog-master-test.xml` o nowy wpis.

## Generowanie skryptu do zmiany bazy na produkcji
W pom.xml należy wybrać wersję, dla której chcesz wygenerować skrypt:
```
<prepare-sql-for-version>1.0.0</prepare-sql-for-version>
```
następnie uruchom:
```
mvn test -Pprepare-sql
``` 
w projekcie:
```
db-schema
```
pojawi się skrypt sql do migracji danych dla wybranej wersji:
```
db-schema/src/main/dbschema/${prepare-sql-for-version}/migration.sql
```

## Dokumentacja pluginu liquibase
http://www.liquibase.org/documentation/maven/