package in.appfolio.shortexpander.model;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 24/03/15.
 */
public class ExpandModel {
    private String url = "";
    private String contentType = "";

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getContentType () {
        return contentType;
    }

    public void setContentType (String contentType) {
        this.contentType = contentType;
    }
}
