package com.hubeleon.cdr;

import com.hubeleon.data.dao.CDRInfoDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.hubeleon.model.CDRInfo;
import com.hubeleon.data.provider.CDRInfoProvider;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.CurrencyPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.DatePropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IdPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.PropertyColumn;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class ChargeList extends WebPage {
	private static final long serialVersionUID = 1L;

	public ChargeList()
	{
		setVersioned(false);
		
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		// DataTable //
		Options options = new Options();
		options.set("height", 430);
		options.set("editable", Options.asString("inline"));
		options.set("pageable", true);
		options.set("toolbar", "[ { name: 'create', text: 'New' } ]"); /* 'toolbar' option can be used as long as #getToolbarButtons returns no button */

		final DataTable<CDRInfo> table = new DataTable<CDRInfo>("datatable", newColumnList(), newDataProvider(), 25, options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onCancel(AjaxRequestTarget target)
			{
				this.info("Cancelled...");
				target.add(feedback);
			}

			@Override
			public void onCreate(AjaxRequestTarget target, JSONObject object)
			{
				CDRInfo cdrInfo = CDRInfo.of(object);
				CDRInfoDAO.insert(cdrInfo);

				this.warn("Inserted #" + cdrInfo.getCdrId());
				target.add(feedback);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, JSONObject object)
			{
				CDRInfo cdrInfo = CDRInfo.of(object);
				CDRInfoDAO.update(cdrInfo);

				this.warn("Updated #" + cdrInfo.getCdrId());
				target.add(feedback);
			}

			@Override
			public void onDelete(AjaxRequestTarget target, JSONObject object)
			{
				CDRInfo cdrInfo = CDRInfo.of(object);
				CDRInfoDAO.delete(cdrInfo);

				this.warn("Deleted #" + cdrInfo.getCdrId());
				target.add(feedback);
			}
		};
		table.setOutputMarkupId(true);
		table.setOutputMarkupPlaceholderTag(true);
		add(table);
	}

	private static IDataProvider<CDRInfo> newDataProvider()
	{
		return new CDRInfoProvider();
	}

	private static List<IColumn> newColumnList()
	{
		
		List<IColumn> columns = new ArrayList<IColumn>();

		columns.add(new IdPropertyColumn("ID", "cdrId")); /* Important, for being sent back to server */
		columns.add(new PropertyColumn("EVSEID", "evseId"));
		columns.add(new PropertyColumn("CDR status", "cdrStatusType"));
		columns.add(new DatePropertyColumn("Start Charge", "startDateTime"));
		columns.add(new CurrencyPropertyColumn("End Charge", "endDateTime", 100));

		columns.add(new CommandColumn("", 340) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<CommandButton> newButtons()
			{
				/*
				 * 'edit' and 'destroy' are built-in buttons/commands, no property has to be to supply #onUpdate(AjaxRequestTarget target, JSONObject object) will be triggered #onDelete(AjaxRequestTarget target, JSONObject object) will be
				 * triggered #onClick(AjaxRequestTarget, CommandButton, String) will not be triggered
				 */
				return Arrays.asList(new CommandButton("edit", Model.of("Accept")), new CommandButton("destroy", Model.of("Reject")));
			}
		});

		return columns;
	}
}
