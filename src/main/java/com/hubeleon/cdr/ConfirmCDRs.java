/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hubeleon.wicket.clearing;

import com.hubeleon.business.ICDRInfoBO;
import com.hubeleon.dto.DeviceDTO;
import com.hubeleon.model.CDRInfoModel;
import com.hubeleon.wicket.principal.BaseWebPage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 *
 * @author Francois.Groenewald
 */
public class ConfirmCDRs extends BaseWebPage {
    private static final Logger LOG = Logger.getLogger(ConfirmCDRs.class.getName());
    private static final String CLASS_NAME = "ConfirmCDRs";
    
    @SpringBean
    private ICDRInfoBO cdrInfoBO;

    
    public ConfirmCDRs(PageParameters parameters) {
        LOG.entering(CLASS_NAME, "Constructor", parameters);
        populateScreen();
        LOG.exiting(CLASS_NAME, "Constructor");
    }

    public ConfirmCDRs() {
        LOG.entering(CLASS_NAME, "Constructor");
        populateScreen();
        LOG.exiting(CLASS_NAME, "Constructor");
    }

    private void populateScreen() {
        LOG.entering(CLASS_NAME, "populateScreen");
        
        List<CDRInfoModel> cdrInfoArray = new ArrayList();
        
        LOG.warning("cdrInfoList result before = " + cdrInfoArray.size());
        cdrInfoArray.addAll(cdrInfoBO.findForReview());
        LOG.warning("cdrInfoList result after = " + cdrInfoArray.size());
        
        final  WebMarkupContainer divList  = new WebMarkupContainer("ochConfirmCDRsTable");
        
        final ListView list = new ListView<CDRInfoModel>("listview", cdrInfoArray){

            protected void populateItem(final ListItem<CDRInfoModel> listItem) {
                                
                CDRInfoModel cdr = (CDRInfoModel) listItem.getModelObject();   
                
                listItem.add(new Label("cdrId", (cdr.getCdrId() != null ? cdr.getCdrId() : "")));
                listItem.add(new Label("evseId", (cdr.getEvseId()!= null ? cdr.getEvseId() : "")));
                
                listItem.add(new Label("startDateTime", (cdr.getStartDateTime().toString() != null ? cdr.getStartDateTime().toString() : "")));
                listItem.add(new Label("endDateTime", (cdr.getEndDateTime().toString() != null ? cdr.getEndDateTime().toString() : "")));
                listItem.add(new Label("duration", (cdr.getDuration() != null ? cdr.getDuration() : "")));
                
                listItem.add(new Label("instance", cdr.getInstance()!= null ? cdr.getInstance() : ""));
                listItem.add(new Label("tokenType", cdr.getTokenType() != null ? cdr.getTokenType() : "" ));
                listItem.add(new Label("tokenSubType", cdr.getTokenSubType() != null ? cdr.getTokenSubType() : "" ));
                listItem.add(new Label("contractId", cdr.getContractId() != null ? cdr.getContractId() : "" ));
                listItem.add(new Label("liveAuthId", cdr.getLiveAuthId() != null ? cdr.getLiveAuthId() : "" ));
                
            }
        };
        list.setOutputMarkupId(true);
        list.setOutputMarkupPlaceholderTag(true);
        divList.setOutputMarkupId(true);
        divList.setOutputMarkupPlaceholderTag(true);
        divList.add(list);
        add(divList);
        
        LOG.exiting(CLASS_NAME, "populateScreen");
    }
}
