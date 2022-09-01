package org.openmrs.module.hospitalrestcore.inventory.mainstore.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugDetails;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugIndent;
import org.openmrs.module.hospitalrestcore.util.PagingUtil;
import org.openmrs.module.hospitalrestcore.util.RequestUtil;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mujuzi Moses
 *
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/transferDrugFromGeneralStore/")
public class TransferDrugFromMainStoreController extends BaseRestController {

    @RequestMapping(value = "/indents", method = RequestMethod.GET)
    public void getIndentList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                              @RequestParam(value = "currentPage", required = false) Integer currentPage,
                              @RequestParam(value = "storeName", required = false) String storeName,
                              @RequestParam(value = "indentStatus", required = false) String indentStatus,
                              @RequestParam(value = "indentName", required = false) String indentName,
                              @RequestParam(value = "fromDate", required = false) String fromDate,
                              @RequestParam(value = "toDate", required = false) String toDate, Map<String, Object> model,
                              HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        int total = hospitalRestCoreService.countStoreDrugFromGeneralStore(storeName, indentStatus, indentName, fromDate, toDate);
        String temp = "";
        if (storeName != null) {
            temp = "?storeName=" + storeName;
        }
        if (indentName != null) {
            if (StringUtils.isBlank(temp)) {
                temp = "?indentName=" + indentName;
            } else {
                temp += "&indentName=" + indentName;
            }
        }
        if (indentStatus != null) {
            if (StringUtils.isBlank(temp)) {
                temp = "?indentStatus=" + indentStatus;
            } else {
                temp += "&indentStatus=" + indentStatus;
            }

        }
        if (fromDate != null) {
            if (StringUtils.isBlank(temp)) {
                temp = "?fromDate=" + fromDate;
            } else {
                temp += "&fromDate=" + fromDate;
            }
        }
        if (toDate != null) {
            if (StringUtils.isBlank(temp)) {
                temp = "?toDate=" + toDate;
            } else {
                temp += "&toDate=" + toDate;
            }
        }

        PagingUtil pagingUtil = new PagingUtil(RequestUtil.getCurrentLink(request) + temp, pageSize, currentPage,
                total);
        List<InventoryStoreDrugIndent> inventoryStoreDrugIndents = hospitalRestCoreService.listStoreDrugFromGeneralStore(
                storeName, indentStatus, indentName, fromDate, toDate, pagingUtil.getStartPos(), pagingUtil.getPageSize());

        List<InventoryStoreDrugDetails> indents = inventoryStoreDrugIndents.stream()
                .map(isdi -> getInventoryStoreDrugIndentDetails(isdi)).collect(Collectors.toList());

        if(indents != null)
            Collections.sort(indents);
        model.put("store", storeName);
        model.put("indentName", indentName);
        model.put("indentStatus", indentStatus);
        model.put("pagingUtil", pagingUtil);
        model.put("indents", indents);
        new ObjectMapper().writeValue(out, indents);

    }

    @RequestMapping(value = "/indent-details", method = RequestMethod.GET)
    public void getIndentDetails(@RequestParam(value = "indentName", required = false) String indentName,
                                 @RequestParam(value = "indentId", required = false) Integer indentId,
                                 HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryStoreDrugIndent> inventoryStoreDrugIndents = hospitalRestCoreService.listAllInventoryStoreDrug();
        List<InventoryStoreDrugIndent> drugIndents = new ArrayList<>();

        if (indentName != null)
            for (InventoryStoreDrugIndent indent : inventoryStoreDrugIndents)
                if (Objects.equals(indent.getName(), indentName))
                    if (Objects.equals(indent.getId(), indentId))
                        drugIndents.add(indent);

        List<InventoryStoreDrugDetails> isdds = drugIndents.stream()
                .map(isd -> getInventoryStoreDrugIndentDetails(isd)).collect(Collectors.toList());

        new ObjectMapper().writeValue(out, isdds);
    }

    //TODO: process indent while status is waiting
    @RequestMapping(value = "/process-indent", method = RequestMethod.PUT)
    public ResponseEntity<Void> processIndentRequest(HttpServletRequest request, HttpServletResponse response) {

        return null;
    }

    public InventoryStoreDrugDetails getInventoryStoreDrugIndentDetails(InventoryStoreDrugIndent inventoryStoreDrugIndent) {
        SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
        InventoryStoreDrugDetails isdd = new InventoryStoreDrugDetails();
        isdd.setStore(inventoryStoreDrugIndent.getStore().getName());
        isdd.setName(inventoryStoreDrugIndent.getName());
        isdd.setCreatedDate(formatterExt.format(inventoryStoreDrugIndent.getCreatedDate()));
        isdd.setMainStoreStatus(inventoryStoreDrugIndent.getMainStoreStatus());
        return isdd;
    }

}
