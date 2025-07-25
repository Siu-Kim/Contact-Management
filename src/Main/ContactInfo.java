package Main;

public abstract class ContactInfo {
    private String name;
    private String phonenum;
    
    ContactInfo(String name_Input, String phonenum_Input){
        name = name_Input;
        phonenum = phonenum_Input;
    }
    
    protected abstract String getInfo();
    public abstract String toString();
    protected abstract String getContactType();
	abstract boolean matches(ContactAttribute attribute, String query);
	abstract void setInfo(ContactAttribute attribute, String query);
	
	String getName(){
		return this.name;	
	}
	String getPhoneNumber(){
		return this.phonenum;
	}
	
	void setName(String name_Input){
		this.name = name_Input;
		return;
	}
	void setPhoneNumber(String phonenum_Input){
		this.name = phonenum_Input;
		return;
	}
	
}
