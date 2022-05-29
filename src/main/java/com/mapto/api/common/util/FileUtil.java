package com.mapto.api.common.util;

import com.mapto.api.common.model.type.FileType;
import org.springframework.stereotype.Component;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Iterator;

@Component("utils")
public class FileUtil {

    public static String convertFileSize(long fileSize) {
        String bytesStr = "";
        if (-1000 < fileSize && fileSize < 1000) {
            bytesStr =  fileSize + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (fileSize <= -999_950 || fileSize >= 999_950) {
            fileSize /= 1000;
            ci.next();
        }
        bytesStr =  String.format("%.1f %cB", fileSize / 1000.0, ci.current());
        return bytesStr;
    }

    public static FileType toFileType(String type) {
        switch (type) {
            //doc type
            case "pdf": return FileType.pdf;
            case "doc": return FileType.doc;
            case "docx": return FileType.docx;
            case "xls": return FileType.xls;
            case "xlsx": return FileType.xlsx;
            case "ppt": return FileType.ppt;
            case "pptx": return FileType.pptx;

            //image type
            case "bmp": return FileType.bmp;
            case "jpg": return FileType.jpg;
            case "png": return FileType.png;
            case "gif": return FileType.gif;
            case "ico": return FileType.ico;

            //video type
            case "avi": return FileType.avi;
            case "mpg": return FileType.mpg;
            case "mp4": return FileType.mp4;
            case "wmv": return FileType.wmv;
            case "mov": return FileType.mov;
            case "flv": return FileType.flv;
            case "swf": return FileType.swf;
            case "mkv": return FileType.mkv;

        }
        return null;
    }

    public static String getFileType(String path) {
        Iterator<String> it = Arrays.stream(path.toLowerCase().split("\\.")).iterator();
        String result = "";
        while(it.hasNext()) {
            result = it.next();
        }
        return result;
    }
}
