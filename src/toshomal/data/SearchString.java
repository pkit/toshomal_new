package toshomal.data;

import static org.apache.commons.lang.StringUtils.join;


public class SearchString {

    private String[] array;
    private int start;
    private int end;
    private int initial_offset = 20;

    public SearchString(String title)
    {
        array = title.split("\\s+");
        int sum = 0;
        int offset = initial_offset * title.length() / 100;
        for (int i = 0; i < array.length; ++i)
        {
            sum += array[i].length();
            if (sum > offset)
            {
                start = i;
                end = i + 1;
                break;
            }
        }
    }

    public String getFirst()
    {
        return join(array, " ", start, end);
    }

    public String getRight()
    {
        if (end == array.length)
            return join(array, " ", start, end);
        else
            return join(array, " ", start, ++end);
    }

    public String getLeft()
    {
        if (start == 0)
            return join(array, " ", start, end);
        else
            return join(array, " ", --start, end);
    }
}
