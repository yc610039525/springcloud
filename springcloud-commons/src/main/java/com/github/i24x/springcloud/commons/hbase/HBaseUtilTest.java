package com.github.i24x.springcloud.commons.hbase;

public class HBaseUtilTest {

	 public static void main(String[] args) {
	        testConnection();
	    }

	    private static void testConnection() {
	        String tableName1="t_student_ext";
	        String tableName2="t_student_info_def";
	        String []columnFamily1={"基本信息","家庭信息"};
	        String []columnFamily2={"基本信息","班级信息"};
	        HBaseClientAPI.dropTable(tableName1);
	        HBaseClientAPI.dropTable(tableName2);
	        HBaseClientAPI.creatTable(tableName1, columnFamily1);
	        HBaseClientAPI.creatTable(tableName2, columnFamily2);
	        
	        HBaseClientAPI.insert(tableName1, "1001", columnFamily1[0], "姓名", "zhangsan");
	        HBaseClientAPI.insert(tableName1, "1002", columnFamily1[0], "姓名", "lisi");
	        HBaseClientAPI.insert(tableName1, "1001", columnFamily1[1], "年龄", "18");
	        HBaseClientAPI.insert(tableName1, "1002", columnFamily1[1], "年龄", "20");

	        HBaseClientAPI.insert(tableName2, "1001", columnFamily2[0], "电话", "123456");
	        HBaseClientAPI.insert(tableName2, "1002", columnFamily2[0], "电话", "234567");
	        HBaseClientAPI.insert(tableName2, "1001", columnFamily2[1], "邮箱", "123@163.com");
	        HBaseClientAPI.insert(tableName2, "1002", columnFamily2[1], "邮箱", "234@163.com");

	        HBaseClientAPI.select(tableName1); //查询该表所有数据
//	        HBaseClientAPI.select(tableName1, "1001"); //根据表名和行健查询
//	        HBaseClientAPI.select(tableName2, "1002",columnFamily2[0]); //根据表名、行健和列族查询
//	        HBaseClientAPI.select(tableName2, "1002",columnFamily2[1],"mail"); //根据表名、行健、列族、和列查询
//
//	        HBaseClientAPI.select(tableName1, "1002"); //根据表名和行健查询
//	        HBaseClientAPI.delete(tableName1, "1002", columnFamily1[0]);//删除数据
//	        HBaseClientAPI.select(tableName1, "1002"); //根据表名和行健查询

	    }
	}