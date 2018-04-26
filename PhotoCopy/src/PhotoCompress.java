import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class PhotoCompress {
	

    public static void  main(String[] args){
        if(args.length<4){
            System.out.println("请输入源文件夹路径  目的文件夹路径  新图片宽  新图片高");
            System.exit(0);
        }//if

        File inputDir = new File(args[0]);
        for(File src : inputDir.listFiles()){
        	compressImage(src.getPath(),args[1],Integer.parseInt(args[2]),Integer.parseInt(args[3]));
        }

    }/* main */


    private static BufferedImage InputImage(String srcImgPath){
        BufferedImage srcImage = null;
        try {
            FileInputStream in = new FileInputStream(srcImgPath);
            srcImage = ImageIO.read(in);
        }catch(Exception e){
            e.printStackTrace();;
        }
        return  srcImage;
    }//InputImage

    public static void compressImage(String srcImgPath,String outImgPath,int new_w,int new_h){
        File srcFile = new File(srcImgPath);
        String fileName = srcFile.getName();

        BufferedImage src = InputImage(srcImgPath);
        if(src != null){
            int old_w = src.getWidth();
            int old_h = src.getHeight();
            disposeImage(src,fileName,outImgPath,new_w,new_h);
        }//
    }// compressImage

    private  static synchronized  void disposeImage(BufferedImage src, String fileName,String outImgPath,int new_w,int new_h)
    {
        int old_w = src.getWidth();
        int old_h = src.getHeight();

        BufferedImage newImg = null;
        switch(src.getType()){case 13:
        	break;
        	default:

            newImg = new BufferedImage(new_w,new_h,1);
        }
        Graphics2D g = newImg.createGraphics();
        g.drawImage(src,0,0,old_w,old_h,null);
        g.dispose();
        newImg.getGraphics().drawImage(src.getScaledInstance(new_w,new_h,4),0,0,null);
        OutImage(fileName,outImgPath,newImg);
        }//

    private static void OutImage(String fileName,String outImgPath,BufferedImage newImg){
        File file = new File(outImgPath);
        if(!file.exists()){
            file.mkdirs();
        }//if
        try{
            String newPath = null;
            if(!outImgPath.endsWith(File.separator)){
                newPath = outImgPath+File.separator;
            }//if

            File result = new File(newPath + fileName);
            ImageIO.write(newImg,fileName.substring(fileName.lastIndexOf(".")+1),result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }// outimage

}

