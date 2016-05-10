/*
 * Copyright 2008 Kindleit Technologies. All rights reserved. This file, all
 * proprietary knowledge and algorithms it details are the sole property of
 * Kindleit Technologies unless otherwise specified. The software this file
 * belong with is the confidential and proprietary information of Kindleit
 * Technologies. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Kindleit.
 */

package com.hubeleon.wicketlist;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.hubeleon.model.CDRInfo;

/**
 * Page is responsible of
 * 
 * @author rhansen@kindleit.net
 *
 */
public class HomePage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomePage() {

		final WebMarkupContainer divList = new WebMarkupContainer("ochConfirmCDRsTable");
		divList.setOutputMarkupId(true);
		divList.setOutputMarkupPlaceholderTag(true);
		setVersioned(false);

		final List<CDRInfo> cdrInfoArray = new ArrayList();
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
			try (Closeable closeable = ObjectifyService.begin()) {
				System.out.println(" #######  get all cdr list");
				List<CDRInfo> ths = ofy().load().type(CDRInfo.class).list();
				cdrInfoArray.addAll(ths);
	        }
		}
		
		final ListView list = new ListView<CDRInfo>("listview", cdrInfoArray) {

			protected void populateItem(final ListItem<CDRInfo> listItem) {

				final CDRInfo cdr = (CDRInfo) listItem.getModelObject();
				
				System.out.println("Populating List for  " + cdr.getCdrId() + " with status  " + cdr.getCdrStatusType()  + " retrieved from database ");
				
				listItem.setOutputMarkupId(true);
				listItem.setOutputMarkupPlaceholderTag(true);
				
				listItem.add(new Label("cdr", (cdr.getCdrId() != null ? cdr.getCdrId() : "")));
				listItem.add(new Label("evseId", (cdr.getEvseId() != null ? cdr.getEvseId() : "")));
				final Label lblStatus = new Label("status", new PropertyModel<String>(cdr, "cdrStatusType"));
				lblStatus.setOutputMarkupId(true);
				lblStatus.setOutputMarkupPlaceholderTag(true);
				
				listItem.add(lblStatus);

				listItem.add(new Label("startDateTime",
						(cdr.getStartDateTime().toString() != null ? cdr.getStartDateTime().toString() : "")));
				listItem.add(new Label("endDateTime",
						(cdr.getEndDateTime().toString() != null ? cdr.getEndDateTime().toString() : "")));
				listItem.add(new Label("duration", (cdr.getDuration() != null ? cdr.getDuration() : "")));

				listItem.add(new Label("instance", cdr.getInstance() != null ? cdr.getInstance() : ""));
				listItem.add(new Label("tokenType", cdr.getTokenType() != null ? cdr.getTokenType() : ""));
				listItem.add(new Label("tokenSubType", cdr.getTokenSubType() != null ? cdr.getTokenSubType() : ""));
				listItem.add(new Label("contractId", cdr.getContractId() != null ? cdr.getContractId() : ""));
				listItem.add(new Label("liveAuthId", cdr.getLiveAuthId() != null ? cdr.getLiveAuthId() : ""));
				
				final Form<CDRInfo> form = new Form<CDRInfo>("myForm", new Model(cdr)){

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					protected void onSubmit(AjaxRequestTarget target, Form<CDRInfo> form) {
						CDRInfo c = (CDRInfo) this.getModelObject();
						System.err.println("######## STATUS:" + c.getCdrStatusType());
				        //this.setResponsePage(new MovieDisplayPage(movie));
				    };
				};
				
				
				form.setOutputMarkupId(true);
				form.setOutputMarkupPlaceholderTag(true);

				final HiddenField currentState = new HiddenField("currentState", new PropertyModel<String>(cdr, "cdrStatusType") );
				currentState.setOutputMarkupId(true);
				form.add(currentState);

				final HiddenField hcdr = new HiddenField("cdrId", new PropertyModel<String>(cdr, "cdrId") );
				hcdr.setOutputMarkupId(true);
				form.add(hcdr);
				
				final Model<String> btnStyle = new Model<String>("btn-warning");
				
				AjaxButton btn = new AjaxButton("stateAcceptButton",new PropertyModel<String>(cdr,"cdrStatusType"), form) {

					protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
					{
						super.updateAjaxAttributes(attributes);
						attributes.setWicketAjaxResponse(false);
					}
					
					protected void onClick(AjaxRequestTarget target, Form form){
						
					}
					
					protected void onSubmit(AjaxRequestTarget target, Form form){
						System.out.println(" ####### " + hcdr.getDefaultModelObjectAsString() + " - " + currentState.getDefaultModelObjectAsString());
						if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
							try (Closeable closeable = ObjectifyService.begin()) {
								CDRInfo result = (CDRInfo) ofy().load().type(CDRInfo.class).id(hcdr.getDefaultModelObjectAsString()).now();
								result.setCdrStatusType("Accepted");
								System.out.println("Persisting to database " + result.getCdrId() + " - " + result.getCdrStatusType() + " ::: " + target.getLastFocusedElementId());
								ofy().save().entity(result).now();
								setReuseItems(true);
								addStateChange();
								modelChanging();
								// Remove item and invalidate listView
								List<CDRInfo> currentList = (List<CDRInfo>) getList();
								for(CDRInfo i : currentList){
									if (i.getCdrId().equalsIgnoreCase(result.getCdrId())){
										System.out.println("#### Found entry and updating state to : " + result.getCdrStatusType() );
										currentList.remove(i);
										i.setCdrStatusType(result.getCdrStatusType());
										System.out.println("#### set list " + currentList);
										setList(currentList);										
										target.appendJavaScript(" document.getElementById('" + lblStatus.getId() + "').value='" + result.getCdrStatusType() + "';");
										System.out.println("#### model changed ");
										modelChanged();
										System.out.println("#### remove all ");
										removeAll();											
										break;
									}else{
										System.out.println("#### NOT FOUND as charge record " + i.getCdrId() + ", while we are looking for " + result.getCdrId());
									}
								}							
					        }
						}
						target.add(lblStatus);
						setResponsePage(HomePage.class);
					}
					
				};
				
				btn.add(AttributeModifier.append("class", btnStyle));
				
				btn.add(new AttributeModifier("cdrStatusType", cdr)
				 {
			        protected String newValue(final String currentValue, final String replacementValue)
			        {
			        		System.out.println("######## New Value 1:" + replacementValue + " old value " + currentValue);
			                return currentValue + replacementValue;
			        }
			        
				 });
				
				form.add(btn);

				AjaxButton btnReject = new AjaxButton("stateRejectButton",new PropertyModel<String>(cdr,"cdrStatusType"), form) {

					protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
					{						
						super.updateAjaxAttributes(attributes);
						attributes.setWicketAjaxResponse(false);
						System.out.println("########updateAjaxAttributes");
					}
					
					protected void onSubmit(AjaxRequestTarget target, Form form){
						System.out.println(" ####### " + hcdr.getDefaultModelObjectAsString() + " - " + currentState.getDefaultModelObjectAsString());
						if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
							try (Closeable closeable = ObjectifyService.begin()) {
								CDRInfo result = (CDRInfo) ofy().load().type(CDRInfo.class).id(hcdr.getDefaultModelObjectAsString()).now();
									result.setCdrStatusType("Rejected");
								System.out.println("Persisting to database " + result.getCdrId() + " - " + result.getCdrStatusType() + " ::: " + target.getLastFocusedElementId());
								ofy().save().entity(result).now();
								getList().remove(result);
					        }
						}
						target.add(divList);
					}
					
				};
				
				btnReject.add(AttributeModifier.append("class", btnStyle));
				
				btnReject.add(new AttributeModifier("cdrStatusType", cdr)
				 {
			        protected String newValue(final String currentValue, final String replacementValue)
			        {
			        		System.out.println("######## New Value Reject Button:" + replacementValue + " old value " + currentValue);
			                return currentValue + replacementValue;
			        }
			        
				 });
				
				form.add(btnReject);				
				listItem.add(form);

			}
		};
		list.setOutputMarkupId(true);
		list.setOutputMarkupPlaceholderTag(true);
		divList.setOutputMarkupId(true);
		divList.setOutputMarkupPlaceholderTag(true);
		divList.add(list);
		
		add(divList);

	}
}
