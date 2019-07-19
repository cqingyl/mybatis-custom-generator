package com.cdxt;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author yanglong
 * @ClassName:
 * @Description:
 * @date 2019/6/24
 * @lastModified： yanglong
 * @lastModifiedDate： 2019/6/24
 */
public class SwaggerPlugin extends PluginAdapter {


    /**
     * @param warnings list of warnings
     * @return always true
     */
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * Intercepts base record class generation
     *
     * @param topLevelClass     the generated base record class
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return always true
     */
    @Override
    public boolean modelBaseRecordClassGenerated(
            TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable
    ) {
        addAnnotations(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        // 如果是包含DATE字段，则格式化时间格式
        if (field.getType().equals(FullyQualifiedJavaType.getDateInstance())) {
            field.addAnnotation("@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")");
            field.addAnnotation("@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
        }
        String remarks = introspectedColumn.getRemarks();
        // 添加swagger实体字段说明
        field.addAnnotation("@ApiModelProperty(value = \"" + remarks + "\")");

        return true;
    }

    /**
     * Adds the swagger annotations' imports and annotations to the class
     *
     * @param topLevelClass the partially implemented model class
     */
    private void addAnnotations(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        // 获取Data类型字段
        List<Field> fieldDataList = topLevelClass.getFields()
                .stream()
                .filter(f -> f.getType().equals(FullyQualifiedJavaType.getDateInstance()))
                .collect(toList());

        // 有Data类型字段，则导包
        if (!fieldDataList.isEmpty()) {
            topLevelClass.addImportedType("org.springframework.format.annotation.DateTimeFormat");
            topLevelClass.addImportedType("com.fasterxml.jackson.annotation.JsonFormat");
        }
        // 添加swagger包
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");

        //获取实体类名称
        String entityName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        topLevelClass.addJavaDocLine("@ApiModel(value =\"" + entityName + "\")");
    }
}