package org.openmrs.module.hospitalrestcore.inventory.substore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.inventory.*;
import org.openmrs.module.hospitalrestcore.util.PagingUtil;
import org.openmrs.module.hospitalrestcore.util.RequestUtil;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mujuzi Moses
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/indentDrugList/")
public class IndentDrugListController extends BaseRestController {

    @RequestMapping(value = "/add-to-slip", method = RequestMethod.POST)
    public ResponseEntity<Void> addToSlip(HttpServletRequest request, HttpServletResponse response,
                                          @Valid @RequestBody InventoryStoreDrugIndentDetailPayload inventoryStoreDrugIndentDetailPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreDrugIndentDetail drugIndentDetail = new InventoryStoreDrugIndentDetail();
        drugIndentDetail.setDrug(inventoryStoreDrugIndentDetailPayload.getDrug());
        drugIndentDetail.setFormulation(inventoryStoreDrugIndentDetailPayload.getFormulation());
        drugIndentDetail.setQuantity(inventoryStoreDrugIndentDetailPayload.getQuantity());
        drugIndentDetail.setCreatedDate(new Date());
        drugIndentDetail.setDeleted(false);
        hospitalRestCoreService.saveOrUpdateInventoryStoreDrugIndentDetail(drugIndentDetail);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/indent-slip", method = RequestMethod.GET)
    public void getIndentList(Map<String, Object> model,
                              HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryStoreDrugIndentDetail> indentDetails = hospitalRestCoreService.listAllInventoryStoreDrugIndentDetail();

        List<InventoryStoreDrugIndentDetails> indents = indentDetails.stream().
                map(id -> getInventoryStoreDrugIndentDetails(id)).collect(Collectors.toList());

        if (indents != null)
            Collections.sort(indents);
        model.put("indents", indents);
        new ObjectMapper().writeValue(out, indents);

    }

    @RequestMapping(value = "/save-and-send", method = RequestMethod.POST)
    public ResponseEntity<Void> saveAndSend(@RequestBody String indentName,
                                            HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStore store = hospitalRestCoreService
                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));

        List<InventoryStoreDrugIndentDetail> indentDetails = hospitalRestCoreService.listAllInventoryStoreDrugIndentDetail();

        InventoryStoreDrugTransaction transaction = new InventoryStoreDrugTransaction();
        transaction.setStore(store);
        transaction.setStatus(0);
        transaction.setTypeTransaction(ActionValue.TRANSACTION[0]);
        transaction.setCreatedDate(new Date());
        transaction.setCreatedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateStoreDrugTransaction(transaction);

        for (InventoryStoreDrugIndentDetail d : indentDetails) {

            InventoryReceiptForm form = new InventoryReceiptForm();
            List<InventoryReceiptForm> forms = hospitalRestCoreService.listAllInventoryReceiptForm();

            for (InventoryReceiptForm f : forms)
                if (Objects.equals(f.getDrug().getName(), d.getDrug().getName()))
                    form = f;

            if (form.getDrug() == null)
                throw new ResourceNotFoundException(
                        String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG, d.getDrug().getName()));

