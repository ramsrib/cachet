# cachet [![Build Status](https://travis-ci.org/ramsrib/cachet.png?branch=master)](https://travis-ci.org/ramsrib/cachet)
======

An Abstract Caching Framework

####Features :
* Provides an wrapper for caching (like slf4j for logging).
* Supports the Ehcache, Infinispan & Memcached caching libraries.

####Goals :
* Provide support for existing caching frameworks like JCS & Hazelcast.
* Provide support to cache configuration API.
* Provide support for Tree Cache API.
* Implement high-performing Tree like Caching.


####How to use it?

1) Add the "cachet" as dependency to your maven project (pom) xml file.
```html
    <dependency>
        <groupId>org.cachet</groupId>
        <artifactId>cachet</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```
2) Add cachet's maven repository to your maven project (pom) xml file.

```html
    <repositories>
        <repository>
            <id>cachet-snapshots</id>
            <url>https://raw.github.com/ramsrib/cachet/mvn-repo/maven/snapshots</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
        <repository>
            <id>cachet-releases</id>
            <url>https://raw.github.com/ramsrib/cachet/mvn-repo/maven/releases</url>
        </repository>
    </repositories>
```

3) Start using the Cache API in your code.
