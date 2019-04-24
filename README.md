# Maven Flatbuffers Plugin

A plugin that integrates flatbuffers compiler (flatc) into Maven lifecycle.
This is project is inspired by xolstice/protobuf-maven-plugin.

# Example usage
0. install the flatc-binary
1. Move your fbs-files to `src/main/flatbuffers`
2. Add this to your pom.xml
    ```
    <pluginRepositories>
        <pluginRepository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </pluginRepository>
    </pluginRepositories>
    
    <build>
        <plugins>
            <plugin>
                <groupId>com.github.leohilbert</groupId>
                <artifactId>flatbuffers-maven-plugin</artifactId>
                <version>1.0.1</version>
                <extensions>true</extensions>
                <configuration>
                    <flatcExecutable>flatc</flatcExecutable>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    ```
3. run `mvn clean compile`