            InventoryStoreDrugTransactionDetail transactionDetail = new InventoryStoreDrugTransactionDetail();
            transactionDetail.setTransaction(transaction);
            transactionDetail.setDrug(d.getDrug());
            transactionDetail.setFormulation(d.getFormulation());
            transactionDetail.setQuantity(d.getQuantity());
            transactionDetail.setCurrentQuantity(form.getQuantity());
            transactionDetail.setIssueQuantity(0);
            transactionDetail.setUnitPrice(BigDecimal.valueOf(form.getRate()));
            transactionDetail.setTotalPrice(form.getTotalAmount());
            transactionDetail.setVAT(form.getVat());
            transactionDetail.setBatchNo(form.getBatchNo());
            transactionDetail.setCompanyName(form.getCompanyName());
            transactionDetail.setDateManufacture(form.getDateManufacture());
            transactionDetail.setDateExpiry(form.getDateExpiry());
            transactionDetail.setOpeningBalance(form.getQuantity());
            transactionDetail.setClosingBalance((form.getQuantity() + d.getQuantity()));
            transactionDetail.setReceiptDate(form.getReceiptDate());
            transactionDetail.setCreatedDate(new Date());
            transactionDetail.setCreatedBy(Context.getAuthenticatedUser());
            hospitalRestCoreService.saveOrUpdateDrugTransactionDetail(transactionDetail);
            d.setDeleted(true);
            hospitalRestCoreService.saveOrUpdateInventoryStoreDrugIndentDetail(d);
        }

        InventoryStoreDrugIndent indent = new InventoryStoreDrugIndent();
        indent.setStore(store);
        indent.setName(indentName);
        indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[1]); //SENT
        indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[0]); //WAITING
        indent.setTransaction(transaction);
        indent.setCreatedDate(new Date());
        indent.setCreatedBy(Context.getAuthenticatedUser());

        hospitalRestCoreService.saveOrUpdateInventoryDrugIndent(indent);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/clear-indent", method = RequestMethod.DELETE)
    public ResponseEntity<Void> clearIndent(HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryStoreDrugIndentDetail> indentDetails = hospitalRestCoreService.listAllInventoryStoreDrugIndentDetail();

        for (InventoryStoreDrugIndentDetail detail : indentDetails) {
            detail.setDeleted(true);
            hospitalRestCoreService.saveOrUpdateInventoryStoreDrugIndentDetail(detail);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/accept-indent", method = RequestMethod.PUT)
    public ResponseEntity<Void> acceptIndent(HttpServletRequest request, HttpServletResponse response,
                                             @Valid @RequestBody InventoryStoreDrugTransactionPayload inventoryStoreDrugTransactionPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStore store = hospitalRestCoreService
                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));

        InventoryStoreDrugIndent indent = hospitalRestCoreService.getInventoryStoreDrugIndentByUuidString(
                inventoryStoreDrugTransactionPayload.getIndentUuid());

        if (indent == null)
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_INDENT,
                            inventoryStoreDrugTransactionPayload.getIndentUuid()));

        InventoryStoreDrugTransaction inventoryStoreDrugTransaction = hospitalRestCoreService.getInventoryStoreDrugTransactionByUuidString(
                inventoryStoreDrugTransactionPayload.getTransactionUuid());

        if (inventoryStoreDrugTransaction == null)
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_TRANSACTION,
                            inventoryStoreDrugTransactionPayload.getTransactionUuid()));

        List<InventoryStoreDrugTransactionDetail> inventoryStoreDrugTransactionDetails = hospitalRestCoreService.listAllStoreDrugTransactionDetail();
        List<InventoryStoreDrugTransactionDetail> transactionDetails = new ArrayList<>();

        for (InventoryStoreDrugTransactionDetail detail : inventoryStoreDrugTransactionDetails)
            if (Objects.equals(detail.getTransaction(), inventoryStoreDrugTransaction))
                transactionDetails.add(detail);

        List<InventoryStoreDrug> drugs = hospitalRestCoreService.listAllInventoryStoreDrug(store);
        for (InventoryStoreDrugTransactionDetail d : transactionDetails) {
            InventoryStoreDrug inventoryStoreDrug = new InventoryStoreDrug();
            for (InventoryStoreDrug drug : drugs)
                if (Objects.equals(drug.getDrug().getName(), d.getDrug().getName()))
                    if (Objects.equals(drug.getFormulation().getName(), d.getFormulation().getName()))
                        inventoryStoreDrug = drug;

            if (inventoryStoreDrug.getDrug() == null) {
                inventoryStoreDrug.setStore(store);
                inventoryStoreDrug.setDrug(d.getDrug());
                inventoryStoreDrug.setFormulation(d.getFormulation());
                inventoryStoreDrug.setCurrentQuantity(d.getQuantity());
                inventoryStoreDrug.setReceiptQuantity(d.getQuantity());
                inventoryStoreDrug.setIssueQuantity(0);
                inventoryStoreDrug.setStatusIndent(1);
                inventoryStoreDrug.setReorderQuantity(d.getQuantity());
                inventoryStoreDrug.setOpeningBalance(0);
                inventoryStoreDrug.setClosingBalance(d.getCurrentQuantity());
                inventoryStoreDrug.setStatus(1);
                inventoryStoreDrug.setCreatedDate(new Date());
                hospitalRestCoreService.saveOrUpdateInventoryStoreDrug(inventoryStoreDrug);
            } else {
                inventoryStoreDrug.setCurrentQuantity((inventoryStoreDrug.getCurrentQuantity() + d.getQuantity()));
                inventoryStoreDrug.setReceiptQuantity(d.getQuantity());
                inventoryStoreDrug.setStatusIndent(1);
                inventoryStoreDrug.setReorderQuantity(d.getQuantity());
                inventoryStoreDrug.setOpeningBalance(inventoryStoreDrug.getClosingBalance());
                inventoryStoreDrug.setClosingBalance((inventoryStoreDrug.getClosingBalance() + d.getQuantity()));
                inventoryStoreDrug.setStatus(1);
                hospitalRestCoreService.saveOrUpdateInventoryStoreDrug(inventoryStoreDrug);
            }

        }

        indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[4]); // DONE
        indent.setLastModifiedDate(new Date());
        indent.setLastModifiedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateInventoryDrugIndent(indent);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/refuse-indent", method = RequestMethod.PUT)
    public ResponseEntity<Void> refuseIndent(HttpServletRequest request, HttpServletResponse response,
                                             @Valid @RequestBody InventoryStoreDrugTransactionPayload inventoryStoreDrugTransactionPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreDrugIndent indent = hospitalRestCoreService.getInventoryStoreDrugIndentByUuidString(
                inventoryStoreDrugTransactionPayload.getIndentUuid());

        if (indent == null)
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_INDENT,
                            inventoryStoreDrugTransactionPayload.getIndentUuid()));

        indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[3]);
        indent.setLastModifiedDate(new Date());
        indent.setLastModifiedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateInventoryDrugIndent(indent);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/indents", method = RequestMethod.GET)
    public void getIndentList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                              @RequestParam(value = "currentPage", required = false) Integer currentPage,
                              @RequestParam(value = "indentStatus", required = false) String indentStatus,
                              @RequestParam(value = "indentName", required = false) String indentName,
                              @RequestParam(value = "fromDate", required = false) String fromDate,
                              @RequestParam(value = "toDate", required = false) String toDate, Map<String, Object> model,
                              HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStore store = hospitalRestCoreService
                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));


        int total = hospitalRestCoreService.countStoreDrugIndent(store.getId(), null, indentStatus, indentName, fromDate, toDate);
        String temp = "";

        if (indentName != null)
            temp = "?indentName=" + indentName;
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
        List<InventoryStoreDrugIndent> inventoryStoreDrugIndents = hospitalRestCoreService.listStoreDrugIndent(store.getId(),
                null, indentStatus, indentName, fromDate, toDate, pagingUtil.getStartPos(), pagingUtil.getPageSize());

        List<InventoryStoreDrugDetails> indents = inventoryStoreDrugIndents.stream()
                .map(isdi -> getInventoryStoreDrugDetails(isdi)).collect(Collectors.toList());

        if (indents != null)
            Collections.sort(indents);
        model.put("indentName", indentName);
        model.put("indentStatus", indentStatus);
        model.put("pagingUtil", pagingUtil);
        model.put("indents", indents);
        new ObjectMapper().writeValue(out, indents);
    }

    @RequestMapping(value = "/indent-details", method = RequestMethod.GET)
    public void getIndentDetail(@RequestParam(value = "indentUuid") String indentUuid,
                                HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreDrugIndent drugIndent = hospitalRestCoreService.getInventoryStoreDrugIndentByUuidString(indentUuid);

        if (drugIndent == null)
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_INDENT, indentUuid));

        InventoryStoreDrugTransaction inventoryStoreDrugTransaction = drugIndent.getTransaction();
        List<InventoryStoreDrugTransactionDetail> inventoryStoreDrugTransactionDetails = hospitalRestCoreService.listAllStoreDrugTransactionDetail();
        List<InventoryStoreDrugTransactionDetail> transactionDetails = new ArrayList<>();

        for (InventoryStoreDrugTransactionDetail detail : inventoryStoreDrugTransactionDetails)
            if (Objects.equals(detail.getTransaction(), inventoryStoreDrugTransaction))
                transactionDetails.add(detail);

        List<InventoryStoreDrugTransactionDetails> isdtds = transactionDetails.stream()
                .map(dtd -> getInventoryStoreDrugTransactionDetails(dtd)).collect(Collectors.toList());

        new ObjectMapper().writeValue(out, isdtds);
    }

    public InventoryStoreDrugTransactionDetails getInventoryStoreDrugTransactionDetails(InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail) {
        InventoryStoreDrugTransactionDetails isdtds = new InventoryStoreDrugTransactionDetails();
        isdtds.setDrugName(inventoryStoreDrugTransactionDetail.getDrug().getName());
        isdtds.setDrugFormulation(inventoryStoreDrugTransactionDetail.getFormulation().getName());
        isdtds.setQuantity(inventoryStoreDrugTransactionDetail.getQuantity());
        isdtds.setCurrentQuantity(inventoryStoreDrugTransactionDetail.getCurrentQuantity());
        isdtds.setBatchNo(inventoryStoreDrugTransactionDetail.getBatchNo());
        isdtds.setCompanyName(inventoryStoreDrugTransactionDetail.getCompanyName());
        return isdtds;
    }

    public InventoryStoreDrugDetails getInventoryStoreDrugDetails(InventoryStoreDrugIndent inventoryStoreDrugIndent) {
        SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
        InventoryStoreDrugDetails isdd = new InventoryStoreDrugDetails();
        isdd.setStore(inventoryStoreDrugIndent.getStore().getName());
        isdd.setName(inventoryStoreDrugIndent.getName());
        isdd.setCreatedDate(formatterExt.format(inventoryStoreDrugIndent.getCreatedDate()));
        isdd.setSubStoreStatus(inventoryStoreDrugIndent.getSubStoreStatus());
        return isdd;
    }

    public InventoryStoreDrugIndentDetails getInventoryStoreDrugIndentDetails(InventoryStoreDrugIndentDetail inventoryStoreDrugIndentDetail) {
        InventoryStoreDrugIndentDetails isdid = new InventoryStoreDrugIndentDetails();
        isdid.setCategory(inventoryStoreDrugIndentDetail.getDrug().getCategory().getName());
        isdid.setDrugName(inventoryStoreDrugIndentDetail.getDrug().getName());
        isdid.setFormulation(inventoryStoreDrugIndentDetail.getFormulation().getName());
        isdid.setQuantity(inventoryStoreDrugIndentDetail.getQuantity());
        return isdid;
    }
}
