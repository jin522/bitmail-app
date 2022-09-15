package cn.dayutec.pigeon.db.ver100;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BuildDB {
    public static String flag = "SQL100";

    public static void main(String[] args) {
        try (
                Connection conn = new DruidPoolUtil().getConn();
                Statement stmt = conn.createStatement()
        ) {
//           FIXME 刷库脚本使用完后保持注释状态，防止误刷，造成数据库数据丢失
            //删表
//            dropTables--(stmt);
            //建表
//            createTables(stmt);
            //更新表
//            updateTables(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateTables(Statement stmt) throws SQLException {
        switch (flag) {
            case "SQL200":
                System.out.println("200----------111");
                break;
        }

    }

    public static void dropTables(Statement stmt) throws SQLException {
        switch (flag) {
            case "SQL200":
                System.out.println("200----------111");
                break;
            case "SQL110":
                stmt.executeUpdate(CASESQL100.DROPMOVECAR);
                break;
        }
    }

    public static void createTables(Statement stmt) throws SQLException {
        switch (flag) {
            case "SQL200":
                System.out.println("200----------222");
                break;
            case "SQL110":
                stmt.executeUpdate(CASESQL100.CREATEMOVECAR);
                stmt.executeUpdate(CASESQL100.MOVECARCOMENT);
                break;

        }
    }

}
