package toshomal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import toshomal.common.Util;

public class DbShow {

    private int id;
    private String name;
    private int malid;
    private String status;
    private String type;
    private int epsnum;
    private String image;
    private ArrayList<DbFile> files;
    private ArrayList<DbEps> eps;
    private ArrayList<DbTag> tags;
    private int maxCount;
    private int minCount;
    private Timestamp updateTime;
    private DbEps latestEps = null;
    //private DbFile latestFile;

    public DbShow(ResultSet rs)
    {
        try {
            this.id = rs.getInt("id_show");
            this.name = rs.getString("name");
            this.malid = rs.getInt("malid");
            this.status = rs.getString("status");
            this.type = rs.getString("type_show").trim();
            this.epsnum = rs.getInt("eps_number");
            this.image = rs.getString("image");
            files = new ArrayList<DbFile>();
            eps = new ArrayList<DbEps>();
            tags = new ArrayList<DbTag>();
            this.updateTime = new Timestamp(0);
            this.maxCount = 0;
            this.minCount = 5;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DbShow(int id, String name, int malid, String status, String type, int epsnum, String image)
    {
        this.id = id;
        this.name = name;
        this.malid = malid;
        this.status = status;
        this.type = type;
        this.epsnum = epsnum;
        this.image = image;
        files = new ArrayList<DbFile>();
        eps = new ArrayList<DbEps>();
        tags = new ArrayList<DbTag>();
        this.updateTime = new Timestamp(0);
        this.maxCount = 0;
        this.minCount = 5;
    }

    public String getMalUrl() {
        return String.format("http://myanimelist.net/anime/%d/", malid);
    }

    public String getImgUrl() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getId()
    {
        return id;
    }

    public boolean addFile(DbFile file)
    {
        //if(! file.getUpdateTime().before(updateTime))
        //    latestFile = file;
        return files.add(file);
    }

    public boolean addEps(DbEps ep)
    {
        return eps.add(ep);
    }

    public boolean addTag(DbTag tag)
    {
        int tcount = tag.getCount();
        if (tcount > maxCount)
            maxCount = tcount;
        //if (tcount < minCount)
        //    minCount = tcount;
        return tags.add(tag);
    }

    public void setFiles(ArrayList<DbFile> files)
    {
        this.files = files;
    }

    public ArrayList<DbTag> getTagsList()
    {
        return tags;
    }

    public ArrayList<DbEps> getEpsList()
    {
        return eps;
    }

    public String getFontSize(DbTag tag)
    {
        return String.format("font-size: %.2fem", 0.7 + Math.log(tag.getCount()) / Math.log(maxCount));
    }


    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void updateLatestEpisode()
    {
        latestEps = eps.get(0);
        for(DbEps ep : eps)
        {
            DbFile file = ep.getLatestFile();
            if (file.getUpdateTime().after(latestEps.getLatestFile().getUpdateTime()))
                latestEps = ep;
        }
    }

    public DbEps getLatestEps()
    {
        return latestEps;
    }

    public int getEpsNum() {
        return epsnum;
    }

    public String getType()
    {
        return type;
    }

    public boolean hasSameId(DbShow show)
    {
        return (this.getId() == show.getId());
    }

    public HashMap<Integer,DbEps> getEpsMap()
    {
        HashMap<Integer,DbEps> result = new HashMap<Integer,DbEps>(Util.nextHashOrd(eps.size()));
        for (DbEps ep : eps)
        {
            result.put(ep.getId(), ep);
        }
        return result;
    }

    public String getMalId()
    {
        return String.format("%d", malid);
    }

/*    public void setId(int id)
    {
        this.id = id;
    }*/
}
