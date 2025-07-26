package Main;

import java.util.ArrayList;
import java.util.List;

public class ContactManager{
  
	class MyStorage<T extends ContactInfo>{
		
		private int storageSize;
		private ArrayList<T> Storage;
		
		MyStorage(){
			storageSize = 0;
			Storage = new ArrayList<>();
		}

		void setStorageSize(int size_Input){
			if(size_Input >= 0) {storageSize = size_Input; return;}
			throw new RuntimeException("You can't set the Storage size with negative number.");
		}
		
		boolean addContact(T contact){
			if(isFull()) {throw new RuntimeException("The Storage is Full. Please delete saved contacts");}
			
			Storage.add(contact);
			return true;
		}
		
		boolean isFull(){
			if(storageSize == 0){ //Storage 크기가 inf
				return false;
			}
			return Storage.size() >= storageSize;
		}
		
		List<T> getAllContacts(){
			return this.Storage;
		}
		
		ArrayList<ContactInfo> search(ContactAttribute attribute, String query) throws IndexOutOfBoundsException{ //query와 같은 값을 가진 모든 Contact를 List에 add하여 반환
			ArrayList<ContactInfo> searchedContact = new ArrayList<>();
			for(T contact: Storage){
				if(contact.matches(attribute, query)){
					searchedContact.add(contact);
					
					return searchedContact;
				}
			}
			throw new IndexOutOfBoundsException("There is no such Contact in the Storage. Please try again...");
		}
		
		void delete(T contact) throws IndexOutOfBoundsException{
			//ArrayList의 멤버 비교 메서드를 이용해 contact와 같은 객체를 삭제
			if(Storage.contains(contact)){
				Storage.remove(contact);
				return;
			}
		}
		
		void edit(T contact, String query) throws IndexOutOfBoundsException{
			if(Storage.contains(contact)){
				int index = Storage.indexOf(contact);
				T tempContact = Storage.get(index);
				
				tempContact.setInfo(contact.getContactType(), query);
				Storage.set(index, tempContact);
				return;
			}
			throw new RuntimeException("Edit failed. Please try again...");
		}		
	}
	
	MyStorage<ContactInfo> contactStorage;
	CommandLineInterface cli;
	
	ContactManager(CommandLineInterface instance_cli){
		cli = instance_cli;
		contactStorage = new MyStorage<ContactInfo>();
	}
	
	boolean run() throws RuntimeException{
		int userChoice = cli.getMainMenu();
		
		switch(userChoice){
			case 1: setStorageSize(); break;
			case 2: saveToFile(); break;
			case 3: loadFromFile();	break;
			case 4:	createContact(); break;
			case 5:	searchContact(); break;
			case 6:	deleteContact(); break;
			case 7: editContact(); break;
			case 8: viewAllContacts(); break;
			case 9:	return false;
			default:
				cli.printErrorMessage("Invalid menu number. Please try again.");
				break;
		}
		return true;		
	}
	
	private void setStorageSize(){
		int size_Input = cli.getSetSizeMenu();
		try{
			contactStorage.setStorageSize(size_Input);
		}
		catch(RuntimeException e){
			cli.printErrorMessage(e.getMessage());
		}
	}

	private void saveToFile(){

	}
	
	private void loadFromFile(){
		
	}

	private void createContact(){ //
		
		int type = cli.getCreateContactMenu();
		String name = cli.promptForName();
		String phone = cli.promptForPhoneNumber();
		try{
			switch(type){
				case 1:
					String relation = cli.promptForRelation();
					NormalContact normalContact = new NormalContact(name, phone, relation);
					contactStorage.addContact(normalContact);
					break;
				case 2:
					String clubName = cli.promptForClubName();
					ClubContact clubContact = new ClubContact(name, phone, clubName);
					contactStorage.addContact(clubContact);

					break;

				case 3:
					String department = cli.promptForDepartment();
					DepartmentContact departContact = new DepartmentContact(name, phone, department);
					contactStorage.addContact(departContact);
					break;
				default:
					cli.printErrorMessage("Selected Wrong Number... Please Try Again.");
			}
			return;
		}
		catch(RuntimeException e){
			//Storage가 가득 찼을 때 예외 처리
			cli.printErrorMessage(e.getMessage());			
		}
	}

