package org.openmrs.module.hospitalrestcore.inventory.mainstore.controller;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.inventory.ActionValue;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStore;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransaction;
import org.openmrs.module.hospitalrestcore.util.PagingUtil;
import org.openmrs.module.hospitalrestcore.util.RequestUtil;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mujuzi Moses
 *
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/receiptsToStoreList/")
public class ReceiptsToMainStoreListController extends BaseRestController {

    @RequestMapping(value = "/receipt ", method = RequestMethod.GET)
    public void getReceiptList(@RequestParam(value="pageSize",required=false)  Integer pageSize,
                               @RequestParam(value="currentPage",required=false)  Integer currentPage,
                               @RequestParam(value="receiptName",required=false)  String receiptName,
                               @RequestParam(value="fromDate",required=false)  String fromDate,
                               @RequestParam(value="toDate",required=false)  String toDate, Map<String, Object> model,
                               HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStore store = hospitalRestCoreService
                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
        int total = hospitalRestCoreService.countStoreDrugTransaction(ActionValue.TRANSACTION[0], store.getId(), receiptName, fromDate, toDate);
        String temp = "";
        if(receiptName != null){
            if(StringUtils.isBlank(temp)){
                temp = "?receiptName=" + receiptName;
            }else{
                temp +="&receiptName=" + receiptName;
            }
        }
        if(!StringUtils.isBlank(fromDate)){
            if(StringUtils.isBlank(temp)){
                temp = "?fromDate=" + fromDate;
            }else{
                temp +="&fromDate=" + fromDate;
            }
        }
        if(!StringUtils.isBlank(toDate)){
            if(StringUtils.isBlank(temp)){
                temp = "?toDate=" + toDate;
            }else{
                temp +="&toDate=" + toDate;
            }
        }

        PagingUtil pagingUtil = new PagingUtil(RequestUtil.getCurrentLink(request) + temp, pageSize, currentPage,
                total);
        List<InventoryStoreDrugTransaction> transactions = hospitalRestCoreService.listStoreDrugTransaction(
                ActionValue.TRANSACTION[0], store.getId(), receiptName, fromDate, toDate,pagingUtil.getStartPos(),
                pagingUtil.getPageSize());
        model.put("receiptName", receiptName );
        model.put("pagingUtil", pagingUtil );
        model.put("transactions", transactions );
        new ObjectMapper().writeValue(out, transactions);

    }

//    @RequestMapping(value = "/item", method = RequestMethod.GET)
//    public void getItemList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
//            @RequestParam(value = "currentPage", required = false) Integer currentPage,
//            @RequestParam(value = "itemName", required = false) String itemName,
//            @RequestParam(value = "fromDate", required = false) String fromDate,
//            @RequestParam(value = "toDate", required = false) String toDate, Map<String, Object> model,
//            HttpServletRequest request, HttpServletResponse response)
//            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {
//
//    }
}