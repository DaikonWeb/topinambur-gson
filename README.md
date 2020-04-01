# Topinambur Gson

![Topinambur](./logo.svg)

Topinambur Gson is a simple Topinambur's exension. The main goal is:
* Adds Json support to the Topinambur's response body


## How to add Topinambur Gson to your project
[![](https://jitpack.io/v/daikonweb/topinambur-gson.svg)](https://jitpack.io/#daikonweb/topinambur-gson)

### Gradle
- Add JitPack in your root build.gradle at the end of repositories:
```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

- Add the dependency
```
implementation 'com.github.DaikonWeb:topinambur-gson:1.2.4'
```

### Maven
- Add the JitPack repository to your build file
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
- Add the dependency
```
<dependency>
    <groupId>com.github.DaikonWeb</groupId>
    <artifactId>topinambur-gson</artifactId>
    <version>1.2.4</version>
</dependency>
```

## Getting Started
```
data class TestBody(val message: String)
val response = "https://some.api".http.get()

println(response.json<TestBody>())
```
