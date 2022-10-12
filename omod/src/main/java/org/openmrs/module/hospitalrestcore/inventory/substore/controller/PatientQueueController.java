package org.openmrs.module.hospitalrestcore.inventory.substore.controller;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonName;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mujuzi Moses
 *
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/patientQueue")
public class PatientQueueController extends BaseRestController {

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public void getPatients(@RequestParam(value="pageSize", required=false)  Integer pageSize,
            @RequestParam(value="currentPage", required=false)  Integer currentPage,
            @RequestParam(value = "identifierOrName", required = false) String identifierOrName,
            @RequestParam(value = "date") String date,
            Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        int total = hospitalRestCoreService.countDrugOrderPatient(identifierOrName, date);
        String  temp = "?date=" + date;

        if (identifierOrName != null)
            temp += "&identifierOrName=" + identifierOrName;

        PagingUtil pagingUtil = new PagingUtil(RequestUtil.getCurrentLink(request) + temp, pageSize, currentPage,
                total);
        List<InventoryStoreDrugOrderPatient> drugOrderPatients = hospitalRestCoreService.listDrugOrderPatient(identifierOrName,
                date, pagingUtil.getStartPos(), pagingUtil.getPageSize());
        List<PatientDetails> patients;

        if (CollectionUtils.isEmpty(drugOrderPatients))
            patients = new ArrayList<PatientDetails>();
        else
            patients = drugOrderPatients.stream()
                .map(dops -> getPatientDrugOrderDetails(dops)).collect(Collectors.toList());

        if (patients != null)
            Collections.sort(patients);
        model.put("identifierOrName", identifierOrName);
        model.put("date", date);
        model.put("pagingUtil", pagingUtil);
        model.put("patients", patients);
        new ObjectMapper().writeValue(out, patients);
    }

    @RequestMapping(value = "/patient-drug-orders", method = RequestMethod.GET)
    public void getPatientDrugOrders(@RequestParam(value = "identifier") String identifier,
            @RequestParam(value = "date") String date,
            Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreDrugOrderPatient patient = hospitalRestCoreService.getDrugOrderPatientByIdentifier(identifier, date);
        PatientDetails patientDetails = getPatientDrugOrderDetails(patient);
        List<InventoryStoreDrugOrderPatientDetail> orderPatientDetail = hospitalRestCoreService.getDrugOrderPatientDetail(patient, date);

        List<OpdDrugOrder> drugOrders = hospitalRestCoreService.listAllDrugOrder();
        List<OpdDrugOrder> orders = new ArrayList<OpdDrugOrder>();

        for (InventoryStoreDrugOrderPatientDetail detail : orderPatientDetail)
            for (OpdDrugOrder order : drugOrders)
                if (Objects.equals(detail.getOpdDrugOrder(), order))
                    orders.add(order);

        List<OpdDrugOrderDetails> orderDetails;

        if (CollectionUtils.isEmpty(orders))
            orderDetails = new ArrayList<OpdDrugOrderDetails>();
        else
            orderDetails = orders.stream()
                .map(ods -> getDrugOrders(ods)).collect(Collectors.toList());

        if (orderDetails != null)
            Collections.sort(orderDetails);
        Map<String, Object> order = new HashMap<String, Object>();
        order.put("patientDetails", patientDetails);
        order.put("orderDetails", orderDetails);

        model.put("order", order);
        new ObjectMapper().writeValue(out, order);

    }

    @RequestMapping(value = "/patient-drug-order-details", method = RequestMethod.GET)
    public void getPatientDrugOrderDetails(@RequestParam(value = "orderId") Integer orderId,
            @RequestParam(value = "identifier") String identifier,
            @RequestParam(value = "date") String date,
            Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreDrugOrderPatient patient = hospitalRestCoreService.getDrugOrderPatientByIdentifier(identifier, date);
        PatientDetails patientDetails = getPatientDrugOrderDetails(patient);

        List<OpdDrugOrderDetail> details = hospitalRestCoreService.getDrugOrderDetailByOrderId(orderId);

        List<OpdDrugOrderDetailDetails> orderDetails;

        if (CollectionUtils.isEmpty(details))
            orderDetails = new ArrayList<OpdDrugOrderDetailDetails>();
        else
            orderDetails = details.stream()
                .map(ods -> getDrugOrderDetails(ods)).collect(Collectors.toList());

        if (orderDetails != null)
            Collections.sort(orderDetails);
        Map<String, Object> orders = new HashMap<String, Object>();
        orders.put("patientDetails", patientDetails);
        orders.put("orderDetails", orderDetails);

        model.put("orders", orders);
        new ObjectMapper().writeValue(out, orders);

    }

