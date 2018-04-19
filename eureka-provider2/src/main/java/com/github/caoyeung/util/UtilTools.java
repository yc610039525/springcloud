package com.github.caoyeung.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Hashtable;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;


public class UtilTools {

    /**
     * 正则表达式数字验证
     * 
     * @author Long.Tang
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (str != null && !str.equals("")) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
            java.util.regex.Matcher match = pattern.matcher(str);
            return match.matches();
        } else {
            return false;
        }
    }

    /**
     * 字符串非空非null判断 long.tang
     */
    public static boolean isEmpty(String val) {
        if (val == null || val.equals("") || val.equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }
   
	 /**
     * 解码数据
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String deCodeStr(String text) throws UnsupportedEncodingException{
		String str="";
		if(!isEmpty(text)){
			str= URLDecoder.decode(text,"UTF-8");
		}
		return str;
	}

    
    public static void main(String[] args) {
    	
    	System.out.println("===>>"+ToDBC("ｈｅｌｌｏ"));
    	System.out.println("===>>"+ToSBC("hello"));
    	System.out.println("补:"+supplementNum("03",3));
	}
    
    
    /**
     * 补0
     * @package com.util.tools
     * @author long.tang
     * @date 2016-12-23
     * @method UtilTools.supplementNum()
     * @project h3c_dbs 
     * @return
     */
    public static String supplementNum(String str,int num){
    	if(str.length()<num){
    		str="0"+str;
    	}else{
    		return str;
    	}
    	return supplementNum(str,num);
    }
   
    
    
    /**
     * 使用java连接AD域
     * @author long.tang
     * @throws 异常说明
     * @param host 连接AD域服务器的IP
     * @param post AD域服务器的端口
     * @param username 用户名
     * @param password 密码
     * @return Integer 1 success 、0  false 、 -1 exception  
     */
    public static Integer connectAD(String host,String post,String username,String password) {
        DirContext ctx=null;
        int isLogin = 0;
        Hashtable<String,String> HashEnv = new Hashtable<String,String>();
        HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别(none,simple,strong)
        HashEnv.put(Context.SECURITY_PRINCIPAL, username!=null?username:""); //AD的用户名
        HashEnv.put(Context.SECURITY_CREDENTIALS, password!=null?password:""); //AD的密码
        HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
        HashEnv.put("com.sun.jndi.ldap.connect.timeout","3000");//连接超时设置为3秒
        HashEnv.put(Context.PROVIDER_URL," ldap://" + host + ":" + post);//默认端口389
        try {
        	if(password!=null&&!password.equals("")){
	            ctx = new InitialDirContext(HashEnv);//初始化上下文
	            //System.out.println("身份验证成功!");
	            isLogin = 1;
        	}else{
        		//System.out.println("身份验证失败!");
        		isLogin = 0;//没有输入密码属于身份失败
        	}
            
        } catch (AuthenticationException e) {
            //System.out.println("身份验证失败!");
            e.printStackTrace();
            isLogin = 0;
        } catch (javax.naming.CommunicationException e) {
            //System.out.println("AD域连接失败!");
            e.printStackTrace();
            isLogin = -1;
        } catch (Exception e) {
            //System.out.println("身份验证未知异常!");
            e.printStackTrace();
            isLogin = -1;
        } finally{
            if(null!=ctx){
                try {
                    ctx.close();
                    ctx=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return isLogin;
    }
    
	
	/**
	 * 半角转全角
	 * 
	 * @param input
	 *            String.
	 * @return 全角字符串.
	 */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            String.
	 * @return 半角字符串
	 */
	public static String ToDBC(String input) {

		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);

			}
		}

		return new String(c);
	}
	

}
