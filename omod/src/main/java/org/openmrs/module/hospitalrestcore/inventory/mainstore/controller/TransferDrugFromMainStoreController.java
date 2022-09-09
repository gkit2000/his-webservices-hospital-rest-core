package org.openmrs.module.hospitalrestcore.inventory.mainstore.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.openmrs.module.hospitalrestcore.inventory.ActionValue.INDENT_MAINSTORE;

/**
 * @author Mujuzi Moses
 *
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/transferDrugFromGeneralStore")
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
        if (storeName != null)
            temp = "?storeName=" + storeName;
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
    public void getIndentDetails(@RequestParam(value = "indentUuid") String indentUuid,
                                 HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreDrugIndent drugIndent = hospitalRestCoreService.getInventoryStoreDrugByUuidString(indentUuid);

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

    @RequestMapping(value = "/transfer-indent", method = RequestMethod.PUT)
    public ResponseEntity<Void> transferIndentRequest(HttpServletRequest request, HttpServletResponse response,
            @Valid @RequestBody InventoryStoreDrugTransactionPayload inventoryStoreDrugTransactionPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreDrugIndent indent = hospitalRestCoreService.getInventoryStoreDrugByUuidString(
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

        List<String> transactionDetailUuids = inventoryStoreDrugTransactionPayload.getTransactionDetailUuids();

        for (String detailUuid : transactionDetailUuids)

            for (int i = 0; i < transactionDetails.size(); i++) {
                InventoryStoreDrugTransactionDetail d = transactionDetails.get(i);

                if (Objects.equals(d.getUuid(), detailUuid)) {
                    Map<String, Integer> transfers = inventoryStoreDrugTransactionPayload.getTransfers();
                    Integer transfer = transfers.get(detailUuid);

                    if (d.getCurrentQuantity() >= transfer) {
                        Integer oldCurrent = d.getCurrentQuantity();
                        Integer newCurrent = oldCurrent - transfer;
                        d.setCurrentQuantity(newCurrent);
                        d.setQuantity(transfer);
                        d.setLastModifiedDate(new Date());
                        d.setLastModifiedBy(Context.getAuthenticatedUser());
                        indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[2]); // DONE
                        indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[4]); // DONE
                        indent.setLastModifiedDate(new Date());
                        indent.setLastModifiedBy(Context.getAuthenticatedUser());
                        inventoryStoreDrugTransaction.setStatus(1); //DONE
                        inventoryStoreDrugTransaction.setLastModifiedDate(new Date());
                        inventoryStoreDrugTransaction.setLastModifiedBy(Context.getAuthenticatedUser());
                        hospitalRestCoreService.saveOrUpdateDrugTransactionDetail(d);
                        hospitalRestCoreService.saveOrUpdateStoreDrugTransaction(inventoryStoreDrugTransaction);
                        hospitalRestCoreService.saveOrUpdateInventoryDrugIndent(indent);

                    } else
                        throw new ResourceNotFoundException(
                                String.format(OpenmrsCustomConstants.DRUG_ERROR_NOT_ENOUGH_DRUG_QUANTITY,
                                        d.getDrug().getName()));
                }
            }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/refuse-indent", method = RequestMethod.PUT)
    public ResponseEntity<Void> refuseIndentRequest(HttpServletRequest request, HttpServletResponse response,
            @Valid @RequestBody InventoryStoreDrugTransactionPayload inventoryStoreDrugTransactionPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreDrugIndent indent = hospitalRestCoreService.getInventoryStoreDrugByUuidString(
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

        indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[1]);
        indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[5]);
        indent.setLastModifiedDate(new Date());
        indent.setLastModifiedBy(Context.getAuthenticatedUser());
        inventoryStoreDrugTransaction.setStatus(1); //DONE
        inventoryStoreDrugTransaction.setLastModifiedDate(new Date());
        inventoryStoreDrugTransaction.setLastModifiedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateInventoryDrugIndent(indent);
        hospitalRestCoreService.saveOrUpdateStoreDrugTransaction(inventoryStoreDrugTransaction);

        return new ResponseEntity<Void>(HttpStatus.OK);
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
}
