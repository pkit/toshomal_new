package toshomal.frontend;

import jaxcent.*;
import org.xml.sax.InputSource;

import toshomal.common.EmbeddedDBConnector;
import toshomal.data.DbShow;

import java.util.HashMap;
import java.util.Map;

import static toshomal.common.ToshoReader.searchForShow;

public class ShowEditorDiv extends HtmlDiv
{
    private JaxcentPage tpage;
    private DbShow show;
    private static final String[] statusList =
            {
                    "Select status",
                    "Currently Airing",
                    "Finished Airing",
                    "Not yet aired",
                    "Unknown"
            };
    private static final String[] typeList =
            {
                    "Select type",
                    "TV",
                    "Movie",
                    "OVA",
                    "Special",
                    "Unknown"
            };

    public ShowEditorDiv(JaxcentPage page, SearchType searchType, String text, String[] attributes, String[] values, DbShow show) throws Jaxception {
        super(page, searchType, text, attributes, values);
        this.tpage = page;
        this.show = show;

        HtmlForm form = new HtmlForm(tpage, SearchType.createNew,
                new String[] { "method", "name", "id" },
                new String[] { "post", "EditShowForm", "showForm" }
        );

        HtmlTable tbl = new HtmlTable(tpage, SearchType.createNew,
                new String[] { "class", "border", "cellpadding", "cellspacing", "width" },
                new String[] { "light_bg", "0", "5", "0", "50%" }
        );

        tbl.insertRow(-1,
                new String[] {
                        "Anime Title",
                        "<input type=\"text\" name=\"show_name\" id=\"input_show_name\""
                                + " class=\"inputtext\" value=\""
                                + show.getName()
                                + "\" size=\"40\">"
                },
                new String[][] {
                        { "width", "class", "valign" },
                        { "class" }
                },
                new String[][] {
                        { "130", "borderClass", "top" },
                        { "borderClass" }
                }
        );

        tbl.insertRow(-1,
                new String[] {
                        "MAL ID",
                        "<input type=\"text\" name=\"show_mal_id\" id=\"input_mal_id\" class=\"inputtext\" value=\"" +
                                show.getMalId() +
                                "\" size=\"6\">"
                },
                new String[][] {
                        { "class" },
                        { "class" }
                },
                new String[][] {
                        { "borderClass" },
                        { "borderClass" }
                }
        );

        tbl.insertRow(-1,
                new String[] {
                        "Status",
                        "<select name=\"show_status\" id=\"input_show_status\" class=\"inputtext\">" +
                                buildOptionList(statusList, show.getStatus()) +
                                "</select>"
                },
                new String[][] {
                        { "class" },
                        { "class" }
                },
                new String[][] {
                        { "borderClass" },
                        { "borderClass" }
                }
        );

        tbl.insertRow(-1,
                new String[] {
                        "Type",
                        "<select name=\"show_type\" id=\"input_show_type\" class=\"inputtext\">" +
                                buildOptionList(typeList, show.getType()) +
                                "</select>"
                },
                new String[][] {
                        { "class" },
                        { "class" }
                },
                new String[][] {
                        { "borderClass" },
                        { "borderClass" }
                }
        );

        tbl.insertRow(-1,
                new String[] {
                        "Number of Episodes",
                        "<input type=\"text\" name=\"show_eps\" id=\"input_show_epsnum\" class=\"inputtext\" value=\"" +
                                String.format("%d", show.getEpsNum()) +
                                "\" size=\"3\">"
                },
                new String[][] {
                        { "class", "valign" },
                        { "class" }
                },
                new String[][] {
                        { "borderClass", "top" },
                        { "borderClass" }
                }
        );

        tbl.insertRow(-1,
                new String[] {
                        "Anime Image",
                        "<input type=\"text\" name=\"show_img\" id=\"input_show_img\" class=\"inputtext\" value=\"" +
                                show.getImgUrl() +
                                "\" size=\"55\"><div class=\"picSurround\"><img id=\"input_img_tag\" src=\"" +
                                show.getImgUrl() +
                                "\" border=\"0\"></a></div>"
                },
                new String[][] {
                        { "class", "valign" },
                        { "class" }
                },
                new String[][] {
                        { "borderClass", "center" },
                        { "borderClass" }
                }
        );

        tbl.insertRow(-1,
                new String[] {
                        "<input type=\"button\" id=\"ShowEditSubmitB\" class=\"inputButton\" " +
                                "style=\"font-weight: bold; font-size: 12px;\" " +
                                "value=\"Update & Close\">" +
                                "&nbsp;<input type=\"button\" id=\"ShowEditClose\" class=\"inputButton\" value=\"Close\">",
                },
                new String[][] {
                        { "colspan", "align" }
                },
                new String[][] {
                        { "2", "left" }
                }
        );

        tbl.insertRow(-1,
                new String[] {
                        "Search String",
                        "<input type=\"text\" name=\"show_search\" id=\"input_show_search\" class=\"inputtext\" value=\"" +
                                show.getName() +
                                "\" size=\"50\">" +
                                "<input type=\"button\" id=\"ShowEditSearch\" class=\"inputButton\" value=\"Search\">"
                },
                new String[][] {
                        { "class", "valign" },
                        { "class" }
                },
                new String[][] {
                        { "borderClass", "top" },
                        { "borderClass" }
                }
        );


        HtmlDiv footer = new HtmlDiv(tpage, SearchType.createNew,
                new String[] { "class", "style" },
                new String[] { "borderClass", "clear: both;" }
        );
        tbl.insertAtEnd(form);
        form.insertAtEnd(this);
        footer.insertAtEnd(this);
    }

