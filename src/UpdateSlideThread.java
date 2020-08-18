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
                newSlideChangeObj.changeSlide(slideOperation);
            }
            //
            Thread.sleep(100);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
         
    }
}