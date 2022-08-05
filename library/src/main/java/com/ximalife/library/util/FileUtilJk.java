package com.ximalife.library.util;

import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.ximalife.library.base.BaseAppApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Image 保存管理
 */
public class FileUtilJk {

    private static final String rootPath = Environment.getExternalStorageDirectory() + "/";   // 最终导出的用户可以看到的目录不会跟随应用卸载而被清除

    private static String audioRootPath = "pauseRecordDemo";

    private static final String PictrueSavePath = "FyPicture/";//保存图片目录

    private static final String AudioSavePath = "BdAudio/";//保存图片目录

    //原始文件(不能播放)
    private final static String AUDIO_PCM_BASEPATH = audioRootPath + "/pcm/";
    //可播放的高质量音频文件
    private final static String AUDIO_WAV_BASEPATH = audioRootPath + "/wav/";

    public static String getSDPath(Context context) {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            if (Build.VERSION.SDK_INT >= 29) {
                //Android10之后
                sdDir = context.getExternalFilesDir(null);
            } else {
                sdDir = Environment.getExternalStorageDirectory();// 获取SD卡根目录
            }
        } else {
            sdDir = Environment.getRootDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

    static String cachePath = "";
    public static String getCacheImgPath() {
        String cachePath;
        cachePath = getSDPath(BaseAppApplication.Companion.getInstance()) + "/" + PictrueSavePath;
        addIgnore(cachePath);
        File file = new File(cachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return cachePath;
    }

    public static String getAudioPath() {
//        return getInsideFilePath();
        String cachePath;
        cachePath = getSDPath(BaseAppApplication.Companion.getInstance()) + "/" + AudioSavePath;
        File file = new File(cachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return cachePath;
    }

    public static void addIgnore(String filePath) {
        Log.d("FileUitls", "path===========" + filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            Log.d("FileUitls", "开始创建===========");
            file.mkdirs();
        }
        File ignoreFile = new File(filePath + "/.nomedia");
//        File ignoreFile = new File(filePath + "/.nomedia");
        if (ignoreFile.exists() && ignoreFile.isFile()) {
            Log.d("FileUitls", "已经创建===========00000");
            return;
        }
        try {
            Log.d("FileUitls", "已经创建===========111111111");
            ignoreFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String TAG = FileUtilJk.class.getSimpleName();
    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


    /**
     * 查询指定路径下的所有图片
     *
     * @param path
     * @return
     */
    public static List<String> getFilesAllName(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        //传入指定文件夹的路径　　
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> imagePaths = new ArrayList<>();
        if (files != null && files.length != 0) {
            for (int i = 0; i < files.length; i++) {
                if (checkIsImageFile(files[i].getPath())) {
                    imagePaths.add(files[i].getPath());
                }
            }
            return imagePaths;
        } else {
            return null;
        }
    }

    public static boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        //获取拓展名
        String fileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (fileEnd.equals("jpg") || fileEnd.equals("png") || fileEnd.equals("gif")
                || fileEnd.equals("jpeg") || fileEnd.equals("bmp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    public static String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = BaseAppApplication.Companion.getInstance().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    public static String getImagePath(Context context, Uri uri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }


    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (f.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static long getFileSize(String strFile) {
        long size = 0;
        try {
            File file = new File(strFile);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取缓存文件绝对路径
     */
    public static String getCacheFilePath(Boolean fileType) {
        if (fileType) {
            return rootPath + AUDIO_PCM_BASEPATH;
        } else {
            return rootPath + AUDIO_WAV_BASEPATH;
        }
    }

    /**
     * 获取pcm文件路径
     *
     * @param fileName
     * @return
     */
    public static String getPcmFileAbsolutePath(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            throw new NullPointerException("fileName isEmpty");
        }
        if (!isSdcardExit()) {
            throw new IllegalStateException("sd card no found");
        }
        String mAudioRawPath = "";
        if (isSdcardExit()) {
            if (!fileName.endsWith(".pcm")) {
                fileName = fileName + ".pcm";
            }
            String fileBasePath = rootPath + AUDIO_PCM_BASEPATH;
            File file = new File(fileBasePath);
            //创建目录
            if (!file.exists()) {
                file.mkdirs();
            }
            mAudioRawPath = fileBasePath + fileName;
        }

        return mAudioRawPath;
    }

    /**
     * 判断是否有外部存储设备sdcard
     *
     * @return true | false
     */
    public static boolean isSdcardExit() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 生成wav文件绝对路径
     *
     * @param fileName
     * @return
     */
    public static String getWavFileAbsolutePath(String fileName) {
        if (fileName == null) {
            throw new NullPointerException("fileName can't be null");
        }
        if (!isSdcardExit()) {
            throw new IllegalStateException("sd card no found");
        }

        String mAudioWavPath = "";
        if (isSdcardExit()) {
            if (!fileName.endsWith(".wav")) {
                fileName = fileName + ".wav";
            }
            String fileBasePath = rootPath + AUDIO_WAV_BASEPATH;
            File file = new File(fileBasePath);
            //创建目录
            if (!file.exists()) {
                file.mkdirs();
            }
            mAudioWavPath = fileBasePath + fileName;
        }
        return mAudioWavPath;
    }


    public static boolean PictureIsSizeFit(String path, int MaxSize) {
        if (getFileOrFilesSize(path, 1) > MaxSize) {
            return false;
        }
        return true;
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSize(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("test", "获取文件大小失败");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("test", "获取文件大小不存在");
        }
        return size;
    }


//    public void syncPhotoAlbum(File file, boolean isVideo) {
//        File rootString = Environment.getExternalStorageDirectory();
//        String filePath = rootString + File.separator + "UpdateImage/";
//        File fileDir = new File(filePath);
//        if (!fileDir.isDirectory()) {
//            fileDir.mkdirs();
//        }
//        String fileName = "tempFile_" + System.currentTimeMillis() + ".mp4";
//        File tempFile = new File(filePath + File.separator + fileName);
//        if (FileKit.copyFile(file, tempFile)) {
//            // 其次把文件插入到系统图库
//            try {
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Video.Media.DATA, tempFile.getAbsolutePath());
//                mContext.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
//
//            } catch (Exception e) {
//                Toast.makeText(mContext, "同步相册失败", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//        Toast.makeText(mContext,"已保存到相册",Toast.LENGTH_SHORT).
//
//    show();
//}

}
