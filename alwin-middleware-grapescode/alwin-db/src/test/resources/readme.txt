How to debug arquillian tests?

1. Make sure arquillian.xml has debug vm options with suspend:

<?xml version="1.0" encoding="UTF-8"?>
<arquillian xmlns="http://jboss.org/schema/arquillian" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="
        http://jboss.org/schema/arquillian
        http://jboss.org/schema/arquillian/arquillian_1_0.xsd">

    <defaultProtocol type="Servlet 3.0" />

    <container qualifier="widlfly-managed" default="true">
        <configuration>
            <property name="jbossHome">${jbossHome:target/wildfly-10.1.0.Final}</property>
            <property name="javaVmArguments">-Xmx512m -XX:MaxPermSize=128m
                -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y
            </property>
        </configuration>
    </container>
</arquillian>


2. Create remote debug configuration for localhost:8787

3. Have fun
