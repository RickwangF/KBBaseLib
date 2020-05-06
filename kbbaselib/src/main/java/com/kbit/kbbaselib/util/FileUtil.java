package com.kbit.kbbaselib.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_EXISTS = 2;
    public static final int RESULT_NOT_EXISTS = 3;

    public static boolean sdCardIsMounted() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public static String getFileExtension(String fileName) {
        String extension = "";
        if (fileName == null && fileName.equals("")) {
            return extension;
        }

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    public static String getFileName(String filePath) {
        String fileName = "";
        if (filePath == null && filePath.equals("")) {
            return fileName;
        }

        int i = filePath.lastIndexOf('/');
        if (i > 0) {
            fileName = filePath.substring(i+1);
        }
        return fileName;
    }

    public static int createFile(String fileName) {
        if (fileName == null || fileName.equals("")) {
            return RESULT_FAILURE;
        }

        if (fileName.endsWith(File.separator)) {
            return RESULT_FAILURE;
        }

        File file = new File(fileName);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.mkdir()){
                return RESULT_FAILURE;
            }
        }

        try {
            if(!file.createNewFile()) {
                return RESULT_FAILURE;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("File", "创建文件失败");
        }

        return RESULT_SUCCESS;
    }

    public static int createDirectory(String dirPath) {
        if (dirPath == null || dirPath.equals("")) {
            return RESULT_FAILURE;
        }

        if (!dirPath.endsWith(File.separator)) {
            dirPath = dirPath + File.separator;
        }

        File dir = new File(dirPath);
        if (dir.exists()) {
            return RESULT_EXISTS;
        }

        if (dir.mkdir()) {
            return RESULT_SUCCESS;
        } else {
            return RESULT_FAILURE;
        }
    }

    public static List<String> ListFilesAt(String dirPath, boolean deep) {
        return ListFilesAt(dirPath, null, deep);
    }

    public static List<String> ListFilesAt(String dirPath, String ext, boolean deep) {
        File dirPathFile = new File(dirPath);
        List<String> fileList = new ArrayList<>();
        if (!dirPathFile.exists()) {
            return fileList;
        }

        File[] files = dirPathFile.listFiles();
        if (files == null || files.length == 0) {
            return fileList;
        }

        for (File file : files) {
            if (deep) {
                if (file.isDirectory()) {
                    List<String> subFiles = ListFilesAt(file.getAbsolutePath(), ext, true);
                    if (!ListUtil.isEmpty(subFiles)) {
                        fileList.addAll(subFiles);
                    }
                } else if (file.isFile()) {
                    String path = file.getAbsolutePath();
                    if (ext != null && !ext.equals("")) {
                        if (path.endsWith(ext)) {
                            fileList.add(path);
                        } else {
                            continue;
                        }
                    }

                    fileList.add(path);
                }
            } else {
                if (file.isFile()) {
                    String path = file.getAbsolutePath();
                    if (ext != null && !ext.equals("")) {
                        if (path.endsWith(ext)) {
                            fileList.add(path);
                        } else {
                            continue;
                        }
                    }
                    fileList.add(path);
                }
            }
        }

        return fileList;
    }

    public static int copyFile(String source, String target) {
        if (source == null || source.equals("")) {
            Log.e("File", "源文件不能为空");
            return RESULT_FAILURE;
        }

        if (target == null || target.equals("")) {
            Log.e("File", "目标文件不能为空");
            return RESULT_FAILURE;
        }

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target);

            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("File", "文件未找到");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return RESULT_SUCCESS;
    }

    public static int moveFile(String source, String target) {
        if (source == null || source.equals("")) {
            Log.e("File", "源文件不能为空");
            return RESULT_FAILURE;
        }

        if (target == null || target.equals("")) {
            Log.e("File", "目标文件不能为空");
            return RESULT_FAILURE;
        }

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target);

            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            File targetFile = new File(target);
            if (targetFile.exists()) {
                File sourceFile = new File(source);
                sourceFile.delete();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("File", "文件未找到");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return RESULT_SUCCESS;
    }

    /**
     * Content:// 转 File://
     * */
    public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
