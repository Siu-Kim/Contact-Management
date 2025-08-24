package main.java;

public class ClubContact extends ContactInfo{
    private String clubName;
    private String name;
    private String phonenum;
    
    ClubContact(String name_Input, String phonenum_Input, String clubName_Input){
        super(name_Input, phonenum_Input);
        this.name = name_Input;
        this.phonenum = phonenum_Input;       
        clubName = clubName_Input;
    }
    
    protected String[] getInfo(){
		String contactElement[] = new String[3];
		contactElement[0] = this.name;
		contactElement[1] = this.phonenum;
		contactElement[2] = this.clubName;

		return contactElement;
    }

    
    public String toString(){
        StringBuilder strContactInfo = new StringBuilder();
        strContactInfo.append("name: ").append(this.name).append(" / ");
        strContactInfo.append("phone number: ").append(this.phonenum).append(" / ");
        strContactInfo.append("club name: ").append(this.clubName).append('\n');
        
        return strContactInfo.toString();
    }
	
	protected ContactAttribute getContactType(){
		return ContactAttribute.CLUB_NAME;
	}
	
	boolean matches(ContactAttribute attribute, String query){
		switch(attribute){
			case NAME:
				return this.name.equals(query);
			case PHONE_NUMBER:
				return this.phonenum.equals(query);
			case CLUB_NAME:
				return this.clubName.equals(query);
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
	String getClubName(){
		return this.clubName;
	}
	
	void setInfo(ContactAttribute attribute, String query){
		switch(attribute){
			case NAME:
				this.setName(query);
				break;
			case PHONE_NUMBER:
				this.setPhoneNumber(query);
				break;
			case CLUB_NAME:
				this.setClubName(query);
				break;
		}
		return;
	}
	void setName(String name_Input){
		this.name = name_Input;
		return;
	}
	void setPhoneNumber(String phonenum_Input){
		this.phonenum = phonenum_Input;
		return;
	}
	void setClubName(String clubName_Input){
		this.clubName = clubName_Input;
		return;
	}
}
