package Main;

public class DepartmentContact extends ContactInfo{
    private String department;
    private String name;
    private String phonenum;
    
    DepartmentContact(String name_Input, String phonenum_Input, String depart_Input){
        super(name_Input, phonenum_Input);
        this.name = name_Input;
        this.phonenum = phonenum_Input;
        department = depart_Input;
    }
    
    protected String getInfo(){
    	return this.toString();   
    }
    
    
    public String toString(){
        StringBuilder strContactInfo = new StringBuilder();
        strContactInfo.append("name: ").append(this.name).append(" / ");
        strContactInfo.append("phone number: ").append(this.phonenum).append(" / ");
        strContactInfo.append("department: ").append(this.department).append('\n');
        
        return strContactInfo.toString();
    }
    protected ContactAttribute getContactType(){
		return ContactAttribute.DEPARTMENT;
	}
	
	boolean matches(ContactAttribute attribute, String query){
		switch(attribute){
			case NAME:
				return this.name.equals(query);
			case PHONE_NUMBER:
				return this.phonenum.equals(query);
			case CLUB_NAME:
				return this.department.equals(query);
			default:
				return false;	
		}
	}
	
	String getName(){
		return this.name;
	}
	String getPhoneNumber(){
		return this.phonenum;
	}
	String getDepartment(){
		return this.department;
	}
	
	void setInfo(ContactAttribute attribute, String query){
		switch(attribute){
			case NAME:
				this.setName(query);
				break;
			case PHONE_NUMBER:
				this.setPhoneNumber(query);
				break;
			case DEPARTMENT:
				this.setDepartment(query);
				break;
		}
		return;
	}
	
	void setName(String name_Input){
		this.name = name_Input;
		return;
	}
	void setPhoneNumber(String phonenum_Input){
		this.name = phonenum_Input;
		return;
	}
	void setDepartment(String department_Input){
		this.department = department_Input;
		return;
	}
}
