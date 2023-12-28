package com.util;

// 컬럼명을 소문자로 변환해주는 클래스
public class ConvertLowerColumnName {
	public static void main(String[] args) {
		String descEmp = "SEQ       NOT NULL NUMBER        \r\n"
				+ "WRITER    NOT NULL VARCHAR2(20)  \r\n"
				+ "PWD       NOT NULL VARCHAR2(20)  \r\n"
				+ "EMAIL              VARCHAR2(100) \r\n"
				+ "TITLE     NOT NULL VARCHAR2(200) \r\n"
				+ "WRITEDATE          DATE          \r\n"
				+ "READED             NUMBER        \r\n"
				+ "TAG                NUMBER(1)     \r\n"
				+ "CONTENT            CLOB     ";
		String lowerDescEmp = descEmp.toLowerCase();
		System.out.println(lowerDescEmp);
		String regex = "\\s+.+\\s*";
//		lowerDescEmp = lowerDescEmp.replaceAll(regex, ";\r\n");
//		System.out.println(lowerDescEmp);
		
		
		descEmp.lines().map(s -> s.toLowerCase().replaceAll(regex, ";\r\n")).forEach(System.out::println);
	}
	
	
}
