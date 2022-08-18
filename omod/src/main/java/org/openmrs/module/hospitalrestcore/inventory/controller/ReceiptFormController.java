package org.openmrs.module.hospitalrestcore.inventory.controller;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptForm;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptFormPayload;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @author Mujuzi Moses
 *
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/receiptsToStore/")
public class ReceiptFormController {

    @RequestMapping(value = "/add-receipt", method = RequestMethod.POST)
    public ResponseEntity<Void> addReceipt(HttpServletRequest request, HttpServletResponse response,
                                           @Valid @RequestBody InventoryReceiptFormPayload inventoryReceiptFormPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryReceiptForm inventoryReceiptForm = new InventoryReceiptForm();
        inventoryReceiptForm.setCreatedDate(new Date());
        inventoryReceiptForm.setCreatedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateInventoryReceiptForm(inventoryReceiptForm);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
