package anonymous.line.bloockgle.bloockgle;

/**
 * Created by Mr.Marshall on 30/09/2015.
 */
public enum FileType {

    IMAGE, AUDIO, VIDEO, TXT, OTHER, LINK;

    public static FileType getFileType (String type) {
        // Convertimos el paramatro type a minuscula
        type = type.toLowerCase();
        if (type.equals("text") || type.equals("texto"))  {
            return FileType.TXT;
        } else if (type.equals("image")|| type.equals("img") || type.equals("imagen")){
            return FileType.IMAGE;
        } else if (type.equals("audio")) {
            return FileType.AUDIO;
        } else if (type.equals("video")){
            return FileType.VIDEO;
        } else if (type.equals("link") || type.equals("enlace")){
            return FileType.LINK;
        } else {
            return FileType.OTHER;
        }
    }

}
