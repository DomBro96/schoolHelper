package cn.dombro.schoolHelper.util;

import com.jfinal.kit.PathKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by 18246 on 2017/5/12.
 */
public class UploadUtil  {

        private static final Logger LOGGER = LoggerFactory.getLogger(UploadUtil.class);

        private static String filePath;

        public static String getFilePath(){
            return filePath;
        }

        public static void upload(File source, String username){
            FileInputStream fis = null;
            FileOutputStream fos = null;
            //1.获取 扩展名
            String fileName = source.getName();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String prefix ;
            if (".jpg".equals(extension) || ".png".equals(extension)){
                prefix = "img";
                fileName = username + extension;
            }else{
                prefix = "file";
            }
            //2.将文件 写入目标文件
            try {
                  fis = new FileInputStream(source);
                //目标文件夹
                File targetDir = new File(PathKit.getWebRootPath()+"/"+"face");

                if (!targetDir.exists()){
                     targetDir.mkdir();
                }
                File target = new File(targetDir,fileName);
                if (!target.exists()){
                    target.createNewFile();
                }
                filePath = target.getPath();

                fos = new FileOutputStream(target);
                byte[] buff = new byte[500];

                while (fis.read(buff,0,buff.length) != -1){
                    fos.write(buff,0,buff.length);
                }

            } catch (FileNotFoundException e) {
                LOGGER.error("文件上传失误",e);
                e.printStackTrace();
            } catch (IOException e) {
                LOGGER.error("文件写入服务器出现失误 ",e);
                e.printStackTrace();
            }finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    LOGGER.error("文件输出流关闭失败",e);
                    e.printStackTrace();
                }
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error("文件输入流关闭失败",e);
                    e.printStackTrace();
                }
            }
        }
    }

