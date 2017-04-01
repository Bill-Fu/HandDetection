import org.bytedeco.javacv.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class Main {
    public static void main(String[] args) throws Exception {
        FrameGrabber grabber = FrameGrabber.createDefault(0);//replace 0 for default camera
        grabber.start();

        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();

        Mat  grabbedImage = converter.convert(grabber.grab());

        int height  = grabbedImage.rows();
        int  width= grabbedImage.cols();
        Mat grayImage  = new Mat(height,width,CV_8UC1);
        Mat outImage= new Mat(height,width,CV_8UC3); 
       
        Hand hand=new Hand(height,width);

        CanvasFrame frame = new CanvasFrame("手势识别", CanvasFrame.getDefaultGamma()/grabber.getGamma());
        CanvasFrame tmpFrame = new CanvasFrame("Debug使用", CanvasFrame.getDefaultGamma()/grabber.getGamma());

        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null && tmpFrame.isVisible()) {
        	hand.update(grabbedImage);
        	cvtColor(hand.getResult(), outImage, CV_RGBA2BGR);
            Frame outFrame = converter.convert(outImage);
            frame.showImage(outFrame);
            Frame debugFrame = converter.convert(hand.getTmp());
            tmpFrame.showImage(debugFrame);

        }

        frame.dispose();
        grabber.stop(); 
    }
    
}