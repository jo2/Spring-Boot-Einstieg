# Spring-Boot-Einstieg
Dieser Artikel soll den Einstieg in Spring Boot vereinfachen.
Im Rahmen dieses Artikels wird anhand eines Bookstore-Projekts grob gezeigt, wie Spring Boot funktioniert und wie mit Spring Boot ein kleines Projekt aufgebaut werden kann.
In diesem Artikel werden verschiedene Tutorials verlinkt.
Grundkenntnisse zu den in den Tutorials vermittelten Themen werden für die jeweils nachfolgenden Schritte vorausgesetzt, es empfiehlt sich also, diese Tutorials durchzuarbeiten.
Grundlegend werden einige Vorkenntnisse vorausgesetzt. Dazu gehören objektorientierte Programmierung mit Java, relationale Datenbanken und SQL und REST.

> **WICHTIG!!**: 
  Dieses Repository ist bereits etwas älter, daher sind die verwendeten Versionen (Java, Spring Boot, etc.) veraltet.
  Ziel dieses Repository ist es, eine Schritt-für-Schritt-Anleitung anhand der Commits abzubilden, und nicht, immer die aktuellsten Versionen zu beinhalten.
  Beim Bearbeiten dieses Artikels sollten immer möglichst aktuelle Versionen verwendet werden.

