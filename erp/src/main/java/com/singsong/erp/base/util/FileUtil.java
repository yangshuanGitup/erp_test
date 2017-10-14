package com.singsong.erp.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 实现大文件切片，文件合并；文件压缩、解压
 * @author Happyfi
 * 
 */
public class FileUtil {
	public static final int BUFFER_SIZE = 4096; //文件读写缓冲区大小
	public static final int PADDING_LENGTH = 4; //切片标识的最大长度，0001-9999
	public static final char EXTENSION_SEPARATOR = '.';
	public static final String BZIP2_EXT = ".bz2";
	
	/**
	 * 分割文件
	 * @param srcName 源文件
	 * @param destDir 分割文件所在目录  当为空时，和源文件同一目录
	 * @param size:每一份大小，以KB为单位
	 * @return 拆分后的文件名列表
	 * @throw IOException
	 **/
	 public static List<String> split(String srcName, String destDir, int size) throws IOException{
		 List<String> fileLs = new ArrayList<String>();
		 if (size < 0) 
			 return fileLs;
	  
	     size=size*1024;
	      
	     File srcFile=new File(srcName);
	     if (!srcFile.exists()) {
	    	 System.out.println("文件不存在：" + srcName);
	    	 return fileLs;
	     }
	     
	     if (StringUtils.isBlank(destDir)) {
	    	 destDir = srcFile.getParent();
		 }
	     
	     //如果目录不存在，先创建出来
	     destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
	     File targetPath=new File(destDir);
	     if(!targetPath.exists()) {
	         targetPath.mkdirs();
	     }
	     
	     long fileSize = srcFile.length(); //获取源文件大小
	     long num; //分割文件个数
	      
	     FileInputStream  fis = null;
		 fis = new FileInputStream(srcFile); //打开要分割文件
	     num=fileSize/size;
	      
	     int index=0;
	      
         FileOutputStream  fos = null;
         byte[] buffer = new byte[BUFFER_SIZE];

	     //对应分割的文件输出对应文件
         String strFile = null;
	     for(; index<num; index++) {
	    	 strFile = destDir + srcFile.getName() + EXTENSION_SEPARATOR + StringUtils.leftPad(String.valueOf(index+1), PADDING_LENGTH, '0');
	    	 fileLs.add(strFile);
	         File targetFile=new File(strFile);
	         try {
	        	 fos=new FileOutputStream(targetFile);
	        	 
	        	 int i;
	        	 int len = size;
	 			 while ((len > 0) && (i = fis.read(buffer,0, Math.min(len, buffer.length))) != -1) {
	 				fos.write(buffer, 0, i);
	 				len -= i;
	 			 }
			 } finally {
	            IOUtils.closeQuietly(fos);
	         }
	     }
	     
	     strFile = destDir + srcFile.getName() + EXTENSION_SEPARATOR + StringUtils.leftPad(String.valueOf(index+1), PADDING_LENGTH, '0');
	     fileLs.add(strFile);
	     File targetFile=new File(strFile);
	     try {
	    	 fos=new FileOutputStream(targetFile);
	    	 
	    	 int i;
			 while ((i = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, i);
			 }
  		 } finally {
			IOUtils.closeQuietly(fos);
	     }
	     
	     IOUtils.closeQuietly(fis);
	     return fileLs;
	 }
	 
	 /**
	  * 合并文件
	  * @param srcFile 待合并的切片文件名（不包含切片后端）
	  * @param destDir 合并后文件的路径  当为空时，合并后的文件与切片文件同一目录
	  * @return 合并后的文件名
	  * @throw IOException
	  **/
	  public static String merge(String srcName,String destDir) throws IOException {
	      File srcPath = new File(FilenameUtils.getFullPath(srcName));
		  if (StringUtils.isBlank(destDir)) {
				 destDir = FilenameUtils.getFullPath(srcName);
		  }
		  destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
		  
	      File[] fs;
	      
	      final String baseFileName = FilenameUtils.getName(srcName);
	      String targetFile = destDir + baseFileName;
	      FileOutputStream fos=new FileOutputStream(targetFile);
	      
	      //取得待合并的文件
	      fs=srcPath.listFiles(new FilenameFilter() {
	          public boolean accept(File dir,String name) {
	              return name.startsWith(baseFileName + EXTENSION_SEPARATOR);
	          }
	      });
	       
	      //打开所有的文件准备进行合并
	      for(int index=0; index<fs.length; index++) {
	    	  FileInputStream input = new FileInputStream(fs[index]);
	    	  IOUtils.copy(input, fos);
	    	  IOUtils.closeQuietly(input);
	      }
	       
	      IOUtils.closeQuietly(fos);
	      return targetFile;
	  }

	  /**
	   * 压缩bzip2文件
	   * @param bzip2File 源文件
	   * @param destDir 压缩后所在文件夹 当为空时，压缩后的文件与源文件同一目录
	   * @return 压缩后的文件绝对路径名
	   * @throw IOException
	   **/
	   public static String bzip2(String srcFile, String destDir) throws IOException {
		  File file = new File(srcFile);
		  return bzip2(file, destDir);
	   }
		