    private String buildOptionList(String[] list, String status)
    {
        String result = "";
        for(int i = 0; i < list.length; ++i)
        {
            String selected = "";
            if (status.equals(list[i]))
                selected = " selected";
            result += String.format("<option value=\"%d\"%s>%s", i, selected, list[i]);
        }
        return result;
    }

    public void addHandlers() {
        HtmlInputButton search = new HtmlInputButton(tpage, "ShowEditSearch")
        {
            public void onClick(Map pageData)
            {
                try {
                    if (this.getDisabled())
                        return;
                    try {
                        this.setDisabled(true);
                        DbShow show = searchForShow((String) pageData.get("show_search"));
                        if (show == null)
                        {
                            tpage.showMessageDialog("Your search for \"" + pageData.get("show_search") + "\" did not return any results...");
                        }
                        else
                        {
                            HtmlInputText input = new HtmlInputText(tpage, "input_show_name");
                            input.setValue(show.getName());
                            input = new HtmlInputText(tpage, "input_mal_id");
                            input.setValue(show.getMalId());
                            HtmlSelect select = new HtmlSelect(tpage, "input_show_status");
                            for (int i = 0; i < select.getNumOptions(); ++i)
                            {
                                if (select.getOption(i).getValue().equals(show.getStatus()))
                                {
                                    select.setSelectedIndex(i);
                                    break;
                                }
                            }
                            select = new HtmlSelect(tpage, "input_show_type");
                            for (int i = 0; i < select.getNumOptions(); ++i)
                            {
                                if (select.getOption(i).getValue().equals(show.getType()))
                                {
                                    select.setSelectedIndex(i);
                                    break;
                                }
                            }
                            input = new HtmlInputText(tpage, "input_show_epsnum");
                            input.setValue(String.format("%d",show.getEpsNum()));
                            input = new HtmlInputText(tpage, "input_show_img");
                            input.setValue(show.getImgUrl());
                            HtmlImage img = new HtmlImage(tpage, "input_img_tag");
                            img.setSrc(show.getImgUrl());
                        }
                    } finally {
                        this.setDisabled(false);
                    }
                } catch (Jaxception jaxception) {
                    jaxception.printStackTrace();
                }
                //tpage.showMessageDialog("You've searched for: " + pageData.get("show_search"));
            }
        };

        HtmlInputButton update = new HtmlInputButton(tpage, "ShowEditSubmitB")
        {
            public void onClick(Map pageData)
            {
                try {
                    if (this.getDisabled())
                        return;
                    try {
                        this.setDisabled(true);

                        HtmlInputText input = new HtmlInputText(tpage, "input_show_name");
                        String title = input.getValue();
                        input = new HtmlInputText(tpage, "input_mal_id");
                        int malid = Integer.parseInt(input.getValue());
                        HtmlSelect select = new HtmlSelect(tpage, "input_show_status");
                        String status = select.getOption(select.getSelectedIndex()).getText();
                        select = new HtmlSelect(tpage, "input_show_type");
                        String type = select.getOption(select.getSelectedIndex()).getText();
                        input = new HtmlInputText(tpage, "input_show_epsnum");
                        int epsnum = Integer.parseInt(input.getValue());
                        input = new HtmlInputText(tpage, "input_show_img");
                        String img = input.getValue();
                        DbShow newShow = new DbShow(show.getId(), title, malid, status, type, epsnum, img);

                        //tpage.showMessageDialog("type = " + select.getOption(select.getSelectedIndex()).getText() + " index = " + select.getSelectedIndex());

                        EmbeddedDBConnector db = new EmbeddedDBConnector();
                        if(! db.updateShowDetails(newShow, false))
                        {
                            if(tpage.showConfirmDialog("There is an anime with \"" + title + "\" title in the DB\nMerge them into one?"))
                            {
                                db.updateShowDetails(newShow, true);
                                db.mergeShowData(newShow);

                            }
                        }
                    } finally {
                        this.setDisabled(false);
                        tpage.navigate(tpage.getCurrentPath());
                    }
                } catch (Jaxception jaxception) {
                    jaxception.printStackTrace();
                }
            }
        };
        HtmlInputButton close = new HtmlInputButton(tpage, "ShowEditClose")
        {
            public void onClick()
            {
                try {
                    this.deleteElement();
                    //tpage.navigate(tpage.getCurrentPath());
                } catch (Jaxception jaxception) {
                    jaxception.printStackTrace();
                }
            }
        };
    }
}
