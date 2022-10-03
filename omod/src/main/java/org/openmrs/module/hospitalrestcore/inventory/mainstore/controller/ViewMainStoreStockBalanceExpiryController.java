package org.openmrs.module.hospitalrestcore.inventory.mainstore.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStore;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransactionDetail;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransactions;
import org.openmrs.module.hospitalrestcore.inventory.MainStoreDrugTransactions;
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
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/viewStockBalanceExpiry")
public class ViewMainStoreStockBalanceExpiryController extends BaseRestController {

    @RequestMapping(value = "/drug", method = RequestMethod.GET)
    public void getDrugList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                            @RequestParam(value = "currentPage", required = false) Integer currentPage,
                            @RequestParam(value = "category", required = false) String category,
                            @RequestParam(value = "drugName", required = false) String drugName,
                            @RequestParam(value = "fromDate", required = false) String fromDate,
                            @RequestParam(value = "toDate", required = false) String toDate, Map<String, Object> model,
                            HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
//        InventoryStore store = hospitalRestCoreService
//                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
        InventoryStore store = new InventoryStore();
        List<InventoryStore> storeList = hospitalRestCoreService.listAllInventoryStore();

        for (InventoryStore s : storeList)
            if (Objects.equals(s.getName(), "Main Store"))
                store = s;

        int total = hospitalRestCoreService.countViewStockBalanceExpiry(store.getId(), category, drugName, fromDate, toDate);
        String temp = "";
        if (category != null)
            temp = "?category=" + category;
        if (drugName != null) {
            if (StringUtils.isBlank(temp)) {
                temp = "?drugName=" + drugName;
            } else {
                temp += "&drugName=" + drugName;
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
        List<InventoryStoreDrugTransactionDetail> inventoryStoreDrugTransactionDetails = hospitalRestCoreService
                .listStoreDrugTransactionDetail(store.getId(), category, drugName, fromDate, toDate, pagingUtil.getStartPos(),
                        pagingUtil.getPageSize());
        List<InventoryStoreDrugTransactions> drugs = inventoryStoreDrugTransactionDetails.stream()
                .map(isdi -> getInventoryStoreDrugTransactions(isdi)).collect(Collectors.toList());

        if (drugs != null)
            Collections.sort(drugs);
        model.put("drugName", drugName);
        model.put("category", category);
        model.put("pagingUtil", pagingUtil);
        model.put("drugs", drugs);
        new ObjectMapper().writeValue(out, drugs);

    }

    @RequestMapping(value = "/drug-details", method = RequestMethod.GET)
    public void getDrugDetailList(
            @RequestParam(value = "drugName") String drugName,
            @RequestParam(value = "drugFormulation") String drugFormulation,
            HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
//                InventoryStore store = hospitalRestCoreService
//                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
        InventoryStore store = new InventoryStore();
        List<InventoryStore> storeList = hospitalRestCoreService.listAllInventoryStore();

        for (InventoryStore s : storeList)
            if (Objects.equals(s.getName(), "Main Store"))
                store = s;
        List<InventoryStoreDrugTransactionDetail>  transactionDetails =
                hospitalRestCoreService.listAllStoreDrugTransactionDetail(store);
        List<InventoryStoreDrugTransactionDetail> details = new ArrayList<>();

        for (InventoryStoreDrugTransactionDetail d : transactionDetails)
            if (Objects.equals(d.getDrug().getName(), drugName))
                if (Objects.equals(d.getFormulation().getName(), drugFormulation))
                    if (Objects.equals(d.getExpireStatus(), 1))
                        details.add(d);

        List<MainStoreDrugTransactions> mdts = details.stream()
                .map(tds -> getMainStoreDrugTransactions(tds)).collect(Collectors.toList());

        new ObjectMapper().writeValue(out, mdts);

    }

    @RequestMapping(value = "/delete-drug-details", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteDrugDetails(@RequestBody List<String> drugDetailUuids, HttpServletRequest request,
                                                  HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        for (String uuid : drugDetailUuids) {
            InventoryStoreDrugTransactionDetail transactionDetail = hospitalRestCoreService.getDrugTransactionDetailByUuidString(uuid);
            if (transactionDetail == null)
                throw new ResourceNotFoundException(String.format(
                        OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_TRANSACTION_DETAIL, uuid));

            transactionDetail.setDeleted(true);
            transactionDetail.setDeletedDate(new Date());
            transactionDetail.setRetired(true);
            transactionDetail.setRetiredDate(new Date());
            transactionDetail.setRetiredBy(Context.getAuthenticatedUser());
            transactionDetail.setDeletedBy(Context.getAuthenticatedUser());

            hospitalRestCoreService.saveOrUpdateDrugTransactionDetail(transactionDetail);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public MainStoreDrugTransactions getMainStoreDrugTransactions(
            InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        MainStoreDrugTransactions mdts = new MainStoreDrugTransactions();
        mdts.setDrugName(inventoryStoreDrugTransactionDetail.getDrug().getName());
        mdts.setDrugFormulation(inventoryStoreDrugTransactionDetail.getFormulation().getName());
        mdts.setTransactionType(inventoryStoreDrugTransactionDetail.getTransaction().getTypeTransactionName());
        mdts.setTransactionStore(inventoryStoreDrugTransactionDetail.getTransaction().getStore().getName());
        mdts.setOpeningBalance(inventoryStoreDrugTransactionDetail.getOpeningBalance());
        mdts.setReceiptQuantity(inventoryStoreDrugTransactionDetail.getQuantity());
        mdts.setCurrentQuantity(inventoryStoreDrugTransactionDetail.getCurrentQuantity());
        mdts.setIssueQuantity(inventoryStoreDrugTransactionDetail.getIssueQuantity());
        mdts.setClosingBalance(inventoryStoreDrugTransactionDetail.getClosingBalance());
        mdts.setRate(inventoryStoreDrugTransactionDetail.getUnitPrice());
        mdts.setUnitPrice(inventoryStoreDrugTransactionDetail.getUnitPrice());
        mdts.setMrPrice(inventoryStoreDrugTransactionDetail.getTotalPrice());
        mdts.setVAT(inventoryStoreDrugTransactionDetail.getVAT());
        mdts.setBatchNo(inventoryStoreDrugTransactionDetail.getBatchNo());
        mdts.setDateExpiry(formatter.format(inventoryStoreDrugTransactionDetail.getDateExpiry()));
        mdts.setReceiptDate(formatter.format(inventoryStoreDrugTransactionDetail.getReceiptDate()));
        mdts.setUuid(inventoryStoreDrugTransactionDetail.getUuid());
        return mdts;
    }

    public InventoryStoreDrugTransactions getInventoryStoreDrugTransactions(
            InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail) {
        InventoryStoreDrugTransactions isdts = new InventoryStoreDrugTransactions();
        isdts.setDrugName(inventoryStoreDrugTransactionDetail.getDrug().getName());
        isdts.setDrugCategory(inventoryStoreDrugTransactionDetail.getDrug().getCategory().getName());
        isdts.setDrugFormulation(inventoryStoreDrugTransactionDetail.getFormulation().getName());
        isdts.setCurrentQuantity(inventoryStoreDrugTransactionDetail.getCurrentQuantity());
        return isdts;
    }

}
