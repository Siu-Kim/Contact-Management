package Main;



public class NormalContact extends ContactInfo{
    
    private String relation;
    private String name;
    private String phonenum;
    
    NormalContact(String name_Input, String phonenum_Input, String relation_user){
        super(name_Input, phonenum_Input);
        this.name = name_Input;
        this.phonenum = phonenum_Input;
        relation = relation_user;
    }
    
    protected String getInfo(){
		return this.toString();
    }
    /*toString 메서드는 보통 디버깅을 위해 Overriding되며, 로깅, 에러메세지 출력 등에도 사용 가능. 
    그러나 이러한 용도 외의 프로덕션 코드로는 toString이 아닌 별도로 정의된 메서드를 사용하는 것이 
    설계 철학을 위배하지 않는 방향으로 이해할 수 있다
    */
    public String toString(){
		StringBuilder strContactInfo = new StringBuilder();
        strContactInfo.append("name: ").append(this.name).append(" / ");
        strContactInfo.append("phone number: ").append(this.phonenum).append(" / ");
        strContactInfo.append("relation: ").append(this.relation);
        
        return strContactInfo.toString();       
    }
	
	protected String getContactType(){
		return "NormalContact";
	}
	
	boolean matches(ContactAttribute attribute, String query){
		switch(attribute){
			case NAME:
				return this.name.equals(query);
			case PHONE_NUMBER:
				return this.phonenum.equals(query);
			case RELATION:
				return this.relation.equals(query);
			default:
				return false;	
		}
	}
		    
	String getRelation(){
		return this.relation;
	}
	
	void setInfo(ContactAttribute attribute, String query){
		switch(attribute){
			case NAME:
				this.setName(query);
				break;
			case PHONE_NUMBER:
				this.setPhoneNumber(query);
				break;
			case RELATION:
				this.setRelation(query);
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
	void setRelation(String relation_Input){
		this.name = relation_Input;
		return;
	}
}
