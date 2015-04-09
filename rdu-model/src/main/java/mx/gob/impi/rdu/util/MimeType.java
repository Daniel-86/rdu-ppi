package mx.gob.impi.rdu.util;

/**
 *
 * @author Infotec
 */
public enum MimeType {

    DOC(1, "doc", "application/msword"),
    DOCX(2, "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    XLS_1(3, "xls", "application/msexcel"),
    XLS_2(4, "xls", "application/vnd.ms-excel"),
    XLSX(5, "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    JPEG(6, "jpg", "image/jpeg"),
    PJPEG(7, "jpg", "image/pjpeg"),
    JPG(8, "jpg", "image/jpg"),
    GIF(9, "gif", "image/gif"),
    PNG(10, "png", "image/png"),
    PDF(11, "pdf", "application/pdf"),
    XML(12, "xml", "application/xml");
    private int id;
    private String extension;
    private String mime;

    private MimeType(int id, String extension, String mime) {
        this.id = id;
        this.extension = extension;
        this.mime = mime;
    }

    public int getId() {
        return id;
    }

    public String getExtension() {
        return extension;
    }

    public String getMime() {
        return mime;
    }

    public MimeType getNext() {
        return values()[(ordinal() + 1) % values().length];
    }
}