	 /**
	  * 压缩bzip2文件
	  * @param srcFile 源文件file对象
	  * @param destDir 压缩后所在文件夹  当为空时，压缩后的文件与源文件同一目录
	  * @return 压缩后的文件绝对路径名
	  * @throw IOException
	  **/
	  public static String bzip2(File srcFile, String destDir) throws IOException {
		 if (StringUtils.isBlank(destDir)) {
			 destDir = srcFile.getParent();
		 }
		  
		 destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
		 InputStream is = null;
		 OutputStream os = null;
		 File destFile = null;
		 try {
			destFile = new File(destDir, FilenameUtils.getName(srcFile.toString()) + BZIP2_EXT );
			is = new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE);
			os = new BZip2CompressorOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE));
			IOUtils.copy(is, os);
		 } finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		 }
		 
		 return destFile.getAbsolutePath();
	 }
	  
	/**
	 * 解压缩bzip2文件
	 * @param bzip2File 源文件名
	 * @param destDir 解压缩后所在目录 当为空时，解压缩后的文件与源文件同一目录
	 * @return 解压缩后的文件绝名
	 * @throw IOException
	 **/	
	public static String unBZip2(String bzip2File, String destDir) throws IOException {
		File file = new File(bzip2File);
		return unBZip2(file, destDir);
	}

	/**
	 * 解压缩bzip2文件
	 * @param srcFile 源文件file对象
	 * @param destDir 解压缩后所在目录 当为空时，解压缩后的文件与源文件同一目录
	 * @return 解压缩后的文件绝对路径名
	 * @throw IOException
	 **/
	public static String unBZip2(File srcFile, String destDir) throws IOException {
		if (StringUtils.isBlank(destDir)) {
			destDir = srcFile.getParent();
		}
		destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
		InputStream is = null;
		File destFile = null;
		OutputStream os = null;
		try {
			destFile = new File(destDir, FilenameUtils.getBaseName(srcFile.toString()));
			is = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE));
			os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
			IOUtils.copy(is, os);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		}
		
		return destFile.getAbsolutePath();
	}
	
	/**
	 * 文件重命名
	 * @param srcFile 原文件名
	 * @param tgtFile 目标文件名
	 * @param delExist 目标文件已存在是，是否删除目标文件 
	 * 					true：删除
	 * 					false：保留
	 * @return 目标文件名
	 */
	public static String rename(String srcFile, String tgtFile, boolean delExist) {
		File resFile = new File(srcFile);   
        File newFile = new File(tgtFile);
        if (delExist)
        	newFile.delete();
        
        resFile.renameTo(newFile);   
		return tgtFile;
	}
	
	/**
	  * 
	  * 将一组文件打成tar包（注意，打包后文件路径丢失）
	  * @param sources 要打包的原文件数组
	  * @param target 打包后的文件
	  * @return File  返回打包后的文件
	  * @throws
	  */
	 public static String tar(String[] sources, String target) throws IOException {
		  FileOutputStream out = null;
		  out = new FileOutputStream(target);
		  TarArchiveOutputStream os = new TarArchiveOutputStream(out);
		  for (String strFile : sources) {
			   try {
				   File file = new File(strFile);
				   TarArchiveEntry entry = new TarArchiveEntry(file.getName());   
			       entry.setSize(file.length());
			       os.putArchiveEntry(entry);     
				   IOUtils.copy(new FileInputStream(file), os);
				   os.closeArchiveEntry();
			   } catch (IOException e) {
			    e.printStackTrace();
			   } 
		  }
		  os.flush();
		  
		  IOUtils.closeQuietly(os);
		  IOUtils.closeQuietly(out);
    
		  return target;
	 }
    
    /** 
     * 解归档 
     * @param source 源归档tar文件 
     * @return 解压缩后的文件名（全路径）列表
     */  
    public static List<String> untar(String strSource){  
        TarArchiveInputStream tarIn = null;  
        FileInputStream fis = null;
        File source = new File(strSource);
        String parentPath = source.getParent();  
          
        FileOutputStream fos = null;
        List<String> fileLs = new ArrayList<String>();
        try{  
            fis = new FileInputStream(source);  
            tarIn = new TarArchiveInputStream(fis);  
            TarArchiveEntry entry = null;  
            while((entry = tarIn.getNextTarEntry()) != null){  
              File file = new File(parentPath + "/" + entry.getName());  
              if(entry.isDirectory()){  
                    file.mkdirs();  
                    continue;  
                }  
                File parentDir = file.getParentFile();  
                if(!parentDir.exists()){  
                    parentDir.mkdirs();  
                }  
                 
                fos = new FileOutputStream(file);  
                IOUtils.copy(tarIn, fos);
                IOUtils.closeQuietly(fos);
                fileLs.add(file.getAbsolutePath());
            }  
        }catch (Exception e) {  
        }finally{
        	IOUtils.closeQuietly(fis);
        	IOUtils.closeQuietly(fos);
        	IOUtils.closeQuietly(tarIn);
        }
        return fileLs;
    }  
    
    /**
     * 将InputStream保存到指定的路径下
     * @param file_name
     * @param is
     * @return File
     */
    public static File file_put_contents(final String file_path, InputStream is) {
		File file = new File(file_path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStream os = null;
		try {
			os = new DataOutputStream(new FileOutputStream(file_path));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			int len = is.available();
			if (len <= 1024 * 1024) {
				byte[] bytes = new byte[len];
				is.read(bytes);
				os.write(bytes);
			} else {
				int byteCount = 0;
				// 1M逐个读取
				byte[] bytes = new byte[1024 * 1024];
				while ((byteCount = is.read(bytes)) != -1) {
					os.write(bytes, 0, byteCount);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.flush();
				if (null != is)
					is.close();
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static void main(String[] args) throws Exception {

//		encodeTest();
//		dncodeTest();
//		encodeTestBak();
//		dncodeTest();
		bzip2(new File(""),"E:/");
		
	}
}