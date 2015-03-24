package in.appfolio.shortexpander.helper;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 24/03/15.
 */
public class DetectTypeHelper {
    public enum Type {
        Image, Video, Audio, File, Unknown
    }

    public static Type who (String url, String contentType) {
        Type type = Type.Unknown;
        if (!TextUtils.isEmpty(contentType)) {
            type = whoByContentType(contentType);
        }
        if (Type.Unknown.equals(type)) {
            return whoByUrl(url);
        }
        return type;
    }

    public static Type whoByContentType (String contentType) {
        switch (contentType) {
            case "image/gif":
            case "image/jpeg":
            case "image/pjpeg":
            case "image/png":
            case "image/svg+xml":
            case "image/tiff":
            case "image/vnd.microsoft.icon":
            case "image/vnd.wap.wbmp": //:D
                return Type.Image;
            case "application/pdf":
            case "application/zip":
                return Type.File;
            case "video/mpeg":
            case "video/mp4":
            case "video/ogg":
            case "video/quicktime":
            case "video/webm":
            case "video/x-flv":
            case "video/x-ms-wmv":
                return Type.Video;
            case "audio/mp4":
            case "audio/mp3":
            case "audio/ogg":
            case "audio/webm":
                return Type.Audio;
            default:
                return Type.Unknown;
        }
    }

    private static Type whoByUrl (String url) {
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String ext = MimeTypeMap.getFileExtensionFromUrl(url);
        String contentType = map.getMimeTypeFromExtension(ext);
        if (TextUtils.isEmpty(contentType)) {
            return Type.Unknown;
        }
        return whoByContentType(contentType);
    }
}