* [Task 1: Spring Boot Projekt erstellen](#task-1-spring-boot-projekt-erstellen)
  * Über Intelli-J
  * Web-Variante
  * Start der Anwendung
* [Task 2: Erstellen der Fachlogik](#task-2-erstellen-der-fachlogik)
  * Entities
  * Services
  * Spring Boots ApplicationContext
* [Task 3: REST](#task-3-rest)
* [Task 4: Datenbanken und Persistenz](#task-4-datenbanken-und-persistenz)
  * Datenbank & neue Dependencies
  * Spring Boot Data JPA
  * Einblick in die Datenbank
  * Persistenz
* [Task 5: Frontend](#task-5-frontend)
  * Tutorials
  * Aufgaben
* [Task 6: Form Validation](#task-6-form-validation)
* [Task 7: Spring Profiles einsetzen](#task-7-spring-profiles-einsetzen)
* [Task 8: Dockerize](#task-8-dockerize)

Ein Git-Repository mit einer Beispiellösung findet sich hier. Die Lösung der einzelnen Tasks kann dabei den entsprechenden Commits entnommen werden. Diese Lösung dient nicht dazu, einfach kopiert zu werden. Sinn der Einarbeitung ist, den Umgang mit Spring Boot und nicht den Umgang mit Copy & Paste zu lernen.

## Task 1: Spring Boot Projekt erstellen
Als Erstes wird das Spring Boot Projekt erstellt.

### Über Intelli-J
IntelliJ-Nutzer können über File → New → Project → Spring Initializr ein Spring Boot Projekt erstellen und nachfolgende Einstellungen vornehmen:

| Attribut | Wert |
| --- |:---:|
| groupId |  de.username |
| artifactId | bookstore |
| Projekt bzw. Typ | Gradle |
| Sprache | Java |
| Spring Boot Version | aktuellste stabile Version (Version X.Y.Z, ohne Snapshot oder ähnliches) |

Im nächsten Schritt können benötigte Abhängigkeiten ausgewählt werden, momentan reicht allerdings das blanke Spring Boot Projekt. 

### Web-Variante
Alternativ zum Spring Initializr kann über [https://start.spring.io/](https://start.spring.io/) das Projekt erstellt, heruntergeladen und anschließend in der Entwicklungsumgebung ein neues Projekt von einer existierenden Codebase erstellt werden. 

Das Projekt kann dann im Fall der Web-Variante heruntergeladen werden oder bei IntelliJ direkt als Projekt geöffnet werden.

### Start der Anwendung
Zum Starten der Anwendung wird Gradle verwendet: `gradlew bootRun`.
In Intelli-J kann die Anwendung auch vom Gradle-Fenster über `de.adesso.bookstore` -> Tasks -> application -> bootRun ausgeführt werden.

## Task 2: Erstellen der Fachlogik
### Entities
In diesem Abschnitt wird die Funktionalität des Bookstore implementiert.
Zunächst wird unter `src/main/java/de/<username>/bookstore` das Package `entities` mit der Klasse `Book` erstellt. In der Klasse `Book` werden folgenden Attribute definiert:

* Titel (String)
* Autor (String)
* Preis (double)
* Erscheinungsjahr (int)

### Services
Als nächstes wird das Package `services` mit der Klasse `BookstoreService` erstellt.
Zur Speicherung der Bücher wird eine einfache Liste verwendet, die als privates Attribut im `BookstoreService` definiert wird.
Für diese Liste müssen Zugriffsmethoden implementiert werden.
Folgende Funktionalitäten sollen implementiert werden:

* Ein Buch anhand seines Titel finden, die Methode gibt das gefundene Buch-Objekt zurück
* Ein Buch zur Liste hinzufügen, die Methode gibt das hinzugefügte Buch-Objekt zurück
* Ein Buch aus der Liste entfernen, die Methode gibt nichts zurück
* Ein Buch in der Liste aktualisieren, die Methode gibt die neue Version des Buch-Objekts zurück
* Die gesamte Liste ausgeben, die Methode gibt eine Liste von Buch-Objekten zurück

### Spring Boots ApplicationContext
In Spring Boot gibt es einen `ApplicationContext`.
Dieser kann unter anderem Java-Objekte, sogenannte Beans, erzeugen und verwalten.
Mehr Informationen über Beans und den `ApplicationContext` finden sich in der Spring-Dokumentation.
Mit der Annotation `@Component` wird dem `ApplicationContext` mitgeteilt, dass er die so annotierte Klasse als Bean verwalten soll.

Die `@Component`-Annotation hat die Subnotationen `@Repository`, `@Service`, `@Controller` und `@RestController`.
Diese markieren eine Klasse ebenfalls als vom `ApplicationContext` verwaltete Bean und bieten darüber hinaus noch einige Features auf die später eingegangen wird.
Genaueres dazu findet sich in der [Spring-Boot-Dokumentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.spring-beans-and-dependency-injection).

Damit der `ApplicationContext` erkennt, aus welchen Klassen eine Bean erzeugt werden soll, wird ein `ComponentScan` durchgeführt.
Dies geschieht in Spring Boot automatisch durch die Annotation `@SpringBootApplication`.
Im Gegensatz zu Spring übernimmt Spring Boot die meisten Konfigurationen, die in der Dokumentation von Spring über XML, Groovy oder Java gemacht werden, automatisch.

Die Klasse `BookstoreService` erhält außerdem eine `init()`-Methode, die mit `@PostConstruct` annotiert ist.
Diese Methode wird von Spring Boot ausgeführt, sobald die Bean vom `ApplicationContext` erstellt wurde.
Der Aufruf der Logik erfolgt in dieser `init()`-Methode.
Die Ausgabe zur Überprüfung der korrekten Funktionalität kann auf der Konsole erfolgen.

## Task 3: REST
Jetzt werden die Funktionalitäten des `BookstoreService` über eine REST-API zur Verfügung gestellt.
Dazu wird in der `build.gradle` eine Abhängigkeit hinzugefügt:

```build.gradle
implementation 'org.springframework.boot:spring-boot-starter-web'
```

Außerdem wird die Klasse `BookstoreController` benötigt.
Sie wird mit `@RestController` annotiert.
Zum einen wird die Klasse dadurch als Bean, die verwaltet werden muss, markiert, zum anderen bedeutet diese Annotation, dass sich in dieser Klasse Handler-Methoden für REST-Anfragen befinden.
Zur Einarbeitung sollte [dieses Tutorial](https://spring.io/guides/gs/rest-service/) bearbeitet werden.

Anschließend sollen die Funktionalitäten des `BookstoreService` über REST-Schnittstellen bereitgestellt werden.
Sämtliche URIs sollen dabei mit `/api` beginnen.

Damit der `BookstoreController` auf den `BookstoreService` zugreifen kann, muss der `BookstoreService` in den `BookstoreController` injiziert werden.
Dies geschieht über die Annotation `@Autowired`, mehr dazu in der [Spring Dokumentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-autowired-annotation).
Das Injizieren geschieht mittels Construktor Injection und nicht mittels Property Injection, die Gründe und Vorgehensweise dazu können [hier](https://odrotbohm.de/2013/11/why-field-injection-is-evil/) nachgelesen werden.
Um die Funktionalität der REST-Schnittstellen zu testen können Programme wie [Postman](https://www.postman.com/downloads/) oder [Insomnia](https://insomnia.rest/download) verwendet werden.

Eure Projektstruktur sollte bisher in etwa so aussehen:

![image](https://user-images.githubusercontent.com/7516893/135058453-83b62bb8-a9ae-435a-aab1-9bebe9953fe3.png)

## Task 4: Datenbanken und Persistenz
### Datenbank & neue Dependencies
Als nächstes wird die Datenhaltung aufgerüstet.
Anstatt einer einfachen Liste wird jetzt eine Datenbank verwendet.
Der Einfachheit halber wird hier eine InMemory-Datenbank verwendet.
Die Datenbank speichert alle Daten im Hauptspeicher und persistiert sie, wenn entsprechend konfiguriert, in einer Datei.
In diesem Beispiel wird die H2-Datenbank verwendet.
Dabei handelt es sich um eine relationale Datenbank, entsprechend sollten Vorkenntnisse in relationalen Datenbanken und SQL vorhanden sein.
Eine kurze Einführung in das Thema bietet [dieses Tutorial](https://www.w3schools.com/mysql/mysql_intro.asp).
Zunächst werden zwei neue Abhängigkeiten benötigt:

```build.gradle
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'com.h2database:h2'
```

Die neuen Abhängigkeiten müssen zunächst noch reingeladen werden, dafür sollte das Projekt einmal neu gebaut werden (Build → Build Project)

### Spring Boot Data JPA
Ein Tutorial zur Verwendung von Spring Boot Data JPA findet sich [hier](https://spring.io/guides/gs/accessing-data-jpa/).
Die Informationen aus diesem Tutorial müssen als Nächstes auf dieses Projekt angewendet werden:

Dazu muss die Klasse `Book` angepasst und das Interface `BookstoreRepository` im Package `repositories` hinzugefügt werden.
Da das Attribut id der Klasse `Book` eindeutig ist, sollen sämtliche Zugriffe, die ein eindeutiges Attribut voraussetzen, diese id verwenden.
In diesem Interface werden die Methoden für die Datenbankzugriffe definiert. 

Auf Basis des Methodennamens kann Spring eine Methode generieren, die eine dem Methodennamen entsprechende SQL-Query ausführt.
Beispielsweise wird aus dem Methodennamen
```java
Book findByTitleAndAuthor(String title, String author);
```

die SQL-Query

```sql
SELECT * FROM BOOKS WHERE title = ? and author = ?.
```

Das Interface `BookstoreRepository` soll außerdem von dem Interface `CrudRepository<{verwaltete Klasse}, {Datentyp des Identifiers}>` erben.

Verwaltete Klasse und Datentyp des Identifiers sind entsprechend mit der Entity-Klasse und dem Datentyp des mit `@Id` annotierten Attributes zu besetzen.
Das `BookstoreRepository` kann dann in den `BookstoreService` injiziert werden.
Viele der benötigten Zugriffsmethoden wie `findById()`, `save()` und `delete()` bringt das `BookstoreRepository` direkt mit, sie müssen also nicht implementiert werden.
Entsprechend müssen die Methoden des `BookstoreService` aktualisiert werden.

Außerdem bietet Spring Boot Data JPA eine bessere Möglichkeit, Testdaten anzulegen.
Anstatt Testdaten über die `init()`-Methode anzugeben, wird unter `src/main/resources` die Datei `data.sql` erstellt.
Hier werden via SQL-Statement Daten in die Tabelle `book` geschrieben.

Das Testen erfolgt wieder per Postman oder Insomnia.

### Einblick in die Datenbank
Um Einblicke in die Datenbank zu erhalten, wird unter `src/main/resources` eine `application.properties`-Datei angelegt (die Konfiguration kann auch über eine `application.yml`-Datei erfolgen, auf die yml-Syntax wird hier aber nicht eingegangen).
Dort wird folgender Eintrag hinzugefügt:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
 
spring.h2.console.enabled=true
```
Unter [http://localhost:8080/h2-console](http://localhost:8080/h2-console) kann dann die Datenbank mit den oben angegebenen Daten aufgerufen werden.

### Persistenz
Damit die Daten auch persistent gespeichert werden, kann die `spring.datasource.url` angepasst werden.
mit `jdbc:h2:~/bookstore` wird im Home-Verzeichnis des Nutzers eine Datei `bookstore.mv.db` angelegt, in der die Daten gespeichert werden.
Anstelle des Home-Verzeichnisses kann natürlich auch ein anderes Verzeichnis gewählt werden.

## Task 5: Frontend
Jetzt wird ein Frontend mit Thymeleaf angelegt. Dazu sind Kenntnisse in HTML und CSS notwendig.

### Tutorials
Zum Auffrischen oder Erwerben dieser Kenntnisse sind folgende Tutorials empfehlenswert. Außerdem sollten die Tutorials zu Spring Boot mit Thymeleaf durchgearbeitet werden.

* [HTML](https://www.w3schools.com/html/html_intro.asp)
* [CSS](https://www.w3schools.com/css/css_intro.asp)
* [Thymeleaf](https://spring.io/guides/gs/serving-web-content/)
* [Thymeleaf Form Handling](https://spring.io/guides/gs/handling-form-submission/)

### Aufgaben
Folgende Seiten sollen angelegt werden:

* Anzeigen aller Bücher in einer Tabelle mit Link zur jeweiligen Detailansicht und Link zum Erstellen eines Buchs
* Detailansicht eines Buchs mit Links zum Bearbeiten des Buchs und zum Löschen des Buchs
* Erstellen eines neuen Buchs
* Bearbeiten eines bestehenden Buchs
* Optional kann das Bearbeiten und Erstellen von Büchern auch in einer Seite gemacht werden.
  Zur Gestaltung der Seiten kann Bootstrap verwendet werden.

Damit die REST-Schnittstelle weiterhin erreichbar bleibt wird dazu eine neue mit `@Controller` annotierte Klasse, der `ViewController`, angelegt.
Hier werden alle Endpunkte für Thymeleaf bereitgestellt.

## Task 6: Form Validation
Nachdem Bücher jetzt über eine Web-Oberfläche erstellt werden können, soll gewährleistet werden, dass nur valide Bücher erstellt werden können.
Dazu wird die Form-Validation von Spring Boot Thymeleaf verwendet.
Ein Tutorial dazu findet sich [hier](https://spring.io/guides/gs/validating-form-input/).
Folgende Aspekte sollen validiert werden:

* Der Titel muss mindestens 2 und maximal 30 Zeichen lang sein
* Der Name des Autors muss mindestens 2 und maximal 20 Zeichen lang sein.
* Das Erscheinungsjahr soll nach 1000 n. Chr. und vor 2019 n. Chr. liegen.
* Der Preis muss mindestens 1 € betragen.
* Die Kombination aus Titel und Autor soll einmalig sein. 

Wenn die Eingaben valide wahren, soll auf die Übersichtsseite weitergeleitet werden, waren die Eingaben ungültig, soll dies auf der Seite mit entsprechenden Meldungen an den Inputfeldern und am Anfang des Formulars gemeldet werden.

## Task 7: Spring Profiles einsetzen
Als Nächstes wird das Interface `PaymentService` erstellt.
Dies enthält eine Methode `pay(double amount)`.
Die Methode `pay()` soll einen Bezahlvorgang durch eine Konsolenausgabe simulieren.
Die Klassen `PaypalPayment` und `DummyPayment` sind Beans und implementieren jeweils dieses Interface.
Es wird sowohl der Betrag, als auch der Name der Klasse, deren `pay()`-Methode ausgeführt wird, ausgegeben.
Im `BookstoreService` wird eine Methode `buyBookById(Long id)` erstellt, die die `pay()`-Methode des `PaymentService` aufruft.
Entsprechend muss ein `PaymentService` injiziert werden.
Welche Implementierung des `PaymentService` verwendet wird, soll per Systemparameter in Gradle übergeben werden.
Dazu wird die Datei `build.gradle` wie folgt ergänzt:

```build.gradle
def activeProfile = 'paypal'
bootRun {
    systemProperty "spring.profiles.active", activeProfile
}
```

Ein Tutorial dazu findet sich [hier](https://www.baeldung.com/spring-profiles).
Ebenfalls wird im Frontend ein Button für das Kaufen eines Buchs angelegt. 

## Task 8: Dockerize
Als Nächstes wird [Docker](https://www.docker.com/) verwendet, um die einzelnen Anwendungsbestandteile separat voneinander zu starten.
Für dieses Kapitel werden Grundkenntnisse im Umgang mit Docker vorausgesetzt, ein Einstieg in Docker liegt nicht im Scope dieses Artikels.

Als Erstes wird eine Datei `Dockerfile` erstellt.
Diese Datei dient dazu, mit Docker ein Image zu erstellen, das genutzt werden kann, um einen Container zu starten, der die Spring-Anwendung hochfährt.

Als Basis wird ein Alpine-Linux mit OpenJDK-8 verwendet.
In dieses Image wird das generierte JAR aus dem Ordner `./build/libs/` kopiert.
Der Einfachheit halber kann es beim Kopieren in `app.jar` umbenannt werden.
Als Einstieg wird dann der Befehl `java -jar /app.jar` verwendet.

Mit dem Dockerfile könnte jetzt schon die Spring-Boot-Anwendung gestartet werden.
Es wird dabei weiterhin die In-Memory-Datenbank H2 verwendet.

Als Nächstes wird diese Datenbank auf eine [MariaDB](https://mariadb.org/) umgestellt.
Allerdings wird die Datenbank nicht auf dem lokalen System installiert, sondern über einen Docker Container bereitgestellt, dazu aber später mehr.
Dazu wird in der Datei `build.gradle` die Dependency von H2 entfernt und stattdessen folgende Dependency eingefügt:

```groovy
implementation 'org.mariadb.jdbc:mariadb-java-client:3.0.6'
```

Danach müssen noch die Konfigurationen in der Datei `application.properties` angepasst werden.
Hier bleiben folgende Konfigurationen erhalten:

```properties
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MariaDB102Dialect
spring.jpa.hibernate.ddl-auto=update
```

Der Driver und der Hibernate-Dialekt wurden hier auf MariaDB umgestellt.
Damit die Daten nicht immer erneut angelegt werden müssen, wird `ddl-auto` auf `update` gestellt.

Damit die MariaDB nicht lokal installiert werden muss, wird nun ein entsprechender Docker Container angelegt.
Damit auch die Docker Container nicht immer manuell gestartet werden müssen, wird zur Verwaltung der Container [Docker Compose](https://docs.docker.com/compose/) verwendet.
Entsprechend wird nun die Datei `docker-compose.yml` erstellt, in die die Container eingetragen werden.

Der erste Container, der über die `docker-compose.yml` verwaltet wird, ist der Container der Spring-Anwendung.
Dieser muss jedes Mal neu gebaut werden.
Als Kontext wird über `.` der aktuelle Ordner angegeben.
Dadurch sucht Docker Compose im aktuellen Ordner nach einem `Dockerfile`.
Heißt die Datei nicht `Dockerfile`, kann über den Parameter `dockerfile` der Name des Dockerfile angegeben werden.
Damit der Container bei einem Absturz automatisch erneut gestartet wird, wird der Parameter `restart` auf `on-failure` gesetzt.
Ein solcher Absturz kann beispielsweise auftreten, wenn die Datenbank, nicht verfügbar ist.
Dies kann beim ersten Starten der Fall sein, da die Datenbank ein paar Sekunden zum Hochfahren benötigt.
Zusätzlich muss dem Container mitgegeben werden, dass er allen Traffic, der am Port `8080` des Containers ankommt, nach innen weiterleiten soll.
Als Letztes werden für diesen Container noch einige Umgebungsvariablen definiert.
Hierbei ist die Benennung der Variablen wichtig, da Spring direkt die Werte aus diesen Umgebungsvariablen in der Konfiguration verwenden kann.
Folgende Variablen werden konfiguriert:

* `SPRING_DATASOURCE_URL`: Die URL, unter der die Datenbank erreicht wird.
  Als Wert wird hier `jdbc:mariadb://db:3306/bookstore` eingetragen.
  `db` entspricht dabei dem Namen des Datenbankeintrags in der `docker-compose.yml`.
  Dadurch kann automatisch der entsprechende Container angesprochen werden.
  Wird stattdessen `localhost` angegeben, referenziert das den `localhost`-Eintrag des Container der Spring-Anwendung.
  Über diesen Eintrag kann natürlich keine Datenbank erreicht werden, weil die Datenbank nicht im selben Container läuft, wie die Spring-Anwendung.
* `SPRING_DATASOURCE_USERNAME`: Der Nutzername des Datenbanknutzers, der für den Login benötigt wird.
  Für dieses Testprojekt kann der Nutzer `root` verwendet werden, auf einer produktiven Datenbank sollte hier ein Nutzer verwendet werden, der nur die notwendigen Berechtigungen hat.
* `SPRING_DATASOURCE_PASSWORD`: Das Passwort des Datenbanknutzers, das für den Login benötigt wird.
  Dieses Passwort wird im Anschluss gesetzt.
  In diesem Fall kann das Datenbankpasswort im Klartext in die Umgebungsvariable geschrieben werden.
  Sollten Dritte allerdings Zugriff auf den Code oder die Datenbank haben, darf das Passwort nicht in Git auftauchen, stattdessen sollten diese Werte über Umgebungsvariablen gesetzt werden.
* `SPRING_PROFILES_ACTIVE`: Das zu verwendende Spring-Profil (vgl. [Spring-Profiles](#task-7-spring-profiles-einsetzen)).

Neben dem Spring-Backend wird auch noch ein Container für die Datenbank benötigt.
Das Image kann vom zentralen Docker Hub bezogen werden.
Dazu wird als Image `mariadb` angegeben, Docker lädt dann ein entsprechendes Image automatisch herunter, wenn es nicht bereits heruntergeladen wurde.
Auch hier muss aller Traffic, der auf Port `3306` des Containers ankommt, nach innen weitergeleitet werden.
Darüber hinaus müssen noch folgende Umgebungsvariablen gesetzt werden:

* `MARIADB_ROOT_PASSWORD`: Das Root-Passwort der Datenbank.
  Für diese Testdatenbank kann dieses Passwort direkt in der `docker-compose.yml` gesetzt werden.
  Sollten Dritte allerdings Zugriff auf den Code oder die Datenbank haben, darf das Passwort nicht in Git auftauchen, stattdessen sollten diese Werte über Umgebungsvariablen gesetzt werden.
  Das verwendete Passwort sollte in dem Fall auch aktuelle Sicherheitsrichtlinien für Passwörter erfüllen.
* `MARIADB_DATABASE`: Dies ist der Name des Datenbankschemas, das beim initialen Starten der Datenbank erstellt wird.
  Dieses Schema wird in der Umgebungsvariable `SPRING_DATASOURCE_URL` als letzter Teil der URL verwendet.

Zu guter letzt kann noch ein Volume definiert werden, damit die Daten aus der Datenbank auch das Löschen des Datenbank-Containers überstehen.
Der entsprechende Ordner im Docker Container ist `/var/lib/mysql`.
Dieses Volume kann beispielsweise mit dem Ordner `data` im aktuellen Projekt verknüpft werden.
Dieser Ordner sollte der `.gitignore` hinzugefügt werden, sodass er nicht versehentlich committed wird.

Über das so erstellte `docker-compose.yml` können dann Datenbank und Backend gestartet werden, das Frontend ist, wie gewohnt, unter [localhost:8080](http://localhost:8080/) erreichbar.
