package org.openmrs.module.hospitalrestcore.inventory.mainstore.controller;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.context.Context;
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

    @RequestMapping(value = "/add-receipt", method = RequestMethod.POST)
    public ResponseEntity<Void> addReceipt(HttpServletRequest request, HttpServletResponse response,
                                           @Valid @RequestBody InventoryReceiptFormPayload inventoryReceiptFormPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryReceiptForm inventoryReceiptForm = new InventoryReceiptForm();
        inventoryReceiptForm.setDrug(inventoryReceiptFormPayload.getDrug());
        inventoryReceiptForm.setFormulation(inventoryReceiptFormPayload.getFormulation());
        inventoryReceiptForm.setRate(inventoryReceiptFormPayload.getRate());
        inventoryReceiptForm.setQuantity(inventoryReceiptFormPayload.getQuantity());
        inventoryReceiptForm.setVat(inventoryReceiptFormPayload.getVat());
        inventoryReceiptForm.setSgst(inventoryReceiptFormPayload.getSgst());
        inventoryReceiptForm.setCgst(inventoryReceiptFormPayload.getCgst());
        inventoryReceiptForm.setMrPrice(inventoryReceiptFormPayload.getMrPrice());
        inventoryReceiptForm.setBatchNo(inventoryReceiptFormPayload.getBatchNo());
        inventoryReceiptForm.setCompanyName(inventoryReceiptFormPayload.getCompanyName());
        inventoryReceiptForm.setDateManufacture(inventoryReceiptFormPayload.getDateManufacture());
        inventoryReceiptForm.setDateExpiry(inventoryReceiptFormPayload.getDateExpiry());
        inventoryReceiptForm.setReceiptDate(inventoryReceiptFormPayload.getReceiptDate());
        inventoryReceiptForm.setWaiverPercentage(inventoryReceiptFormPayload.getWaiverPercentage());
        inventoryReceiptFormPayload.setTotalAmount();
        inventoryReceiptForm.setTotalAmount(inventoryReceiptFormPayload.getTotalAmount());
        inventoryReceiptFormPayload.setReceiptNumber();
        inventoryReceiptForm.setReceiptNumber(inventoryReceiptFormPayload.getReceiptNumber());
        inventoryReceiptFormPayload.setBillAmount();
        inventoryReceiptForm.setBillAmount(inventoryReceiptFormPayload.getBillAmount());
        inventoryReceiptForm.setCreatedDate(new Date());
        inventoryReceiptForm.setCreatedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateInventoryReceiptForm(inventoryReceiptForm);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/receipts", method = RequestMethod.GET)
    public void getReceiptList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "currentPage", required = false) Integer currentPage,
                               @RequestParam(value = "companyName", required = false) String companyName,
                               @RequestParam(value = "fromDate", required = false) String fromDate,
                               @RequestParam(value = "toDate", required = false) String toDate, Map<String, Object> model,
                               HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        int total = hospitalRestCoreService.countReceiptsToGeneralStore(companyName, fromDate, toDate);
        String temp = "";
        if (companyName != null) {
            temp = "?companyName=" +companyName;
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
        List<InventoryReceiptForm> inventoryReceiptForms = hospitalRestCoreService.listReceiptsToGeneralStore(
                companyName, fromDate, toDate, pagingUtil.getStartPos(), pagingUtil.getPageSize());

        List<MainStoreReceiptFormDetails> receipts = inventoryReceiptForms.stream()
                .map(irf -> getMainStoreReceiptFormDetails(irf)).collect(Collectors.toList());

        if (receipts != null)
            Collections.sort(receipts);
        model.put("companyName", companyName);
        model.put("pagingUtil", pagingUtil);
        model.put("receipts", receipts);
        new ObjectMapper().writeValue(out, receipts);

    }

    @RequestMapping(value = "/vendor-receipt", method = RequestMethod.GET)
    public void getReceiptsByVendor(@RequestParam(value = "companyName", required = false) String companyName,
                                    @RequestParam(value = "receiptNumber", required = false) String receiptNumber,
                                    HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryReceiptForm> inventoryReceiptForms = hospitalRestCoreService.listAllInventoryReceiptForm();
        List<InventoryReceiptForm> receiptForms = new ArrayList<>();

        if (companyName != null)
            for (InventoryReceiptForm form : inventoryReceiptForms)
                if (Objects.equals(form.getCompanyName(), companyName))
                    if (Objects.equals(form.getReceiptNumber(), receiptNumber))
                        receiptForms.add(form);

        List<InventoryReceiptFormDetails> irfds = receiptForms.stream()
                .map(irf -> getInventoryReceiptFormDetails(irf)).collect(Collectors.toList());

        new ObjectMapper().writeValue(out, irfds);

    }

    public InventoryReceiptFormDetails getInventoryReceiptFormDetails(InventoryReceiptForm inventoryReceiptForm) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        InventoryReceiptFormDetails irfd = new InventoryReceiptFormDetails();
        irfd.setId(inventoryReceiptForm.getId());
        irfd.setDrugName(inventoryReceiptForm.getDrug().getName());
        irfd.setDrugUuid(inventoryReceiptForm.getDrug().getUuid());
        irfd.setFormulationName(inventoryReceiptForm.getFormulation().getName());
        irfd.setFormulationUuid(inventoryReceiptForm.getFormulation().getUuid());
        irfd.setRate(inventoryReceiptForm.getRate());
        irfd.setQuantity(inventoryReceiptForm.getQuantity());
        irfd.setVat(inventoryReceiptForm.getVat());
        irfd.setSgst(inventoryReceiptForm.getSgst());
        irfd.setCgst(inventoryReceiptForm.getCgst());
        irfd.setMrPrice(inventoryReceiptForm.getMrPrice());
        irfd.setTotalAmount(inventoryReceiptForm.getTotalAmount());
        irfd.setBillAmount(inventoryReceiptForm.getBillAmount());
        irfd.setBatchNo(inventoryReceiptForm.getBatchNo());
        irfd.setCompanyName(inventoryReceiptForm.getCompanyName());
        irfd.setDateManufacture(formatter.format(inventoryReceiptForm.getDateManufacture()));
        irfd.setDateExpiry(formatter.format(inventoryReceiptForm.getDateExpiry()));
        irfd.setReceiptDate(formatter.format(inventoryReceiptForm.getReceiptDate()));
        irfd.setReceiptNumber(inventoryReceiptForm.getReceiptNumber());
        irfd.setWaiverPercentage(inventoryReceiptForm.getWaiverPercentage());
        irfd.setReceiptUuid(inventoryReceiptForm.getUuid());
        irfd.setDeleted(inventoryReceiptForm.getDeleted());
        irfd.setRetired(inventoryReceiptForm.getRetired());
        return irfd;
    }

    public MainStoreReceiptFormDetails getMainStoreReceiptFormDetails(InventoryReceiptForm inventoryReceiptForm) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        MainStoreReceiptFormDetails mrfd = new MainStoreReceiptFormDetails();
        mrfd.setBillAmount(inventoryReceiptForm.getBillAmount());
        mrfd.setCompanyName(inventoryReceiptForm.getCompanyName());
        mrfd.setReceiptDate(formatter.format(inventoryReceiptForm.getReceiptDate()));
        mrfd.setReceiptNumber(inventoryReceiptForm.getReceiptNumber());
        return mrfd;
    }
}