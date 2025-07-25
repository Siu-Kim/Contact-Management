package Main;
import java.util.Scanner;
import java.util.List;

public class CommandLineInterface {
	private Scanner sc;

	CommandLineInterface(){
		sc = new Scanner(System.in);
	}

	private String getString(){
		String strUserInput = sc.nextLine();
		
		return strUserInput;
	}
	private int getInteger(){
		int intUserInput = sc.nextInt();
		sc.nextLine(); //개행문자 제거를 위한 코드
		return intUserInput;
	}
	
	int getMainMenu(){
		StringBuilder strMainMenu = new StringBuilder();
		
		strMainMenu.append("----<Main Menu>----").append('\n')
			.append("1. Set size of totoal contacts\n")
			.append("2. Save the current contact to a file\n")
			.append("3. Load the saved contact file\n")
			.append("4. Register new contact\n")
			.append("5. Search contact\n")
			.append("6. Delete contact\n")
			.append("7. Edit contact\n")
			.append("8. View All Contacts\n")
			.append("9. Exit\n")
			.append("Select: ");
		
		System.out.print(strMainMenu);
		
		return getInteger();
	}

	int getSetSizeMenu(){
		StringBuilder strSetSizeMenu = new StringBuilder();
		
		strSetSizeMenu.append("Set Size...").append('\n')
			.append("enter the size of Storage\n")
			.append("Integer or '0'(inf)\n")
			.append("Enter: ");
		
		System.out.print(strSetSizeMenu);
		
		return getInteger();
	}

	int getCreateContactMenu(){
		StringBuilder strCreateContactMenu = new StringBuilder();

		strCreateContactMenu.append("Create contact...").append('\n')
			.append("1. normal contact\n")
			.append("2. club contact\n")
			.append("3. department contact\n")
			.append("Select: ");
		System.out.print(strCreateContactMenu);

		return getInteger();
	}
	
	String promptForName(){
		System.out.print("\tname: ");
		return getString();
	}
	String promptForPhoneNumber(){
		System.out.print("\tphone number: "); 
		return getString();
	}
	String promptForRelation(){
		System.out.print("\trelation: ");
		return getString();
	}
	String promptForClubName(){
		System.out.print("\tclub name: ");
		return getString();
	}
	String promptForDepartment(){
		System.out.print("\tdepartment: ");
		return getString();
	}

	int getSearchContactMenu(){
		StringBuilder strSearchContactMenu = new StringBuilder();
		
		strSearchContactMenu.append("Search contact...").append('\n')
			.append("Choose the variable\n")
			.append("1. name\n")
			.append("2. phone number\n")
			.append("3. relation\n")
			.append("4. club name\n")
			.append("5. department\n")
			.append("Select: ");
		
		System.out.print(strSearchContactMenu);
	
		return getInteger();
	}
	
	String promptForQuery(ContactAttribute attribute){
		switch(attribute){
			case NAME:
				return promptForName();
			case PHONE_NUMBER:
				return promptForPhoneNumber();
			case RELATION:
				return promptForRelation();
			case CLUB_NAME:
				return promptForClubName();
			case DEPARTMENT:
				return promptForDepartment();
			default:
				throw new RuntimeException("There is no contact with this query.");
		}
	}
	
	int getDeleteContactMenu(){
		StringBuilder strDeleteContactMenu = new StringBuilder();
		
		strDeleteContactMenu.append("Delete contact...").append('\n')
			.append("Choose the variable to search contacts you wanna delete\n")
			.append("1. name\n")
			.append("2. phone number\n")
			.append("3. relation\n")
			.append("4. club name\n")
			.append("5. department\n")
			.append("Select: ");
		
		System.out.print(strDeleteContactMenu);
	
		return getInteger();
	}
	
	int promptForDelete(){
		System.out.print("\nSelect the Contact number to delete: ");
		return getInteger();
	}
	
	boolean checkSurelyDelete(){
		//Delete 동작 전 마지막으로 삭제 여부를 확인. 삭제 선택 시 true를 반환
		System.out.println("Do you really wanna delete this Contact? Once you delete it, You cannot recover this information");
		System.out.println("\t0. No, I would not delete it!");
		System.out.println("\t1. Yes, I would delete it!");
		System.out.print("Select: ");
		
		int checker = getInteger();
		if(checker == 1){return true;}
		return false;
	}
	
	int getEditContactMenu(){
		StringBuilder strEditContactMenu = new StringBuilder();
		
		strEditContactMenu.append("Edit contact...").append('\n')
			.append("Choose the variable to Search\n")
			.append("1. name\n")
			.append("2. phone number\n")
			.append("3. relation\n")
			.append("4. club name\n")
			.append("5. department\n")
			.append("Select: ");
		
		System.out.print(strEditContactMenu);
	
		return getInteger();
	}

	
	
	int promptForEdit(){
		System.out.print("\nSelect the Contact number to Edit: ");
		return getInteger();
	}
	
	int promptForEditAttribute(String contactInfo, ContactAttribute attribute){
		System.out.printf("Contact Information\n");
		System.out.printf("\t%s\n", contactInfo);
		System.out.printf("\tSelect the variable to Edit\n");
		
		
		
		
		
		
	}
	
	void printContactInfo(List<String> strListContactInfo){
		System.out.printf("Contact Information\n");
		for(String strSearchInfo: strListContactInfo){
			int i = 0;
			System.out.printf("\t%d. %s\n", ++i, strSearchInfo);
		}
		
		return;
	}
	
	void printAllContactInfo(List<String> normalContacts, List<String> clubContacts, 
							 List<String> departmentContacts){
		StringBuilder sb = new StringBuilder();
		sb.append("Normal Contacts\n");
		//System.out.println("Normal Contacts");
		if(normalContacts.isEmpty()){
			//System.out.println("    No Contacts...");
			sb.append("\tNo Contacts...\n");
		}
		else{
			for(int i = 0; i <normalContacts.size(); i++){
				sb.append(String.format("\t%d. ", i+1)).append(normalContacts.get(i)).append('\n');
			}
		}
		
		sb.append("Club Contacts\n");
		if(clubContacts.isEmpty()){
			//System.out.println("    No Contacts...");
			sb.append("\tNo Contacts...\n");
		}
		else{
			for(int i = 0; i < clubContacts.size(); i++){
				sb.append(String.format("\t%d. ", i+1)).append(clubContacts.get(i)).append('\n');
			}
		}
		
		sb.append("Department Contacts\n");
		if(departmentContacts.isEmpty()){
			//System.out.println("    No Contacts...");
			sb.append("\tNo Contacts...\n");
		}
		else{
			for(int i = 0; i <departmentContacts.size(); i++){
				sb.append(String.format("\t%d. ", i+1)).append(departmentContacts.get(i)).append('\n');
			}
		}
		
		System.out.println(sb);
	}
	
	void printErrorMessage(String errorMessage){
		System.out.println(errorMessage);
		return;
	}

		


}
