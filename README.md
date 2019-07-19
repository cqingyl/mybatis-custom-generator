# 使用说明

## 安装
mvn clean install

## 使用
把打包好的java放到在别的项目里，例子如下
`${project.basedir}/lib/cdxt-mybatis-generator.jar`

放下之后再在pom.xml添加以下插件

```
      <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>${mybatis-generator.version}</version>
        <configuration>
          <verbose>true</verbose>
          <overwrite>false</overwrite>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>com.cdxt</groupId>
            <artifactId>mybatis-generator</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/cdxt-mybatis-generator.jar</systemPath>
          </dependency>
        </dependencies>
      </plugin>
```