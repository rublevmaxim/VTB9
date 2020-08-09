package com.geekbrains.lesson9;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Processor {
    private static Connection cn;
    private static Statement st;
    private static PreparedStatement pr_st;

    private static char dm= (char) 34;//""-символ

    public static void main(String[] args) {
        try {
            connect();
            create_table(BD.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            disconnect();
        }
    }




    public static void create_table(Class cl) {
        if (!cl.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("Fuck!");
        }
        Map<Class, String> map = new HashMap<>();
        map.put(int.class, "INTEGER");
        map.put(String.class, "TEXT");

        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(((Table) cl.getAnnotation(Table.class)).title());
        sql.append(" (");
        Field[] fields = cl.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Column.class)) {
                sql.append(f.getName())
                        .append(" ")
                        .append(map.get(f.getType()))
                        .append(", ");
            }
        }
        sql.setLength(sql.length() - 2);// Отпилим последнюю запятую
        sql.append(");");

        System.out.println(sql);
        try {
            st.executeUpdate(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            cn= DriverManager.getConnection("jdbc:sqlite:maindb.db");
            st=cn.createStatement();


        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Unable to connect");
        }

    }

    public static void disconnect() {
        try {
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
