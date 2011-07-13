package toshomal.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbTag {

    private int id;
    private String name;
    private int count;

    public DbTag(ResultSet rs)
    {
        try {
            this.id = rs.getInt("id_tag");
            this.name = rs.getString("name");
            this.count = rs.getInt(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getName()
    {
        return name;
    }

    public int getCount()
    {
        return count;
    }

    public int getId()
    {
        return id;
    }
}
