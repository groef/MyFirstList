package com.hubeleon.main;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class BasePage extends WebPage {
	
	 public BasePage()
	    {
	        add(new BookmarkablePageLink<Void>("back", Index.class).setAutoEnable(true));
	    }
}
