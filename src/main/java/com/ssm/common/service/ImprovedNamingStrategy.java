package com.ssm.common.service;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class ImprovedNamingStrategy implements PhysicalNamingStrategy {
    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convert(identifier, 0);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convert(identifier, 0);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convert(identifier, 1);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convert(identifier, 0);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convert(identifier, 0);
    }

    /* springboot的话自带有实现类，具体参考https://blog.csdn.net/J080624/article/details/84790421 */
    private Identifier convert(Identifier identifier, int isTable) {
        if (identifier == null || identifier.getText() == null || identifier.getText().equals("")) {
            return identifier;
        }

        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        String newName = null;
        /* Linux下mysql是区分大小写的，这里表名统一生产大写 */
        if(isTable == 1){
            newName = identifier.getText().replaceAll(regex, replacement).toUpperCase();
        }else{
            newName = identifier.getText().replaceAll(regex, replacement).toLowerCase();
        }
        return Identifier.toIdentifier(newName);
    }

}
