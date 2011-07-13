package toshomal.common;

import java.util.Comparator;

import toshomal.data.DbShow;

public class Util
{
    public static final Comparator<DbShow> LATEST_FIRST =
            new Comparator<DbShow>()
            {
                public int compare(DbShow a, DbShow b)
                {
                    return b.getUpdateTime().compareTo(a.getUpdateTime());
                }
            };

    public static final Comparator<DbShow> LATEST_LAST =
            new Comparator<DbShow>()
            {
                public int compare(DbShow a, DbShow b)
                {
                    return a.getUpdateTime().compareTo(b.getUpdateTime());
                }
            };

    public static int nextHashOrd(int i)
    {
        int v = i;
        --v;
        v |= v >> 1;
        v |= v >> 2;
        v |= v >> 4;
        v |= v >> 8;
        v |= v >> 16;
        return ++v;
    }
}