	private void searchContact(){
		int type = cli.getSearchContactMenu();
				
		try{
			ContactAttribute inputAttribute = ContactAttribute.findByAttributeCount(type);
			String query = cli.promptForQuery(inputAttribute);
			List<ContactInfo> listSearchInfo = contactStorage.search(inputAttribute, query);
			
			List<String> strListSearchInfo = new ArrayList<>();
			for(ContactInfo searchContact: listSearchInfo){
				strListSearchInfo.add(searchContact.getInfo());
			}
			cli.printContactInfo(strListSearchInfo);
			
		}
		catch(IndexOutOfBoundsException e){
			cli.printErrorMessage(e.getMessage());
		}
		catch(RuntimeException e){
			cli.printErrorMessage(e.getMessage());
		}		
		return;
	}

	private void deleteContact(){
		int type = cli.getDeleteContactMenu();
		
		try{
			ContactAttribute inputAttribute = ContactAttribute.findByAttributeCount(type);
			String query = cli.promptForQuery(inputAttribute);
			List<ContactInfo> listSearchInfo = contactStorage.search(inputAttribute, query);
			
			List<String> strListSearchInfo = new ArrayList<>(); //검색한 Contact의 정보를 저장할 ArryList<String> 객체
			//검색한 contact의 getInfo()메소드를 이용해 모든 검색 결과를 저장하는 String list 생성
			for(ContactInfo searchContact: listSearchInfo){
				strListSearchInfo.add(searchContact.getInfo());
			}
			cli.printContactInfo(strListSearchInfo); //검색한 Contact를 출력
			
			int indexForDelete = cli.promptForDelete(); //삭제할 Contact의 index를 CLI에서 입력받기
			ContactInfo contactToDelete = listSearchInfo.get(indexForDelete - 1);
			if(cli.checkSurelyDelete()) {contactStorage.delete(contactToDelete);} // 삭제 여부를 재확인 및 삭제 처리
		}
		catch(IndexOutOfBoundsException e){
			cli.printErrorMessage(e.getMessage());
		}
		catch(RuntimeException e){
			cli.printErrorMessage(e.getMessage());
		}		
		return;
	}
	
	private void editContact(){
		//search 매커니즘 이용해서 검색 결과 표시 -> 원하는 Contact 선택 -> 수정하고 싶은 멤버 변수 선택 (int type number, String query) 입력
		
		int type = cli.getEditContactMenu();
		try{ 
			ContactAttribute inputAttribute = ContactAttribute.findByAttributeCount(type);
			String searchQuery = cli.promptForQuery(inputAttribute);
			List<ContactInfo> listSearchInfo = contactStorage.search(inputAttribute, searchQuery);
			
			List<String> strListSearchInfo = new ArrayList<>(); //검색한 Contact의 정보를 저장할 ArryList<String> 객체
			for(ContactInfo searchContact: listSearchInfo){
				strListSearchInfo.add(searchContact.getInfo());
			}
			cli.printContactInfo(strListSearchInfo); //검색한 Contact를 출력
			
			//edit할 Contact의 index 선택 -> 해당 Contact의 attribute 정렬해서 출력하고 변경할 attribute의 번호와 변경할 attribute의 query 입력
			//-> MyStorage의 edit(ContactInfo Contact, ContactAttribute attribute, String query)에서 정보를 변경 
			
			int indexForContact = cli.promptForEdit();
			ContactInfo contactToEdit = listSearchInfo.get(indexForContact - 1);
			
			int indexForAttribute = cli.promptForEditAttribute(contactToEdit.getInfo(), contactToEdit.getContactType());
			if(indexForAttribute == 3) {indexForAttribute = contactToEdit.getContactType().getAttributeCount();}
			String editQuery = cli.promptForQuery(ContactAttribute.findByAttributeCount(indexForAttribute));
			
			contactStorage.edit(contactToEdit, editQuery);
				cli.printErrorMessage("Edit failed. Please try again...");	
			
			cli.printEditSuccessfully();
			return;			
		}
		catch(IllegalArgumentException e){
			cli.printErrorMessage(e.getMessage());
		}
		catch(RuntimeException e){
			cli.printErrorMessage(e.getMessage());
		}	
	}
	
	private void viewAllContacts(){
		List<ContactInfo> allContacts = contactStorage.getAllContacts();
		
		List<String> strNormalContacts = new ArrayList<>();
		List<String> strClubContacts = new ArrayList<>();
		List<String> strDepartmentContacts = new ArrayList<>();
		
		for(ContactInfo contact : allContacts){
			switch(contact.getContactType()){
				case RELATION:
					strNormalContacts.add(contact.getInfo());
					break;
				case CLUB_NAME:
					strClubContacts.add(contact.getInfo());
					break;
				case DEPARTMENT:
					strDepartmentContacts.add(contact.getInfo());
					break;	
			}
		}
		cli.printAllContactInfo(strNormalContacts, strClubContacts, strDepartmentContacts);
		return;
	}

}
