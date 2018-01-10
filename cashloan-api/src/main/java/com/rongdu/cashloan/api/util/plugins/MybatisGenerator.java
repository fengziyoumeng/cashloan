
package com.rongdu.cashloan.api.util.plugins;

import org.mybatis.generator.api.ShellRunner;


/**
 * @author heroy
 *
 */
public class MybatisGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String config = MybatisGenerator.class.getClassLoader().getResource("config/mybatis-generator/generatorConfig.xml").getFile();
		String[] arg = { "-configfile", config, "-overwrite" };
		ShellRunner.main(arg);
		System.out.println("生成成功");
	}
}
