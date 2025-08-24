package main.java;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
/*
 * Constructor에서 파일을 읽고, 파일 내용을 담은 byte[] 멤버 변수에 저장
 * 이후 Constructor에서 byte[]를 string으로 변환하는 메소드를 실행, 
 * 해당 string을 ContactManager에 반환하고 메서드는 종료, 이후 constructor가 종료
 * byte[]을 line마다 받아서 List에 저장하고, 반환.
 * 
 */

public class FileInputInterface {
    FileInputStream inputStream;
    byte[] readFileContent;

    FileInputInterface(String fileName) throws FileNotFoundException{
        this.inputStream = new FileInputStream(fileName);
    }

    List<String> loadSavedContactFile() throws IOException{
        InputStreamReader isr = new InputStreamReader(this.inputStream);
        BufferedReader br = new BufferedReader(isr);

        List<String> convertedFileContact = new ArrayList<String>();
        String fileContactLine;
        while((fileContactLine = br.readLine()) != null){
            convertedFileContact.add(fileContactLine);

        }
        return convertedFileContact;
    }
}
