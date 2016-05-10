package com.hubeleon.cdr;


import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.hubeleon.model.CDRInfo;
import com.hubeleon.data.provider.CDRInfoProvider;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;

public class ChargeList2 extends WebPage {
	private static final long serialVersionUID = 1L;

	public ChargeList2()
	{
		setVersioned(false);
		
		// FeedbackPanel //
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		this.add(feedback);

        //DefaultDataTable table = new DefaultDataTable("datatable", newColumnList(), new CDRInfoProvider(), 10);
        AjaxFallbackDefaultDataTable table = new AjaxFallbackDefaultDataTable("datatable", newColumnList(), new CDRInfoProvider(), 10);
        
		table.setOutputMarkupId(true);
		table.setOutputMarkupPlaceholderTag(true);
		add(table);
	}


	private static List<IColumn> newColumnList()
	{
		
		List<IColumn> columns = new ArrayList<IColumn>();

		columns.add(new PropertyColumn<CDRInfo, String>(Model.of("CDR ID"),"ID", "cdrId")); /* Important, for being sent back to server */
		columns.add(new PropertyColumn<CDRInfo, String>(Model.of("EVSE ID"),"EVSEID", "evseId"));
		columns.add(new PropertyColumn<CDRInfo, String>(Model.of("STATUS"),"CDR status", "cdrStatusType"));
		columns.add(new PropertyColumn<CDRInfo, String>(Model.of("startDateTime"),"Start Charge", "startDateTime"));
		columns.add(new PropertyColumn<CDRInfo, String>(Model.of("endDateTime"),"End Charge", "endDateTime"));
		return columns;
	}
}
