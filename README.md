# 使用说明

## 前置
需要在用到插件的地方加入swagger和lombok
```
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>${swagger.version}</version>
    </dependency>
    
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>${swagger.version}</version>
    </dependency>
    
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>${lombok.version}</version>
      <scope>provided</scope>
    </dependency>
```
## 安装
mvn clean install

## 使用
把打包好的java放到在别的项目里，例子如下 `${project.basedir}/lib/cdxt-mybatis-generator.jar`

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
在项目里有个generatorConfig.xml，可以参照。
最终例子如下:
```
package com.cdxt.identity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户注册表
 * 实体类对应的数据表为： RMC_USER_REG
 * @author Administrator
 * @date 2019-07-19 14:04:49
 */
@ApiModel(value = "RmcUserReg")
@Data
public class RmcUserReg implements Serializable {
    /**
     * ID
     */
    @NotBlank(message = "ID不能为空")
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 登陆密码
     */
    @NotBlank(message = "登陆密码不能为空")
    @ApiModelProperty(value = "登陆密码")
    private String password;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    private static final long serialVersionUID = 1L;
}
```