    @RequestMapping(value = "/process-drug-details", method = RequestMethod.GET)
    public void getProcessDrugDetails(@RequestParam(value = "drugName") String drugName,
            @RequestParam(value = "formulation") String formulation,
            Map<String, Object> model,
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
            if (Objects.equals(s.getName(), "Pharmacy"))
                store = s;


        List<InventoryStoreDrug> drugs = hospitalRestCoreService.listAllInventoryStoreDrug(store);
        InventoryStoreDrug inventoryStoreDrug = new InventoryStoreDrug();
        for (InventoryStoreDrug drug : drugs)
            if (Objects.equals(drug.getDrug().getName(), drugName))
                if (Objects.equals(drug.getFormulation().getName(), formulation))
                    inventoryStoreDrug = drug;

        if (inventoryStoreDrug.getDrug() == null)
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG, drugName));

        List<InventoryStoreDrugTransactionDetail> inventoryStoreDrugTransactionDetails =
                hospitalRestCoreService.listAllStoreDrugTransactionDetail(store);
        InventoryStoreDrugTransactionDetail transactionDetail = new InventoryStoreDrugTransactionDetail();
        for (InventoryStoreDrugTransactionDetail d : inventoryStoreDrugTransactionDetails)
            if (Objects.equals(d.getDrug().getName(), inventoryStoreDrug.getDrug().getName()))
                if (Objects.equals(d.getFormulation().getName(), inventoryStoreDrug.getFormulation().getName()))
                    transactionDetail = d;

        if (transactionDetail.getDrug() == null)
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_TRANSACTION_DETAIL, drugName));

        IssueDrugDetails drugDetails = getIssueDrugDetails(transactionDetail);

        model.put("drugDetails", drugDetails);
        new ObjectMapper().writeValue(out, drugDetails);

    }

    @RequestMapping(value = "/issue-drug", method = RequestMethod.POST)
    public ResponseEntity<Void> issueDrug(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
            @Valid @RequestBody InventoryStoreDrugIssueDetailPayload inventoryStoreDrugIssueDetailPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<OpdDrugOrderDetail> details = hospitalRestCoreService.getDrugOrderDetailByOrderId(inventoryStoreDrugIssueDetailPayload.getOrderId());

        for (OpdDrugOrderDetail d : details)
            if (Objects.equals(d.getInventoryDrug().getName(), inventoryStoreDrugIssueDetailPayload.getDrug().getName()))
                if (Objects.equals(d.getInventoryDrugFormulation().getName(), inventoryStoreDrugIssueDetailPayload.getFormulation().getName())) {
                    d.setDeleted(true);
                    hospitalRestCoreService.saveOrUpdateOpdDrugOrderDetail(d);
                }

        InventoryStoreDrugOrderIssueDetail drugOrderIssueDetail = new InventoryStoreDrugOrderIssueDetail();
        drugOrderIssueDetail.setDrug(inventoryStoreDrugIssueDetailPayload.getDrug());
        drugOrderIssueDetail.setFormulation(inventoryStoreDrugIssueDetailPayload.getFormulation());
        drugOrderIssueDetail.setQuantity(inventoryStoreDrugIssueDetailPayload.getQuantity());
        drugOrderIssueDetail.setCreatedDate(new Date());
        drugOrderIssueDetail.setDeleted(false);
        hospitalRestCoreService.saveOrUpdateInventoryStoreDrugOrderIssueDetail(drugOrderIssueDetail);

        List<InventoryStoreDrugOrderIssueDetail> issueDetails = hospitalRestCoreService.listAllInventoryStoreDrugOrderIssueDetail();

        List<InventoryStoreDrugIssueDetails> issues;

        if (CollectionUtils.isEmpty(issueDetails))
            issues = new ArrayList<InventoryStoreDrugIssueDetails>();
        else
            issues = issueDetails.stream().
                map(id -> getInventoryStoreDrugOrderIssueDetails(id)).collect(Collectors.toList());

        if (issues != null)
            Collections.sort(issues);
        model.put("issues", issues);
        new ObjectMapper().writeValue(out, issues);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/issue-slip", method = RequestMethod.GET)
    public void getIssueSlip(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryStoreDrugOrderIssueDetail> issueDetails = hospitalRestCoreService.listAllInventoryStoreDrugOrderIssueDetail();

        List<InventoryStoreDrugIssueDetails> issues;

        if (CollectionUtils.isEmpty(issueDetails))
            issues = new ArrayList<InventoryStoreDrugIssueDetails>();
        else
            issues = issueDetails.stream().
                map(id -> getInventoryStoreDrugOrderIssueDetails(id)).collect(Collectors.toList());

        if (issues != null)
            Collections.sort(issues);
        model.put("issues", issues);
        new ObjectMapper().writeValue(out, issues);

    }

    @RequestMapping(value = "/finish-order", method = RequestMethod.POST)
    public ResponseEntity<Void> saveAndSend(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
            @Valid @RequestBody InventoryStoreDrugIssuePayload inventoryStoreDrugIssuePayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
//        InventoryStore store = hospitalRestCoreService
//                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles())); //TODO: commit this
        InventoryStore store = new InventoryStore();
        List<InventoryStore> storeList = hospitalRestCoreService.listAllInventoryStore();

        for (InventoryStore s : storeList)
            if (Objects.equals(s.getName(), "Pharmacy"))
                store = s;

        List<InventoryStoreDrugOrderIssueDetail> issueDetails = hospitalRestCoreService.listAllInventoryStoreDrugOrderIssueDetail();

        InventoryStoreDrugTransaction transaction = new InventoryStoreDrugTransaction();
        transaction.setStore(store);
        transaction.setStatus(1);
        transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
        transaction.setCreatedDate(new Date());
        transaction.setCreatedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateStoreDrugTransaction(transaction);

        PersonName personName = hospitalRestCoreService.getPersonNameByNameString(inventoryStoreDrugIssuePayload.getPatientName());
        PatientIdentifier identifier = hospitalRestCoreService.getPatientIdentifierByPatientId(personName.getPerson().getPersonId());
        Patient patient = hospitalRestCoreService.getPatientByPatientId(personName.getPerson().getPersonId());

        InventoryStoreDrugPatient drugPatient = new InventoryStoreDrugPatient();
        drugPatient.setStore(store);
        drugPatient.setPatient(patient);
        drugPatient.setName(inventoryStoreDrugIssuePayload.getPatientName());
        drugPatient.setPrescription(inventoryStoreDrugIssuePayload.getComment());
        drugPatient.setIdentifier(identifier.getIdentifier());
        drugPatient.setTotal(inventoryStoreDrugIssuePayload.getTotal());
        drugPatient.setWaiverPercentage(inventoryStoreDrugIssuePayload.getWaiverPercentage());
        drugPatient.setTotalPayable(inventoryStoreDrugIssuePayload.getTotalPayable());
        drugPatient.setAmountPaid(inventoryStoreDrugIssuePayload.getAmountPaid());
        drugPatient.setBalanceReturned(inventoryStoreDrugIssuePayload.getBalanceReturned());
        drugPatient.setCreatedDate(new Date());
        drugPatient.setCreatedBy(Context.getAuthenticatedUser());
        hospitalRestCoreService.saveOrUpdateInventoryStoreDrugPatient(drugPatient);

        List<InventoryStoreDrugTransactionDetail> detailList = new ArrayList<InventoryStoreDrugTransactionDetail>();
        if (issueDetails.size() > 0) {
            for (InventoryStoreDrugOrderIssueDetail d : issueDetails) {

                List<InventoryStoreDrug> drugs = hospitalRestCoreService.listAllInventoryStoreDrug(store);
                InventoryStoreDrug inventoryStoreDrug = new InventoryStoreDrug();
                for (InventoryStoreDrug drug : drugs)
                    if (Objects.equals(drug.getDrug().getName(), d.getDrug().getName()))
                        if (Objects.equals(drug.getFormulation().getName(), d.getFormulation().getName()))
                            inventoryStoreDrug = drug;

                if (inventoryStoreDrug.getDrug() == null)
                    throw new ResourceNotFoundException(
                            String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG, d.getDrug().getName()));

                List<InventoryStoreDrugTransactionDetail> inventoryStoreDrugTransactionDetails =
                        hospitalRestCoreService.listAllStoreDrugTransactionDetail(store);
                InventoryStoreDrugTransactionDetail transactionD = new InventoryStoreDrugTransactionDetail();

                for (InventoryStoreDrugTransactionDetail detail : inventoryStoreDrugTransactionDetails)
                    if (Objects.equals(detail.getDrug().getName(), inventoryStoreDrug.getDrug().getName()))
                        if (Objects.equals(detail.getFormulation().getName(), inventoryStoreDrug.getFormulation().getName()))
                            transactionD = detail;

                if (transactionD.getDrug() == null)
                    throw new ResourceNotFoundException(
                            String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_TRANSACTION_DETAIL, d.getDrug().getName()));

                InventoryStoreDrugTransactionDetail transactionDetail = new InventoryStoreDrugTransactionDetail();
                InventoryStoreDrugPatientDetail patientDetail = new InventoryStoreDrugPatientDetail();
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
                transactionDetail.setReceiptDate(new Date());
                transactionDetail.setCreatedDate(new Date());
                transactionDetail.setCreatedBy(Context.getAuthenticatedUser());
                hospitalRestCoreService.saveOrUpdateDrugTransactionDetail(transactionDetail);
                d.setDeleted(true);
                hospitalRestCoreService.saveOrUpdateInventoryStoreDrugOrderIssueDetail(d);
                patientDetail.setStoreDrugPatient(drugPatient);
                patientDetail.setTransactionDetail(transactionDetail);
                patientDetail.setQuantity(d.getQuantity());
                hospitalRestCoreService.saveOrUpdateInventoryStoreDrugPatientDetail(patientDetail);
                detailList.add(transactionDetail);
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

    public InventoryStoreDrugIssueDetails getInventoryStoreDrugOrderIssueDetails(InventoryStoreDrugOrderIssueDetail InventoryStoreDrugOrderIssueDetail) {
        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        InventoryStoreDrugIssueDetails isdid = new InventoryStoreDrugIssueDetails();
//        InventoryStore store = hospitalRestCoreService
//                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
        InventoryStore store = new InventoryStore();
        List<InventoryStore> storeList = hospitalRestCoreService.listAllInventoryStore();

        for (InventoryStore s : storeList)
            if (Objects.equals(s.getName(), "Pharmacy"))
                store = s;

        InventoryStoreDrugTransactionDetail transactionDetail = new InventoryStoreDrugTransactionDetail();
        List<InventoryStoreDrugTransactionDetail>  transactionDetails =
                hospitalRestCoreService.listAllStoreDrugTransactionDetail(store);

        for (InventoryStoreDrugTransactionDetail d : transactionDetails)
            if (Objects.equals(d.getDrug().getName(), InventoryStoreDrugOrderIssueDetail.getDrug().getName()))
                if (Objects.equals(d.getFormulation().getName(), InventoryStoreDrugOrderIssueDetail.getFormulation().getName()))
                    transactionDetail = d;

        if (transactionDetail.getDrug() == null)
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_TRANSACTION_DETAIL,
                    InventoryStoreDrugOrderIssueDetail.getDrug().getName()));


        isdid.setDrugName(InventoryStoreDrugOrderIssueDetail.getDrug().getName());
        isdid.setFormulation(InventoryStoreDrugOrderIssueDetail.getFormulation().getName());
        isdid.setQuantity(InventoryStoreDrugOrderIssueDetail.getQuantity());
        isdid.setMrp(transactionDetail.getTotalPrice());

        return isdid;
    }

    public IssueDrugDetails getIssueDrugDetails(InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail) {

        SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
        IssueDrugDetails idds = new IssueDrugDetails();
        idds.setDateExpiry(formatterExt.format(inventoryStoreDrugTransactionDetail.getDateExpiry()));
        idds.setDateManufacture(formatterExt.format(inventoryStoreDrugTransactionDetail.getDateManufacture()));
        idds.setCompanyName(inventoryStoreDrugTransactionDetail.getCompanyName());
        idds.setBatchNo(inventoryStoreDrugTransactionDetail.getBatchNo());
        idds.setQuantityAvailable(inventoryStoreDrugTransactionDetail.getCurrentQuantity());

        return idds;
    }

    public OpdDrugOrderDetailDetails getDrugOrderDetails(OpdDrugOrderDetail orderDetail) {

        OpdDrugOrderDetailDetails ododds = new OpdDrugOrderDetailDetails();
        ododds.setDrugName(orderDetail.getInventoryDrug().getName());
        ododds.setFormulation(orderDetail.getInventoryDrugFormulation().getName());
        ododds.setFrequency(orderDetail.getFrequency().getDisplayString());
        ododds.setNoOfDays(orderDetail.getNoOfDays());
        ododds.setComments(orderDetail.getComments());
        return ododds;
    }

    public OpdDrugOrderDetails getDrugOrders(OpdDrugOrder order) {
        SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
        OpdDrugOrderDetails odods = new OpdDrugOrderDetails();
        odods.setOrderId(order.getOpdDrugOrderId());
        odods.setOrderDate(formatterExt.format(order.getCreatedDate()));
        odods.setSentFrom(order.getReferralWardName());
        return odods;
    }

    public PatientDetails getPatientDrugOrderDetails(InventoryStoreDrugOrderPatient inventoryStoreDrugOrderPatient) {

        SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
        PatientDetails pds = new PatientDetails();
        pds.setPatientId(inventoryStoreDrugOrderPatient.getPatient().getPatientId());
        pds.setPatientIdentifier(inventoryStoreDrugOrderPatient.getIdentifier());
        pds.setPatientName(inventoryStoreDrugOrderPatient.getName());
        pds.setGender(inventoryStoreDrugOrderPatient.getGender());
        pds.setAge(inventoryStoreDrugOrderPatient.getAge());
        pds.setCreatedOn(formatterExt.format(inventoryStoreDrugOrderPatient.getCreatedDate()));
        return pds;
    }

}
