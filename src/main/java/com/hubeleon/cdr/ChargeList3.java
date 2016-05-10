package com.hubeleon.cdr;


import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.hubeleon.model.CDRInfo;
import com.hubeleon.data.provider.CDRInfoProvider;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.CSVDataExporter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.ExportToolbar;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


public class ChargeList3 extends WebPage {
	private static final long serialVersionUID = 1L;

	public ChargeList3()
	{
		setVersioned(false);
		
		// FeedbackPanel //
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		this.add(feedback);

		List<IColumn<CDRInfo, String>> columns = newColumnList();
		CDRInfoProvider dataProvider = new CDRInfoProvider();
		
		DataTable<CDRInfo, String> dataTable = new DefaultDataTable("datatable", columns, dataProvider, 10);
		dataTable.setOutputMarkupId(true);
		dataTable.setOutputMarkupPlaceholderTag(true);

        add(dataTable);
        
	}


	private static List<IColumn<CDRInfo, String>> newColumnList()
	{
		
		 List<IColumn<CDRInfo, String>> columns = new ArrayList<IColumn<CDRInfo, String>>();

		 columns.add(new AbstractColumn<CDRInfo, String>(new Model<String>("Actions")
	        {
	            @Override
	            public void populateItem(Item<ICellPopulator<CDRInfo>> cellItem, String componentId,
	                IModel<CDRInfo> model)
	            {
	                cellItem.add(new ActionPanel(componentId, model));
	            }
	        });
		 
        columns.add(new AbstractColumn<CDRInfo, String>(new Model<String>("Actions"))
        {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void populateItem(Item<ICellPopulator<CDRInfo>> cellItem, String componentId,
                IModel<CDRInfo> model)
            {
                cellItem.add(new ActionPanel(componentId, model));
            }
        });

        columns.add(new PropertyColumn<CDRInfo, String>(new Model<String>("CDR ID"), "cdrId")
        {
            @Override
            public String getCssClass()
            {
                return "numeric";
            }
        });

        columns.add(new PropertyColumn<CDRInfo, String>(Model.of("EVSE ID"), "evseId", "evseId"));

        columns.add(new PropertyColumn<CDRInfo, String>(Model.of("STATUS"), "cdrStatusType", "cdrStatusType")
        {
            @Override
            public String getCssClass()
            {
                return "last-name";
            }
        });

        columns.add(new PropertyColumn<CDRInfo, String>(Model.of("startDateTime"), "startDateTime"));
        columns.add(new PropertyColumn<CDRInfo, String>(Model.of("endDateTime"), "endDateTime"));
        
		return columns;
	}
}
