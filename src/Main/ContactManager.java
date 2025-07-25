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
			storageSize = size_Input;
		}
		
		boolean addContact(T contact){
			if(isFull()) {throw new RuntimeException("The Storage is Full. Please delete saved contacts");}
			
			Storage.add(contact);
			return true;
		}
		
		boolean isFull(){
			if(storageSize == 0){
				return false;
			}
			return Storage.size() >= storageSize;
		}
		
		List<T> getAllContacts(){
			return this.Storage;
		}
		
		ArrayList<ContactInfo> search(ContactAttribute attribute, String query){ //query와 같은 값을 가진 모든 Contact를 List에 add하여 반환
			ArrayList<ContactInfo> searchedContact = new ArrayList<>();
			for(T contact: Storage){
				if(contact.matches(attribute, query)){
					searchedContact.add(contact);
					
					return searchedContact;
				}
			}
			throw new RuntimeException("There is no contact with this query.");
		}
		
		void delete(T contact){
			//ArrayList의 멤버 비교 메서드를 이용해 contact와 같은 객체를 삭제
			if(Storage.contains(contact)){
				Storage.remove(contact);
				return;
			}
			throw new RuntimeException("There is no contact to delete."); //Storage에 해당 객체가 존재하지 않음
		}
		
		void edit(T contact, ContactAttribute attribute, String query) throws IndexOutOfBoundsException{
			if(Storage.contains(contact)){
				int index = Storage.indexOf(contact);
				T tempContact = Storage.get(index);
				
				tempContact.setInfo(attribute,query);
				Storage.set(index, tempContact);

			}
			throw new RuntimeException("There is no contact to delete."); //Storage에 해당 객체가 존재하지 않음
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
		contactStorage.setStorageSize(size_Input);
		
	}

	private void saveToFile(){

	}
	
	private void loadFromFile(){
		
	}

	private void createContact(){
		
		int type = cli.getCreateContactMenu();
		String name = cli.promptForName(); //CLI 입력 간 동기화 필요
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
		if(type <= 0 || type > 5) {cli.printErrorMessage("Selected Wrong Number... Please Try Again."); return;}
		
		//type을 받은 후 Enum을 이용해서 isAttributeStringFormat을 이용해서 
		String[] field = {"name","phone number", "relation","club name", "department"};
		String searchField = field[type-1];
		

		try{
			String query = cli.promptForQuery(type);
			List<ContactInfo> listSearchInfo = contactStorage.search(` query);
			List<String> strListSearchInfo = new ArrayList<>();
			for(ContactInfo searchContact: listSearchInfo){
				strListSearchInfo.add(searchContact.getInfo());
			}
			cli.printContactInfo(strListSearchInfo);
			
		}
		catch(RuntimeException e){
			cli.printErrorMessage(e.getMessage());
		}		
		return;
	}

	private void deleteContact(){
		int type = cli.getDeleteContactMenu();
		if(type <= 0 || type > 5) {cli.printErrorMessage("Selected Wrong Number... Please Try Again."); return;}
		
		String[] field = {"name","phone number", "relation","club name", "department"};
		String searchField = field[type-1];
		
		
		try{
			String query = cli.promptForQuery(type);
			List<ContactInfo> listSearchInfo = contactStorage.search(searchField, query);
			List<String> strListSearchInfo = new ArrayList<>();
			for(ContactInfo searchContact: listSearchInfo){
				strListSearchInfo.add(searchContact.getInfo());
			}
			cli.printContactInfo(strListSearchInfo);
			
			int indexForDelete = cli.promptForDelete();
			ContactInfo contactToDelete = listSearchInfo.get(indexForDelete - 1);
			
			if(cli.checkSurelyDelete()) {contactStorage.delete(contactToDelete);}
		}
		catch(IndexOutOfBoundsException e){
			cli.printErrorMessage(e.toString());
		}
		catch(RuntimeException e){
			cli.printErrorMessage(e.toString());
		}		
		return;
	}
	
	private void editContact(){
		//search 매커니즘 이용해서 검색 결과 표시 -> 원하는 Contact 선택 -> 수정하고 싶은 멤버 변수 선택 (int type number, String query) 입력
		
		int type = cli.getEditContactMenu();
		try{ 
			ContactAttribute inputAttribute = ContactAttribute.findByAttributeCount(type);
			String query = cli.promptForQuery(inputAttribute);
			List<ContactInfo> listSearchInfo = contactStorage.search(inputAttribute, query);
			

		



		}
		catch(){

		}	
		catch(){
			
		}
		
		
	}
	
	private void viewAllContacts(){
		List<ContactInfo> allContacts = contactStorage.getAllContacts();
		
		List<String> strNormalContacts = new ArrayList<>();
		List<String> strClubContacts = new ArrayList<>();
		List<String> strDepartmentContacts = new ArrayList<>();
		
		for(ContactInfo contact : allContacts){
			switch(contact.getContactType()){
				case "NormalContact":
					strNormalContacts.add(contact.getInfo());
					break;
				case "ClubContact":
					strClubContacts.add(contact.getInfo());
					break;
				case "DepartmentContact":
					strDepartmentContacts.add(contact.getInfo());
					break;
			
			}
		}
		cli.printAllContactInfo(strNormalContacts, strClubContacts, strDepartmentContacts);
		return;
	}

}
