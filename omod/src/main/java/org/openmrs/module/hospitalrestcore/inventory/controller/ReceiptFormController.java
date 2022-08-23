package org.openmrs.module.hospitalrestcore.inventory.controller;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptForm;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptFormDetails;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptFormPayload;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mujuzi Moses
 *
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/receiptsToStore/")
public class ReceiptFormController {

    @RequestMapping(value = "/all-receipt-details", method = RequestMethod.GET)
    public void getAllReceipts(HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryReceiptForm> inventoryReceiptForms = hospitalRestCoreService.listAllInventoryReceiptForm();

        List<InventoryReceiptFormDetails> isds = inventoryReceiptForms.stream()
                .map(irf -> getInventoryReceiptFormDetails(irf)).collect(Collectors.toList());

        new ObjectMapper().writeValue(out, isds);
    }

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
        inventoryReceiptForm.setCreatedDate(new Date());
        inventoryReceiptForm.setCreatedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateInventoryReceiptForm(inventoryReceiptForm);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/edit-receipt", method = RequestMethod.PUT)
    public ResponseEntity<Void> editReceipt(HttpServletRequest request, HttpServletResponse response,
            @Valid @RequestBody InventoryReceiptFormPayload inventoryReceiptFormPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        InventoryReceiptForm inventoryReceiptForm = hospitalRestCoreService
                .getInventoryReceiptFormByUuidString(inventoryReceiptFormPayload.getUuid());

        if (inventoryReceiptForm == null) {
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_RECEIPT_FORM_UUID,
                            inventoryReceiptFormPayload.getUuid()));
        }
        if (inventoryReceiptForm.getRetired()) {
            inventoryReceiptForm.setRetired(true);
            inventoryReceiptForm.setRetiredDate(new Date());
            inventoryReceiptForm.setRetiredBy(Context.getAuthenticatedUser());
        } else {
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
            inventoryReceiptForm.setLastModifiedDate(new Date());
            inventoryReceiptForm.setLastModifiedBy(Context.getAuthenticatedUser());
        }

        hospitalRestCoreService.saveOrUpdateInventoryReceiptForm(inventoryReceiptForm);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-receipts", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReceipts(@RequestBody List<String> receiptFormUuids, HttpServletRequest request,
            HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        for (String receiptFormUuid : receiptFormUuids) {
            InventoryReceiptForm inventoryReceiptForm = hospitalRestCoreService
                    .getInventoryReceiptFormByUuidString(receiptFormUuid);
            if (inventoryReceiptForm == null) {
                throw new ResourceNotFoundException(String.format(
                        OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_RECEIPT_FORM_UUID, receiptFormUuid));
            }

            inventoryReceiptForm.setDeleted(true);
            inventoryReceiptForm.setDeletedDate(new Date());
            inventoryReceiptForm.setDeletedBy(Context.getAuthenticatedUser());

            hospitalRestCoreService.saveOrUpdateInventoryReceiptForm(inventoryReceiptForm);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
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
        irfd.setBatchNo(inventoryReceiptForm.getBatchNo());
        irfd.setCompanyName(inventoryReceiptForm.getCompanyName());
        irfd.setDateManufacture(formatter.format(inventoryReceiptForm.getDateManufacture()));
        irfd.setDateExpiry(formatter.format(inventoryReceiptForm.getDateExpiry()));
        irfd.setReceiptDate(formatter.format(inventoryReceiptForm.getReceiptDate()));
        irfd.setWaiverPercentage(inventoryReceiptForm.getWaiverPercentage());
        irfd.setReceiptUuid(inventoryReceiptForm.getUuid());
        irfd.setDeleted(inventoryReceiptForm.getDeleted());
        irfd.setRetired(inventoryReceiptForm.getRetired());
        return irfd;
    }
}
