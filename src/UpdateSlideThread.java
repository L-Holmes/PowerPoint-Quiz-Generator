import java.io.IOException;

public class UpdateSlideThread extends Thread {
    ConvertPDFPagesToImages newSlideChangeObj = null;
    String slideOperation = "";

    public UpdateSlideThread(ConvertPDFPagesToImages passedObj) {
        newSlideChangeObj = passedObj;
        System.out.println("initializing the thread..");
    }

    public void setSlideOperation(String newOperation) {
        slideOperation = newOperation;

    }

    @Override
    public void run() {
        //
        System.out.println("started the thread");
        try {
            if ((newSlideChangeObj != null) && (slideOperation != "")) {
                // then user has set the appropriate values and can call the update slide method
                try {
                    newSlideChangeObj.changeSlide(slideOperation);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            //
            Thread.sleep(100);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
         
    }
}