package toshomal.data;

import java.util.List;

public class ParsedFileName {

    String name;
    String eps;
    String sub;
    String ver;
    String ext;
    List<String> tags;
    String search;
    String md5;

    public ParsedFileName(String name, String eps, String sub, String ver, String ext, String md5, List<String> tags, String search) {
        this.name = name;
        this.eps = eps;
        this.sub = sub;
        this.ver = ver;
        this.ext = ext;
        this.tags = tags;
        this.search = search;
        this.md5 = md5;
        System.out.println(String.format("parsed file->>> %s - %s - %s - %s - %s - %s - %s", eps, sub, ver, ext, tags, search, md5));
    }

    public String getEps() {
        return eps;
    }

    public String getSub() {
        return sub;
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public String getVer() {
        return ver;
    }

    public String getMD5() {
        return md5;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getSearch() {
        return search;
    }
}
