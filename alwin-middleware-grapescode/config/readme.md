**Konfiguracja alwin**

1. alwin.properties - Plik musi znajdować się w tej samej lokalizacji co standalone.xml. Służy do przechowywania zewnętrznych zmiennych konfigurujących 
aplikację per środowisko

2. standalone.xml

    a) Baza danych
    
        Należy dodać moduł ze sterownikami do bazy danych  postgres. 
        Rozpakować plik wildfly-postgresql-module.zip w katalogu modules wildly. Po rozpakowaniu katalog modules powinien zawierać katalog org/postgres/main z plikami module.xml oraz postgresql-9.4.1207.jar.
        
        W standalone.xml należy dodać bazę danych ALWIN w sekcji: 
        <subsystem xmlns="urn:jboss:domain:datasources:4.0"><datasources>
        
        <datasource jta="true" jndi-name="java:jboss/datasources/AlwinDS" pool-name="AlwinDS" enabled="true" use-ccm="false">
            <connection-url>jdbc:postgresql://localhost:5432/alwin</connection-url>
            <driver-class>org.postgresql.Driver</driver-class>
            <driver>postgresql</driver>
            <security>
                <user-name>alwin_admin</user-name>
                <password>xxxxxxxxxxxxxxxx</password>
            </security>
            <validation>
                <validate-on-match>false</validate-on-match>
                <background-validation>false</background-validation>
            </validation>
            <statement>
                <share-prepared-statements>false</share-prepared-statements>
            </statement>
        </datasource>
        
        W tym samym pliku standalone.xml należy dodać sterownik dla postgresqla w sekcji 
        <subsystem xmlns="urn:jboss:domain:datasources:4.0"><datasources><drivers>
        
        <driver name="postgresql" module="org.postgres">
            <xa-datasource-class>org.postgresql.Driver</xa-datasource-class>
        </driver>
    
    b)	Server smtp
    
        System potrafi wysyłać maile, dlatego należy ustawić poprawne dane serwera smtp. Konfiguracja znajduje się w pliku standalone.xml: 
        
        <outbound-socket-binding name="mail-smtp">
            <remote-destination host="localhost" port="25"/>
        </outbound-socket-binding>
    
    c)	Cross-Origin Resource Sharing
    
        Na wypadek, gdyby frontendowa część aplikacji ALWIN była zinstalowana na innym serwerze niż wildfy, należy dodać dodatkową konfigurację w standalone.xml.
        
        Należy dodać poniższe wpisy w sekcji: 
        <subsystem xmlns="urn:jboss:domain:undertow:3.1"><server name="default-server"><host name="default-host" alias="localhost">
        
        <filter-ref name="Access-Control-Allow-Origin"/>
        <filter-ref name="Access-Control-Allow-Methods"/>
        <filter-ref name="Access-Control-Allow-Headers"/>
        <filter-ref name="Access-Control-Allow-Credentials"/>
        <filter-ref name="Access-Control-Max-Age"/>
        
        Oraz w sekcji:
        <subsystem xmlns="urn:jboss:domain:undertow:3.1"><server name="default-server"><filters>
        
        <response-header name="Access-Control-Allow-Origin" header-name="Access-Control-Allow-Origin" header-value="*"/>
        <response-header name="Access-Control-Allow-Methods" header-name="Access-Control-Allow-Methods"
                         header-value="GET, POST, OPTIONS, PUT, PATCH, DELETE"/>
        <response-header name="Access-Control-Allow-Headers" header-name="Access-Control-Allow-Headers"
                         header-value="accept, authorization, content-type, x-requested-with"/>
        <response-header name="Access-Control-Allow-Credentials" header-name="Access-Control-Allow-Credentials" header-value="true"/>
        <response-header name="Access-Control-Max-Age" header-name="Access-Control-Max-Age" header-value="1"/>
    
    d)	Activemq
    
        Należy dodać poniższa konfigurację w standalone.xml.
        
        W sekcji: <extensions>
        
        <extension module="org.wildfly.extension.messaging-activemq"/>
        
        W sekcji: <profile>
        
        <subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0">
            <server name="default">
                <security-setting name="#">
                    <role name="guest" send="true" consume="true" create-non-durable-queue="true" delete-non-durable-queue="true"/>
                </security-setting>
                <address-setting name="#" dead-letter-address="jms.queue.DLQ" expiry-address="jms.queue.ExpiryQueue" max-size-bytes="10485760" page-size-bytes="2097152" message-counter-history-day-limit="10"/>
                <http-connector name="http-connector" socket-binding="http" endpoint="http-acceptor"/>
                <http-connector name="http-connector-throughput" socket-binding="http" endpoint="http-acceptor-throughput">
                    <param name="batch-delay" value="50"/>
                </http-connector>
                <in-vm-connector name="in-vm" server-id="0"/>
                <http-acceptor name="http-acceptor" http-listener="default"/>
                <http-acceptor name="http-acceptor-throughput" http-listener="default">
                    <param name="batch-delay" value="50"/>
                    <param name="direct-deliver" value="false"/>
                </http-acceptor>
                <in-vm-acceptor name="in-vm" server-id="0"/>
                <jms-queue name="ExpiryQueue" entries="java:/jms/queue/ExpiryQueue"/>
                <jms-queue name="DLQ" entries="java:/jms/queue/DLQ"/>
                <jms-queue name="emailQueue" entries="java:/jms/queue/email java:jboss/jms/queue/email"/>
                <connection-factory name="InVmConnectionFactory" entries="java:/ConnectionFactory" connectors="in-vm"/>
                <connection-factory name="RemoteConnectionFactory" entries="java:jboss/exported/jms/RemoteConnectionFactory" connectors="http-connector"/>
                <pooled-connection-factory name="activemq-ra" entries="java:/JmsXA java:jboss/DefaultJMSConnectionFactory" connectors="in-vm" transaction="xa"/>
            </server>
        </subsystem>
        
        W sekcji: <subsystem xmlns="urn:jboss:domain:ejb3:4.0">
        
        <mdb>
            <resource-adapter-ref resource-adapter-name="${ejb.resource-adapter-name:activemq-ra.rar}"/>
            <bean-instance-pool-ref pool-name="mdb-strict-max-pool"/>
        </mdb>
    
    e)	 Domyślna długość transakcji
    
        Alwin tworzy zlecenia na podstawie danych z Leo. Podczas pierwszego uruchomienia może się okazać że jest dużo zleceń do utworzenia co spowoduje że domyślna długość transakci może okazać się za krótka. Można zmienić domyślną wartość poprzez dodanie poniższego wpisu w pliku standalone.xml w sekcji: <subsystem xmlns="urn:jboss:domain:transactions:3.0">
        
        <coordinator-environment default-timeout="3600"/>
    
    f) Na potrzeby wczytania zewnętrznych zmiennych konfigurujacych należy dodać
        
        <system-properties>
                <property name="alwin.properties" value="${jboss.server.config.dir}/alwin.properties"/>
        </system-properties>
        
    g) Konfiguracja katalogu z załacznikami: 
    
       Dodanie wpisu w awin.properties
       
       attachments.dir=/opt/alwin/attachments
    
       W pliku stanalone.xml w sekcji <subsystem xmlns="urn:jboss:domain:undertow:3.1"><server name="default-server"> należy dodać wpis

       <location name="/attachments" handler="attachments"/>
      
       oraz w sekcji <subsystem xmlns="urn:jboss:domain:undertow:3.1"><handlers>
      
       <file name="attachments" path="/opt/alwin/attachments" directory-listing="false"/>
       
       Użytkownik, który uruchamia serwer wildfy powinien mieć uprawnienia do katalogu pozwalające mu tworzyć podkatalogi oraz pliki w podkatalogach.
       Z treści pliku jest liczone md5 i tworzony katalog z pierwszych dwóch znaków, w nim jest zapisywany załącznik (przykład pełnej ścieżki zapisanego 
       załącznika: /opt/alwin/attachments/fa/b366d45d-a7c5-42ff-adaf-54fb13066d61.pdf)
    