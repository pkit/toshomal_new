package toshomal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DbEps {

    private int id;
    private int showId;
    private int num;
    private int sub;
    private DbFile latestFile = null;
    private ArrayList<DbFile> files;

    public DbEps(ResultSet rs)
    {
        try {
            this.id = rs.getInt("id_eps");
            this.showId = rs.getInt("id_show");
            this.num = rs.getInt("num");
            this.sub = rs.getInt("sub");
            this.files = new ArrayList<DbFile>();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String toString()
    {
        if (sub == 0)
            return String.format("%02d", num);
        return String.format("%02d.%d", num, sub);
    }

    public int getId()
    {
        return id;
    }

    public boolean addFile(DbFile file)
    {
        if (latestFile == null)
            latestFile = file;
        if(! file.getUpdateTime().before(latestFile.getUpdateTime()))
            latestFile = file;
        return files.add(file);
    }

    public DbFile getLatestFile()
    {
        return latestFile;
    }

    public int getNum() {
        return num;
    }

    public ArrayList<DbFile> getFiles()
    {
        return files;
    }
}
