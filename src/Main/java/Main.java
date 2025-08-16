package Main.java;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
		
		CommandLineInterface cli = new CommandLineInterface();
		ContactManager contactManager = new ContactManager(cli);
		
		boolean isRunning = true;
		while(isRunning){			
			try{
				//Main은 event excution process를 시작(추후 새 프로세스 생성 기능 추가) 이벤트 실행 프로세스는 전적으로 ContactManager에 의해 실행
				// User가 Exit을 선태가면 run()이 false를 반환하고 Main loop가 종료
				isRunning = contactManager.run();
			}
			catch(Exception e)
			{
				cli.printErrorMessage("RuntimeException occurs. restart main menu.");
			}      	  
		}
		System.out.println("Exiting Contact Manager");
    }

}
