package org.openmrs.module.hospitalrestcore.inventory.mainstore.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
 *
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/receiptsToStoreList/")
public class ReceiptsToMainStoreListController extends BaseRestController {

    @RequestMapping(value = "/add-to-slip", method = RequestMethod.POST)
    public ResponseEntity<Void> addReceipt(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
            @Valid @RequestBody InventoryReceiptFormPayload inventoryReceiptFormPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryReceiptFormDetail inventoryReceiptFormDetail = new InventoryReceiptFormDetail();
        inventoryReceiptFormDetail.setDrug(inventoryReceiptFormPayload.getDrug());
        inventoryReceiptFormDetail.setFormulation(inventoryReceiptFormPayload.getFormulation());
        inventoryReceiptFormDetail.setRate(inventoryReceiptFormPayload.getRate());
        inventoryReceiptFormDetail.setQuantity(inventoryReceiptFormPayload.getQuantity());
        inventoryReceiptFormDetail.setVat(inventoryReceiptFormPayload.getVat());
        inventoryReceiptFormDetail.setSgst(inventoryReceiptFormPayload.getSgst());
        inventoryReceiptFormDetail.setCgst(inventoryReceiptFormPayload.getCgst());
        inventoryReceiptFormDetail.setMrPrice(inventoryReceiptFormPayload.getMrPrice());
        inventoryReceiptFormDetail.setBatchNo(inventoryReceiptFormPayload.getBatchNo());
        inventoryReceiptFormDetail.setCompanyName(inventoryReceiptFormPayload.getCompanyName());
        inventoryReceiptFormDetail.setDateManufacture(inventoryReceiptFormPayload.getDateManufacture());
        inventoryReceiptFormDetail.setDateExpiry(inventoryReceiptFormPayload.getDateExpiry());
        inventoryReceiptFormDetail.setReceiptDate(inventoryReceiptFormPayload.getReceiptDate());
        inventoryReceiptFormDetail.setWaiverPercentage(inventoryReceiptFormPayload.getWaiverPercentage());
        inventoryReceiptFormPayload.setTotalAmount();
        inventoryReceiptFormDetail.setTotalAmount(inventoryReceiptFormPayload.getTotalAmount());
        inventoryReceiptFormPayload.setAmountAfterGST();
        inventoryReceiptFormDetail.setAmountAfterGST(inventoryReceiptFormPayload.getAmountAfterGST());
        inventoryReceiptFormPayload.setUnitPrice();
        inventoryReceiptFormDetail.setUnitPrice(inventoryReceiptFormPayload.getUnitPrice());
        inventoryReceiptFormDetail.setDeleted(false);
        hospitalRestCoreService.saveOrUpdateInventoryReceiptFormDetail(inventoryReceiptFormDetail);

        List<InventoryReceiptFormDetail> receiptFormDetails = hospitalRestCoreService.listAllInventoryReceiptFormDetail();

        List<InventoryReceiptFormDetails> receipt = receiptFormDetails.stream()
                .map(irf -> getInventoryReceiptFormDetails(irf)).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(receipt))
            Collections.sort(receipt);
        model.put("receipt", receipt);
        new ObjectMapper().writeValue(out, receipt);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/receipt-slip", method = RequestMethod.GET)
    public void getReceiptSlip(Map<String, Object> model,
                             HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        List<InventoryReceiptFormDetail> receiptFormDetails = hospitalRestCoreService.listAllInventoryReceiptFormDetail();

        List<InventoryReceiptFormDetails> receipt = receiptFormDetails.stream()
                .map(irf -> getInventoryReceiptFormDetails(irf)).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(receipt))
            Collections.sort(receipt);
        model.put("receipt", receipt);
        new ObjectMapper().writeValue(out, receipt);

    }

    @RequestMapping(value = "/clear-slip", method = RequestMethod.DELETE)
    public ResponseEntity<Void> clearSlip(HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryReceiptFormDetail> receiptFormDetails = hospitalRestCoreService.listAllInventoryReceiptFormDetail();

        if (!CollectionUtils.isEmpty(receiptFormDetails))
            for (InventoryReceiptFormDetail d : receiptFormDetails) {
                d.setDeleted(true);
                hospitalRestCoreService.saveOrUpdateInventoryReceiptFormDetail(d);
            }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/finish-receipt", method = RequestMethod.POST)
    public ResponseEntity<Void> finishReceipt(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
            @Valid @RequestBody InventoryReceiptFormSlipPayload inventoryReceiptFormSlipPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStore store = hospitalRestCoreService
                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
//        InventoryStore store = new InventoryStore();
//        List<InventoryStore> storeList = hospitalRestCoreService.listAllInventoryStore();
//
//        for (InventoryStore s : storeList)
//            if (Objects.equals(s.getName(), "Main Store"))
//                store = s;

        List<InventoryReceiptFormDetail> receiptFormDetails = hospitalRestCoreService.listAllInventoryReceiptFormDetail();

        InventoryStoreDrugTransaction transaction = new InventoryStoreDrugTransaction();
        transaction.setStore(store);
        transaction.setStatus(1);
        transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
        transaction.setCreatedDate(new Date());
        transaction.setCreatedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateStoreDrugTransaction(transaction);

        InventoryStoreDrugReceipt receipt = new InventoryStoreDrugReceipt();
        receipt.setTransaction(transaction);
        receipt.setReceiptNumber(inventoryReceiptFormSlipPayload.getReceiptNumber());
        receipt.setBillAmount(inventoryReceiptFormSlipPayload.getReceiptAmount());
        receipt.setVendorName(inventoryReceiptFormSlipPayload.getVendorName());
        receipt.setCreatedDate(new Date());
        receipt.setCreatedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateDrugReceipt(receipt);

        List<InventoryStoreDrugTransactionDetail> detailList = new ArrayList<InventoryStoreDrugTransactionDetail>();
        if (receiptFormDetails.size() > 0) {
            for (InventoryReceiptFormDetail d : receiptFormDetails) {

                List<InventoryDrug> drugs = hospitalRestCoreService.listAllInventoryDrug();
                InventoryDrug inventoryDrug = new InventoryDrug();
                for (InventoryDrug drug : drugs)
                    if (Objects.equals(drug.getName(), d.getDrug().getName()))
                        inventoryDrug = drug;

                if (inventoryDrug.getName() == null)
                    throw new ResourceNotFoundException(
                            String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG, d.getDrug().getName()));

                List<InventoryDrugFormulation> formulations = hospitalRestCoreService.listAllInventoryDrugFormulation();
                InventoryDrugFormulation formulation = new InventoryDrugFormulation();
                for (InventoryDrugFormulation f : formulations)
                    if (Objects.equals(f.getName(), d.getFormulation().getName()))
                        formulation = f;

                if (formulation.getName() == null)
                    throw new ResourceNotFoundException(
                            String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_FORMULATION, d.getFormulation().getName()));

                List<InventoryStoreDrugTransactionDetail> inventoryStoreDrugTransactionDetails =
                        hospitalRestCoreService.listAllStoreDrugTransactionDetail(store);
                InventoryStoreDrugTransactionDetail transactionD = new InventoryStoreDrugTransactionDetail();

                for (InventoryStoreDrugTransactionDetail detail : inventoryStoreDrugTransactionDetails)
                    if (Objects.equals(detail.getDrug().getName(), inventoryDrug.getName()))
                        if (Objects.equals(detail.getFormulation().getName(), formulation.getName()))
                            transactionD = detail;

                InventoryStoreDrugTransactionDetail transactionDetail = new InventoryStoreDrugTransactionDetail();
                InventoryStoreDrugReceiptDetail receiptDetail = new InventoryStoreDrugReceiptDetail();

                if (transactionD.getDrug() == null) {
                    transactionDetail.setTransaction(transaction);
                    transactionDetail.setDrug(d.getDrug());
                    transactionDetail.setFormulation(d.getFormulation());
                    transactionDetail.setQuantity(0);
                    transactionDetail.setCurrentQuantity(d.getQuantity());
                    transactionDetail.setIssueQuantity(d.getQuantity());
                    transactionDetail.setUnitPrice(d.getUnitPrice());
                    transactionDetail.setTotalPrice(d.getTotalAmount());
                    transactionDetail.setVAT(d.getVat());
                    transactionDetail.setBatchNo(d.getBatchNo());
                    transactionDetail.setCompanyName(d.getCompanyName());
                    transactionDetail.setDateManufacture(d.getDateManufacture());
                    transactionDetail.setDateExpiry(d.getDateExpiry());
                    transactionDetail.setOpeningBalance(0);
                    transactionDetail.setClosingBalance(d.getQuantity());
                } else {
                    int closingQuantity = Math.toIntExact(transactionD.getClosingBalance() - d.getQuantity());
                    transactionDetail.setTransaction(transaction);
                    transactionDetail.setDrug(d.getDrug());
                    transactionDetail.setFormulation(d.getFormulation());
                    transactionDetail.setQuantity(0);
                    transactionDetail.setCurrentQuantity(closingQuantity);
                    transactionDetail.setIssueQuantity(d.getQuantity());
                    transactionDetail.setUnitPrice(transactionD.getUnitPrice());
                    transactionDetail.setTotalPrice(transactionD.getTotalPrice());
                    transactionDetail.setVAT(transactionD.getVAT());
                    transactionDetail.setBatchNo(transactionD.getBatchNo());
                    transactionDetail.setCompanyName(transactionD.getCompanyName());
                    transactionDetail.setDateManufacture(transactionD.getDateManufacture());
                    transactionDetail.setDateExpiry(transactionD.getDateExpiry());
                    transactionDetail.setOpeningBalance(transactionD.getClosingBalance());
                    transactionDetail.setClosingBalance(closingQuantity);
                }
                transactionDetail.setReceiptDate(new Date());
                transactionDetail.setCreatedDate(new Date());
                transactionDetail.setCreatedBy(Context.getAuthenticatedUser());
                hospitalRestCoreService.saveOrUpdateDrugTransactionDetail(transactionDetail);
                detailList.add(transactionDetail);

                BigDecimal rate = BigDecimal.valueOf(d.getRate());
                BigDecimal cd = d.getWaiverPercentage() != 0.0f ? BigDecimal.valueOf(d.getWaiverPercentage()) : BigDecimal.ZERO;
                BigDecimal sgstAmount = d.getSgst() != null ? rate.multiply(d.getSgst()) : BigDecimal.ZERO;
                BigDecimal cgstAmount = d.getCgst() != null ? rate.multiply(d.getCgst()) : BigDecimal.ZERO;
                BigDecimal cdAmount = rate.multiply(cd);

                receiptDetail.setReceipt(receipt);
                receiptDetail.setSgst(d.getSgst());
                receiptDetail.setSgstAmount(sgstAmount);
                receiptDetail.setCgst(d.getCgst());
                receiptDetail.setCgstAmount(cgstAmount);
                receiptDetail.setMrPrice(d.getMrPrice());
                receiptDetail.setCd(cd);
                receiptDetail.setCdAmount(cdAmount);
                receiptDetail.setTotalAmount(d.getTotalAmount());
                receiptDetail.setAmountAfterGST(d.getAmountAfterGST());
                hospitalRestCoreService.saveOrUpdateInventoryStoreDrugReceiptDetail(receiptDetail);
                d.setDeleted(true);
                hospitalRestCoreService.saveOrUpdateInventoryReceiptFormDetail(d);

                List<InventoryStoreDrug> drugList = hospitalRestCoreService.listAllInventoryStoreDrug(store);
                InventoryStoreDrug updateDrug = new InventoryStoreDrug();
                if (CollectionUtils.isEmpty(drugList)) {
                    InventoryStoreDrug addDrug = new InventoryStoreDrug();
                    addDrug.setStore(store);
                    addDrug.setDrug(inventoryDrug);
                    addDrug.setFormulation(formulation);
                    addDrug.setCurrentQuantity(d.getQuantity());
                    addDrug.setReceiptQuantity(d.getQuantity());
                    addDrug.setIssueQuantity(0);
                    addDrug.setStatusIndent(1);
                    addDrug.setReorderQuantity(d.getQuantity());
                    addDrug.setOpeningBalance(0);
                    addDrug.setClosingBalance(d.getQuantity());
                    addDrug.setStatus(1);
                    addDrug.setCreatedDate(new Date());
                    hospitalRestCoreService.saveOrUpdateInventoryStoreDrug(addDrug);
                } else {
                    for (InventoryStoreDrug sd : drugList)
                        if (Objects.equals(sd.getDrug().getName(), inventoryDrug.getName()))
                            if (Objects.equals(sd.getFormulation().getName(), formulation.getName()))
                                updateDrug = sd;

                    if (updateDrug.getDrug() == null) {
                        InventoryStoreDrug addDrug = new InventoryStoreDrug();
                        addDrug.setStore(store);
                        addDrug.setDrug(inventoryDrug);
                        addDrug.setFormulation(formulation);
                        addDrug.setCurrentQuantity(d.getQuantity());
                        addDrug.setReceiptQuantity(d.getQuantity());
                        addDrug.setIssueQuantity(0);
                        addDrug.setStatusIndent(1);
                        addDrug.setReorderQuantity(d.getQuantity());
                        addDrug.setOpeningBalance(0);
                        addDrug.setClosingBalance(d.getQuantity());
                        addDrug.setStatus(1);
                        addDrug.setCreatedDate(new Date());
                        hospitalRestCoreService.saveOrUpdateInventoryStoreDrug(addDrug);
                    } else {
                        int currentQuantity = updateDrug.getCurrentQuantity() != null ?
                                updateDrug.getCurrentQuantity() + d.getQuantity() : d.getQuantity();

                        int closingBalance = updateDrug.getClosingBalance() != null ?
                                updateDrug.getClosingBalance() + d.getQuantity() : d.getQuantity();

                        int openingBalance = updateDrug.getClosingBalance() != null ? updateDrug.getClosingBalance() : 0;
                        updateDrug.setCurrentQuantity(currentQuantity);
                        updateDrug.setReceiptQuantity(d.getQuantity());
                        updateDrug.setReorderQuantity(d.getQuantity());
                        updateDrug.setOpeningBalance(openingBalance);
                        updateDrug.setClosingBalance(closingBalance);
                        updateDrug.setLastModifiedDate(new Date());
                        updateDrug.setLastModifiedBy(Context.getAuthenticatedUser());
                        hospitalRestCoreService.saveOrUpdateInventoryStoreDrug(updateDrug);
                    }
                }
            }
        }

        List<InventoryStoreDrugTransactionDetails> details = detailList.stream()
                .map(sdtds -> getTransactionDetails(sdtds)).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(details))
            Collections.sort(details);
        model.put("details", details);
        new ObjectMapper().writeValue(out, details);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/receipts", method = RequestMethod.GET)
    public void getReceiptList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "currentPage", required = false) Integer currentPage,
                               @RequestParam(value = "vendorName", required = false) String vendorName,
                               @RequestParam(value = "fromDate", required = false) String fromDate,
                               @RequestParam(value = "toDate", required = false) String toDate, Map<String, Object> model,
                               HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        int total = hospitalRestCoreService.countStoreDrugReceipt(vendorName, fromDate, toDate);
        String temp = "";
        if (vendorName != null) {
            temp = "?vendorName=" +vendorName;
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
        List<InventoryStoreDrugReceipt> inventoryStoreDrugReceipts = hospitalRestCoreService.listStoreDrugReceipt(
                vendorName, fromDate, toDate, pagingUtil.getStartPos(), pagingUtil.getPageSize());

        List<MainStoreReceiptFormDetails> receipts;
        if (CollectionUtils.isEmpty(inventoryStoreDrugReceipts))
            receipts = new ArrayList<MainStoreReceiptFormDetails>();
        else
            receipts = inventoryStoreDrugReceipts.stream()
                .map(irf -> getMainStoreReceiptFormDetails(irf)).collect(Collectors.toList());

        if (receipts != null)
            Collections.sort(receipts);
        model.put("vendorName", vendorName);
        model.put("pagingUtil", pagingUtil);
        model.put("receipts", receipts);
        new ObjectMapper().writeValue(out, receipts);

    }

    @RequestMapping(value = "/vendor-receipt", method = RequestMethod.GET)
    public void getReceiptsByVendor(@RequestParam(value = "vendorName") String vendorName,
                                    @RequestParam(value = "receiptNumber") String receiptNumber, Map<String, Object> model,
                                    HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        InventoryStore store = hospitalRestCoreService
                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
//        InventoryStore store = new InventoryStore();
//        List<InventoryStore> storeList = hospitalRestCoreService.listAllInventoryStore();
//
//        for (InventoryStore s : storeList)
//            if (Objects.equals(s.getName(), "Main Store"))
//                store = s;

        List<InventoryStoreDrugReceipt> receipts = hospitalRestCoreService.getInventoryStoreDrugReceiptByName(vendorName);
        InventoryStoreDrugReceipt receipt = new InventoryStoreDrugReceipt();

        for (InventoryStoreDrugReceipt r : receipts)
            if (Objects.equals(r.getReceiptNumber(), receiptNumber))
                receipt = r;

        MainStoreReceiptDetails totalDetails = getTotalDetails(receipt);

        List<InventoryStoreDrugTransactionDetail> drugDetails = hospitalRestCoreService.listAllStoreDrugTransactionDetail(store);
        List<InventoryStoreDrugTransactionDetail> transactionDetails = new ArrayList<InventoryStoreDrugTransactionDetail>();
        for (InventoryStoreDrugTransactionDetail td : drugDetails)
            if (Objects.equals(td.getTransaction(), receipt.getTransaction()))
                transactionDetails.add(td);

        List<InventoryStoreDrugReceiptDetail> receiptDetailsList = hospitalRestCoreService.getStoreDrugReceiptDetailByReceiptId(receipt.getId());
        List<InventoryStoreDrugReceiptDetails> receiptDetails = new ArrayList<InventoryStoreDrugReceiptDetails>();

        for (int i = 0; i < transactionDetails.size(); i++) {
            InventoryStoreDrugReceiptDetails details = getReceiptDetails(receiptDetailsList.get(i), transactionDetails.get(i));
            receiptDetails.add(details);
        }

        if (receiptDetails != null)
            Collections.sort(receiptDetails);

        Map<String, Object> receiptMap = new HashMap<String, Object>();
        receiptMap.put("totalDetails", totalDetails);
        receiptMap.put("receiptDetails", receiptDetails);

        model.put("receipt", receiptMap);
        new ObjectMapper().writeValue(out, receiptMap);

    }

    public MainStoreReceiptDetails getTotalDetails(InventoryStoreDrugReceipt inventoryStoreDrugReceipt) {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryStoreDrugReceiptDetail> details = hospitalRestCoreService.getStoreDrugReceiptDetailByReceiptId(inventoryStoreDrugReceipt.getId());

        BigDecimal totalCD = BigDecimal.ZERO;
        BigDecimal totalCGST = BigDecimal.ZERO;
        BigDecimal totalSGST = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountAfterGST = BigDecimal.ZERO;

        for (InventoryStoreDrugReceiptDetail d : details) {
            totalCD = totalCD.add(d.getCdAmount());
            totalCGST = totalCGST.add(d.getCgstAmount());
            totalSGST = totalSGST.add(d.getSgstAmount());
            totalAmount = totalAmount.add(d.getTotalAmount());
            totalAmountAfterGST = totalAmountAfterGST.add(d.getAmountAfterGST());
        }

        MainStoreReceiptDetails msrds = new MainStoreReceiptDetails();
        msrds.setTotalCD(totalCD);
        msrds.setTotalCGST(totalCGST);
        msrds.setTotalSGST(totalSGST);
        msrds.setTotalAmount(totalAmount);
        msrds.setTotalAmountAfterGST(totalAmountAfterGST);
        return msrds;
    }

    public InventoryStoreDrugReceiptDetails getReceiptDetails(InventoryStoreDrugReceiptDetail inventoryStoreDrugReceiptDetail,
            InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail) {

        SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

        InventoryStoreDrugReceiptDetails isdrds = new InventoryStoreDrugReceiptDetails();
        isdrds.setDrugName(inventoryStoreDrugTransactionDetail.getDrug().getName());
        isdrds.setFormulationName(inventoryStoreDrugTransactionDetail.getFormulation().getName());
        isdrds.setReceiptQuantity(inventoryStoreDrugTransactionDetail.getIssueQuantity());
        isdrds.setRate(inventoryStoreDrugTransactionDetail.getUnitPrice().intValueExact());
        isdrds.setUnitPrice(inventoryStoreDrugTransactionDetail.getUnitPrice());
        isdrds.setVat(inventoryStoreDrugTransactionDetail.getVAT());
        isdrds.setMrPrice(inventoryStoreDrugReceiptDetail.getMrPrice());
        isdrds.setBatchNo(inventoryStoreDrugTransactionDetail.getBatchNo());
        isdrds.setCd(inventoryStoreDrugReceiptDetail.getCd().intValueExact());
        isdrds.setCdAmount(inventoryStoreDrugReceiptDetail.getCdAmount().intValueExact());
        isdrds.setCgst(inventoryStoreDrugReceiptDetail.getCgst());
        isdrds.setCgstAmount(inventoryStoreDrugReceiptDetail.getCgstAmount().intValueExact());
        isdrds.setSgst(inventoryStoreDrugReceiptDetail.getSgst());
        isdrds.setSgstAmount(inventoryStoreDrugReceiptDetail.getSgstAmount().intValueExact());
        isdrds.setDateExpiry(formatterExt.format(inventoryStoreDrugTransactionDetail.getDateExpiry()));
        isdrds.setTotalAmount(inventoryStoreDrugTransactionDetail.getTotalPrice());
        isdrds.setAmountAfterGST(inventoryStoreDrugReceiptDetail.getAmountAfterGST());
        return isdrds;
    }

    public InventoryStoreDrugTransactionDetails getTransactionDetails(InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail) {
        InventoryStoreDrugTransactionDetails isdtds = new InventoryStoreDrugTransactionDetails();

        isdtds.setDrugName(inventoryStoreDrugTransactionDetail.getDrug().getName());
        isdtds.setDrugFormulation(inventoryStoreDrugTransactionDetail.getFormulation().getName());
        isdtds.setQuantity(inventoryStoreDrugTransactionDetail.getQuantity());
        isdtds.setCurrentQuantity(inventoryStoreDrugTransactionDetail.getCurrentQuantity());
        isdtds.setBatchNo(inventoryStoreDrugTransactionDetail.getBatchNo());
        isdtds.setCompanyName(inventoryStoreDrugTransactionDetail.getCompanyName());
        isdtds.setUuid(inventoryStoreDrugTransactionDetail.getUuid());
        return isdtds;

    }

    public InventoryReceiptFormDetails getInventoryReceiptFormDetails(InventoryReceiptFormDetail inventoryReceiptFormDetail) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        InventoryReceiptFormDetails irfd = new InventoryReceiptFormDetails();
        irfd.setDrugName(inventoryReceiptFormDetail.getDrug().getName());
        irfd.setFormulationName(inventoryReceiptFormDetail.getFormulation().getName());
        irfd.setRate(inventoryReceiptFormDetail.getRate());
        irfd.setQuantity(inventoryReceiptFormDetail.getQuantity());
        irfd.setVat(inventoryReceiptFormDetail.getVat());
        irfd.setSgst(inventoryReceiptFormDetail.getSgst());
        irfd.setCgst(inventoryReceiptFormDetail.getCgst());
        irfd.setMrPrice(inventoryReceiptFormDetail.getMrPrice());
        irfd.setBatchNo(inventoryReceiptFormDetail.getBatchNo());
        irfd.setCompanyName(inventoryReceiptFormDetail.getCompanyName());
        irfd.setDateManufacture(formatter.format(inventoryReceiptFormDetail.getDateManufacture()));
        irfd.setDateExpiry(formatter.format(inventoryReceiptFormDetail.getDateExpiry()));
        irfd.setReceiptDate(formatter.format(inventoryReceiptFormDetail.getReceiptDate()));
        irfd.setWaiverPercentage(inventoryReceiptFormDetail.getWaiverPercentage());
        irfd.setTotalAmount(inventoryReceiptFormDetail.getTotalAmount());
        irfd.setAmountAfterGST(inventoryReceiptFormDetail.getAmountAfterGST());
        irfd.setUnitPrice(inventoryReceiptFormDetail.getUnitPrice());
        return irfd;
    }

    public MainStoreReceiptFormDetails getMainStoreReceiptFormDetails(InventoryStoreDrugReceipt inventoryStoreDrugReceipt) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        MainStoreReceiptFormDetails mrfd = new MainStoreReceiptFormDetails();
        mrfd.setBillAmount(BigDecimal.valueOf(inventoryStoreDrugReceipt.getBillAmount()));
        mrfd.setCompanyName(inventoryStoreDrugReceipt.getVendorName());
        mrfd.setReceiptDate(formatter.format(inventoryStoreDrugReceipt.getCreatedDate()));
        mrfd.setReceiptNumber(inventoryStoreDrugReceipt.getReceiptNumber());
        return mrfd;
    }
}
