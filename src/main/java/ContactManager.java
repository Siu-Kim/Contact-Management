package main.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

		boolean isSameContact(String name, String phonenum, String attribute){
			for(T contact: Storage){
				String contactElement[] = contact.getInfo();
				if((contactElement[0].equals(name)) && (contactElement[1].equals(phonenum)) 
				&& (contactElement[2].equals(attribute))){
					return true;
				}
			}
			return false;
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
		
		void edit(T contact, int queryType, String query) throws IndexOutOfBoundsException{
			if(Storage.contains(contact)){
				int index = Storage.indexOf(contact);
				T tempContact = Storage.get(index);
				ContactAttribute tempAttribute = ContactAttribute.findByAttributeCount(queryType);
				tempContact.setInfo(tempAttribute, query);
				Storage.set(index, tempContact);
				return;
			}
			throw new RuntimeException("Edit failed. Please try again...");
		}		
	}
	
	MyStorage<ContactInfo> contactStorage;
	CommandLineInterface cli;
	
	public ContactManager(CommandLineInterface instance_cli){
		cli = instance_cli;
		contactStorage = new MyStorage<ContactInfo>();
	}
	
	public boolean run() throws RuntimeException{
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
		try{
			FileOutputInterface FOI = new FileOutputInterface("src/main/resources/ContactBook.txt");
			FOI.saveCurrentContactToFile(contactStorage.getAllContacts());
			return;
		}
		catch(FileNotFoundException e){
			cli.printErrorMessage(e.getMessage());
		}
		catch(IOException e){
			cli.printErrorMessage(e.getMessage());
		}
	}	

	private void loadFromFile(){
		try{
			FileInputInterface FII = new FileInputInterface("src/main/resources/ContactBook.txt");
			List<String> fileContacts = FII.loadSavedContactFile();
			int count = 0;

			for(String contactInfo: fileContacts){
				String parsedContactInfo[] = parseFileContents(contactInfo);
				if(!checkDuplicated(parsedContactInfo)){
					addFileContact(parsedContactInfo[0], parsedContactInfo[1], parsedContactInfo[2], parsedContactInfo[3]);
					count++;
				}
			}
			cli.printResultOfLoadContact(count);
			return;
		}
		catch(FileNotFoundException e){
			cli.printErrorMessage(e.getMessage());
		}
		catch(IOException e){
			cli.printErrorMessage(e.getMessage());
		}
	}

	private String[] parseFileContents(String contactInfo){
		String parsedContactInfo[] = contactInfo.split("/");
		return parsedContactInfo;
	}

	private boolean checkDuplicated(String parsedContactInfo[]){
		return contactStorage.isSameContact
		(parsedContactInfo[0], parsedContactInfo[1], parsedContactInfo[2]);
	}

	private void addFileContact(String name, String phonenum, String attribute, String contactType){
		ContactAttribute type = ContactAttribute.valueOf(contactType);
		switch(type){
			case RELATION:
				contactStorage.addContact(new NormalContact(name, phonenum, attribute));
				break;
			case CLUB_NAME:
				contactStorage.addContact(new ClubContact(name, phonenum, attribute));
				break;
			case DEPARTMENT:
				contactStorage.addContact(new DepartmentContact(name, phonenum, attribute));
				break;
			default:
				break;
		}
	}

	private void createContact(){
		
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
					//promptForName 실행 전에 오류를 처리할 수 있도록 수정 필요
			}
			return;
		}
		catch(InputMismatchException e){
			cli.printErrorMessage(e.getMessage());
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
				strListSearchInfo.add(searchContact.toString());
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
		try{
			List<ContactInfo> listSearchInfo = searchForDelete();
			showSearchInfo(listSearchInfo);

			ContactInfo contactToDelete = selectContactForDelete(listSearchInfo);
			if(cli.checkSurelyDelete()) {contactStorage.delete(contactToDelete);}
		}
		catch(IndexOutOfBoundsException e){
			cli.printErrorMessage(e.getMessage());
		}
		catch(RuntimeException e){
			cli.printErrorMessage(e.getMessage());
		}		
		return;
	}

	private List<ContactInfo> searchForDelete(){
		int type = cli.getDeleteContactMenu();

		ContactAttribute inputAttribute = ContactAttribute.findByAttributeCount(type);
		String searchQuery = cli.promptForQuery(inputAttribute);
		List<ContactInfo> listSearchInfo = contactStorage.search(inputAttribute, searchQuery);

		return listSearchInfo;
	}

	private ContactInfo selectContactForDelete(List<ContactInfo> listSearchInfo){
		int indexForContact = cli.promptForDelete();
		ContactInfo contactToDelete = listSearchInfo.get(indexForContact - 1);
		return contactToDelete;
	}

	
	private void editContact(){ // LIst<String> searchForEdit() / ContactInfo selectContactForEdit / String getEditQuery 할 내용 받기 / editContact
		//search 매커니즘 이용해서 검색 결과 표시 -> 원하는 Contact 선택 -> 수정하고 싶은 멤버 변수 선택 (int type number, String query) 입력
		try{ 
			List<ContactInfo> listSearchInfo = searchForEdit();
			showSearchInfo(listSearchInfo);
			
			ContactInfo contactToEdit = selectContactForEdit(listSearchInfo);
			int queryType = getQueryType(contactToEdit);
			contactStorage.edit(contactToEdit, queryType, getEditQuery(queryType));

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

	private List<ContactInfo> searchForEdit(){
		int type = cli.getEditContactMenu();

		ContactAttribute inputAttribute = ContactAttribute.findByAttributeCount(type);
		String searchQuery = cli.promptForQuery(inputAttribute);
		List<ContactInfo> listSearchInfo = contactStorage.search(inputAttribute, searchQuery);

		return listSearchInfo;
	}

	private void showSearchInfo(List<ContactInfo> listSearchInfo){
		List<String> strListSearchInfo = new ArrayList<>(); //검색한 Contact의 정보를 저장할 ArryList<String> 객체
		for(ContactInfo searchContact: listSearchInfo){
			strListSearchInfo.add(searchContact.toString());
		}
		cli.printContactInfo(strListSearchInfo); //검색한 Contact를 출력
	}

	private ContactInfo selectContactForEdit(List<ContactInfo> listSearchInfo){
		int indexForContact = cli.promptForEdit();
		ContactInfo contactToEdit = listSearchInfo.get(indexForContact - 1);
		return contactToEdit;
	}
	
	private String getEditQuery(int indexForAttribute){
		String editQuery = cli.promptForQuery(ContactAttribute.findByAttributeCount(indexForAttribute));
		return editQuery;
	}

	private int getQueryType(ContactInfo contactToEdit){
		int indexForAttribute = cli.promptForEditAttribute(contactToEdit.toString(), contactToEdit.getContactType());
		if(indexForAttribute == 3) {indexForAttribute = contactToEdit.getContactType().getAttributeCount();}
		return indexForAttribute;
	}


	private void viewAllContacts(){
		List<ContactInfo> allContacts = contactStorage.getAllContacts();
		
		List<String> strNormalContacts = new ArrayList<>();
		List<String> strClubContacts = new ArrayList<>();
		List<String> strDepartmentContacts = new ArrayList<>();
		
		for(ContactInfo contact : allContacts){
			switch(contact.getContactType()){
				case RELATION:
					strNormalContacts.add(contact.toString());
					break;
				case CLUB_NAME:
					strClubContacts.add(contact.toString());
					break;
				case DEPARTMENT:
					strDepartmentContacts.add(contact.toString());
					break;	
				default:
					cli.printErrorMessage("Error occuerd. Please try again.");
					cli.printErrorMessage(contact.toString());
					return;
			}
		}
		cli.printAllContactInfo(strNormalContacts, strClubContacts, strDepartmentContacts);
		return;
	}

}
