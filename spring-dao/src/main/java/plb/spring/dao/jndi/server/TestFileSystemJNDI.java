package plb.spring.dao.jndi.server;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestFileSystemJNDI {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws NamingException {
		@SuppressWarnings("rawtypes")
		Hashtable env = new Hashtable();
		String name = "F:\\fscontext-1_2-beta3.zip";
		env.put(Context.OBJECT_FACTORIES, "com.sun.jndi.fscontext.RefFSContextFactory");
		Context ctx = new InitialContext(env);
		Object obj = ctx.lookup(name);
		System.out.println("名称：[" + name + "]绑定的对象是:" + obj);
	}
}
