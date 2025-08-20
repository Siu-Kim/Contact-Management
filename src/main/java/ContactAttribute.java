package main.java;

import java.util.Arrays;

public enum ContactAttribute {
	NAME(1, "name"),
	PHONE_NUMBER(2, "phone number"),
	RELATION(3, "relation"),
	CLUB_NAME(4, "club name"),
	DEPARTMENT(5, "department");
	
	private int attributeCount;
	private String attributeStringFormat;

	private ContactAttribute(int attributeCount, String attributeStringFormat){
		this.attributeCount = attributeCount;
		this.attributeStringFormat = attributeStringFormat;
	}

	public int getAttributeCount(){
		return attributeCount;
	}

	public String getAttributeStringFormat(){
		return attributeStringFormat;
	}

	public static ContactAttribute findByAttributeCount(int attributeCount){ // attributeCount를 받아 해당하는 ContactAttribute 객체를 반환(value -> key)
		return Arrays.stream(ContactAttribute.values())
				.filter(attribute -> attributeCount == attribute.attributeCount)
				.findFirst().orElseThrow(() -> new IllegalArgumentException
				("Cannot find this attribute count... Please Try Again."));
	}

	public static ContactAttribute findByAttributeStringFormat(String attributeStringFormat){ // attributeStringFormat를 받아 해당하는 ContactAttribute 객체를 반환(value -> key)
		return Arrays.stream(ContactAttribute.values())
				.filter(attribute -> attributeStringFormat.equals(attribute.attributeStringFormat))
				.findFirst().orElseThrow(() -> new IllegalArgumentException
				("Cannot find this attribute string... Please Try Again."));
	}
}
