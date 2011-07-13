package toshomal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Comparator;

public class DbFile {

    private int id;
    private Timestamp time;
    private String name;
    private String url;
    private String ext;
    private String md5;
    private int ver;
    private String size;

    public DbFile(ResultSet rs)
    {
        try {
            this.id = rs.getInt("id_file");
            this.time = rs.getTimestamp("time");
            this.name = rs.getString("name");
            this.url = rs.getString("url");
            this.ext = rs.getString("ext");
            this.md5 = rs.getString("md5");
            this.ver = rs.getInt("ver");
            this.size = rs.getString("size");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String toString()
    {
        return String.format("%s %s [%s] [%s] (%s)", time.toString(), name, url, md5, size);
    }

    public Timestamp getUpdateTime()
    {
        return time;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static final Comparator<DbFile> LATEST_FIRST =
            new Comparator<DbFile>()
            {
                public int compare(DbFile a, DbFile b)
                {
                    return b.getUpdateTime().compareTo(a.getUpdateTime());
                }
            };
}
