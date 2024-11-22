package Model;

public class Upload {

    String name;
    String url;
    String songCategory;


    public Upload(String name, String url, String songCategory) {
        this.name = name;
        this.url = url;
        this.songCategory = songCategory;
    }

    public Upload() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUri(String url) {
        this.url = url;
    }

    public String getSongCategory() {
        return songCategory;
    }

    public void setSongCategory(String songCategory) {
        this.songCategory = songCategory;
    }
}
