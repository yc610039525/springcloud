package com.github.springcloud.commons.hbase;

public class HBaseUtilTest {

	 public static void main(String[] args) {
	        test();
	    }

	    /**
	     * 一些测试
	     * @throws  
	     */
	    private static void test() {
	        String tableName1="t_student",tableName2="t_student_info";
	        String []columnFamily1={"st1","st2"};
	        String []columnFamily2={"stf1","stf2"};
	        HBaseUtil.creatTable(tableName1, columnFamily1);
	        System.out.println("table created");
//	        HBaseUtil.creatTable(tableName2, columnFamily2);

//	        HBaseUtil.insert(tableName1, "1001", columnFamily1[0], "name", "zhangsan");
//	        HBaseUtil.insert(tableName1, "1002", columnFamily1[0], "name", "lisi");
//	        HBaseUtil.insert(tableName1, "1001", columnFamily1[1], "age", "18");
//	        HBaseUtil.insert(tableName1, "1002", columnFamily1[1], "age", "20");
//
//	        HBaseUtil.insert(tableName2, "1001", columnFamily2[0], "phone", "123456");
//	        HBaseUtil.insert(tableName2, "1002", columnFamily2[0], "phone", "234567");
//	        HBaseUtil.insert(tableName2, "1001", columnFamily2[1], "mail", "123@163.com");
//	        HBaseUtil.insert(tableName2, "1002", columnFamily2[1], "mail", "234@163.com");
//
//	        HBaseUtil.select(tableName1); //查询该表所有数据
//	        HBaseUtil.select(tableName1, "1001"); //根据表名和行健查询
//	        HBaseUtil.select(tableName2, "1002",columnFamily2[0]); //根据表名、行健和列族查询
//	        HBaseUtil.select(tableName2, "1002",columnFamily2[1],"mail"); //根据表名、行健、列族、和列查询
//
//	        HBaseUtil.select(tableName1, "1002"); //根据表名和行健查询
//	        HBaseUtil.delete(tableName1, "1002", columnFamily1[0]);//删除数据
//	        HBaseUtil.select(tableName1, "1002"); //根据表名和行健查询

	    }
	}