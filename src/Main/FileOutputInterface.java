package Main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/*
 * StorageSize보다 크거나, 설정이 되어있지 않은 경우에 File input이 들어오면 예외 처리
 * MyStorage에 대한 Access 문제 -> storage 객체를 전달받아 해결
 * <MyStorage> Storage 객체 반환 
 * -> <ContactManager> Storage 객체에서 각 Contact 객체의 getInfo()를 통해
 *    받아온 String을 Byte[]로 저장
 * -> Byte[]을 OutputStream으로 write
 */

public class FileOutputInterface{
    //List<ContactInfo> currentContacts;
    FileOutputStream outputStream;
    byte[] convertedFileContent;

    FileOutputInterface(String fileName) throws FileNotFoundException{
        //this.currentContacts = currentContacts;
        this.outputStream = new FileOutputStream(fileName);
    }

    void saveCurrentContactToFile(List<ContactInfo> currentContacts) throws IOException{
        convertToByteAray(currentContacts);
        outputStream.write(convertedFileContent);
    }
    
    private void convertToByteAray(List<ContactInfo> currentContacts){
        StringBuilder fileContent = new StringBuilder();
        for(ContactInfo contact: currentContacts){
            fileContent.append(contact.getInfo());
        }
        convertedFileContent = fileContent.toString().getBytes();
        
        return;
    }






    
}
