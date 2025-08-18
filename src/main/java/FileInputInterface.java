package Main.java;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Constructor에서 파일을 읽고, 파일 내용을 담은 byte[] 멤버 변수에 저장
 * 이후 Constructor에서 byte[]를 string으로 변환하는 메소드를 실행, 
 * 해당 string을 ContactManager에 반환하고 메서드는 종료, 이후 constructor가 종료
 */

public class FileInputInterface {
    FileInputStream inputStream;
    byte[] readFileContent;

    FileInputInterface(String fileName) throws FileNotFoundException{
        this.inputStream = new FileInputStream(fileName);
    }

    String loadSavedContactFile() throws IOException{
        this.readFileContent = new byte[inputStream.available()];
        int contentsLength = this.inputStream.read(this.readFileContent);

        String convertedFileContents = convertFileContentToString(contentsLength);
        return convertedFileContents;
    }

    String convertFileContentToString(int contentsLength){
        return new String(this.readFileContent, 0, contentsLength);
    }
}
