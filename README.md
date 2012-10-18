swagger-standalone-generator
============================

A standalone Java annotation processor that generates Swagger json. This application is intended to be run as part
of your Maven build, to produce swagger spec artifacts.

Example usage:
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>apt-maven-plugin</artifactId>
            <version>1.0-alpha-5</version>
            <configuration>
                <factory>com.wotifgroup.swaggerjsongenerator.SwaggerJsonGeneratorAPFactory</factory>
                <options>
                    <option>apiVersion=1.0</option>
                </options>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>process</goal>
                    </goals>
                </execution>
            </executions>
            <dependencies>
                <dependency>
                    <groupId>com.wotifgroup</groupId>
                    <artifactId>swagger-json-generator</artifactId>
                    <version>2012.07.19</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>