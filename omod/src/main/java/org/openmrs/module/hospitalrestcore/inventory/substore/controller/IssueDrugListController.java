package org.openmrs.module.hospitalrestcore.inventory.substore.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.*;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mujuzi Moses
 *
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/issueDrugList")
public class IssueDrugListController extends BaseRestController {

    @RequestMapping(value = "/add-to-slip", method = RequestMethod.POST)
    public ResponseEntity<Void> addToSlip(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
            @Valid @RequestBody InventoryStoreDrugIssueDetailPayload InventoryStoreDrugIssueDetailPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreDrugIssueDetail drugIssueDetail = new InventoryStoreDrugIssueDetail();
        drugIssueDetail.setDrug(InventoryStoreDrugIssueDetailPayload.getDrug());
        drugIssueDetail.setFormulation(InventoryStoreDrugIssueDetailPayload.getFormulation());
        drugIssueDetail.setQuantity(InventoryStoreDrugIssueDetailPayload.getQuantity());
        drugIssueDetail.setCreatedDate(new Date());
        drugIssueDetail.setDeleted(false);
        hospitalRestCoreService.saveOrUpdateInventoryStoreDrugIssueDetail(drugIssueDetail);

        List<InventoryStoreDrugIssueDetail> issueDetails = hospitalRestCoreService.listAllInventoryStoreDrugIssueDetail();

        List<InventoryStoreDrugIssueDetails> issues = issueDetails.stream().
                map(id -> getInventoryStoreDrugIssueDetails(id)).collect(Collectors.toList());

        if (issues != null)
            Collections.sort(issues);
        model.put("issues", issues);
        new ObjectMapper().writeValue(out, issues);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/drug-details", method = RequestMethod.GET)
    public void getDrugDetail(@RequestParam(value = "drugName") String drugName,
            @RequestParam(value = "formulation") String formulation,
            Map<String, Object> model,
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
//            if (Objects.equals(s.getName(), "Pharmacy"))
//                store = s;


        List<InventoryStoreDrug> drugs = hospitalRestCoreService.listAllInventoryStoreDrug(store);
        if (CollectionUtils.isEmpty(drugs))
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_LIST, store.getName()));

        InventoryStoreDrug inventoryStoreDrug = new InventoryStoreDrug();
        for (InventoryStoreDrug drug : drugs)
            if (Objects.equals(drug.getDrug().getName(), drugName))
                if (Objects.equals(drug.getFormulation().getName(), formulation))
                    inventoryStoreDrug = drug;

        if (inventoryStoreDrug.getDrug() == null)
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_2, drugName, formulation, store.getName()));

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

    @RequestMapping(value = "/issue-slip", method = RequestMethod.GET)
    public void getIssueSlip(Map<String, Object> model,
                             HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryStoreDrugIssueDetail> issueDetails = hospitalRestCoreService.listAllInventoryStoreDrugIssueDetail();

        List<InventoryStoreDrugIssueDetails> issues = issueDetails.stream().
                map(id -> getInventoryStoreDrugIssueDetails(id)).collect(Collectors.toList());

        if (issues != null)
            Collections.sort(issues);
        model.put("issues", issues);
        new ObjectMapper().writeValue(out, issues);

    }

    @RequestMapping(value = "/find-patient", method = RequestMethod.GET)
    public void findPatient(@RequestParam(value = "identifierOrName") String identifierOrName,
                            Map<String, Object> model,
                            HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        Integer patientId = hospitalRestCoreService.getPatientIdByIdentifierString(identifierOrName);
        PersonName personName;

        if (patientId != null)
            personName = hospitalRestCoreService.getPersonNameByPersonId(patientId);
        else
            personName = hospitalRestCoreService.getPersonNameByNameString(identifierOrName);

        PatientDetails patientDetails;
        if (personName != null)
            patientDetails = getPatientDetails(personName);
        else
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_PATIENT_IDENTIFIER_OR_NAME, identifierOrName));

        model.put("patientDetails", patientDetails);
        new ObjectMapper().writeValue(out, patientDetails);
    }

    @RequestMapping(value = "/save-issue-patient", method = RequestMethod.POST)
    public ResponseEntity<Void> saveIssuePatient(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
            @Valid @RequestBody InventoryStoreIssuePatientDetailPayload inventoryStoreIssuePatientDetailPayload)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreIssuePatientDetail isipd = new InventoryStoreIssuePatientDetail();
        isipd.setPatientIdentifier(inventoryStoreIssuePatientDetailPayload.getPatientIdentifier());
        isipd.setPatientName(inventoryStoreIssuePatientDetailPayload.getPatientName());
        isipd.setCategory(inventoryStoreIssuePatientDetailPayload.getCategory());
        isipd.setAge(inventoryStoreIssuePatientDetailPayload.getAge());
        isipd.setCreatedDate(new Date());
        isipd.setDeleted(false);
        hospitalRestCoreService.saveOrUpdateInventoryStoreIssuePatientDetail(isipd);

        InventoryStoreIssuePatientDetail patientDetails = hospitalRestCoreService.getInventoryStoreIssuePatientDetail();
        PatientDetails patient = null;

        if (patientDetails != null)
            patient = getInventoryStoreIssuePatientDetails(patientDetails);

        model.put("patients", patient);
        new ObjectMapper().writeValue(out, patient);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/get-issue-patient", method = RequestMethod.GET)
    public void getIssuePatient(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        InventoryStoreIssuePatientDetail patientDetails = hospitalRestCoreService.getInventoryStoreIssuePatientDetail();
        PatientDetails patient = null;

        if (patientDetails != null)
            patient = getInventoryStoreIssuePatientDetails(patientDetails);

        model.put("patients", patient);
        new ObjectMapper().writeValue(out, patient);

    }

    @RequestMapping(value = "/save-and-send", method = RequestMethod.POST)
    public ResponseEntity<Void> saveAndSend(HttpServletRequest request, HttpServletResponse response,
            @Valid @RequestBody InventoryStoreDrugIssuePayload inventoryStoreDrugIssuePayload, Map<String, Object> model)
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
//            if (Objects.equals(s.getName(), "Pharmacy"))
//                store = s;

        List<InventoryStoreDrugIssueDetail> issueDetails = hospitalRestCoreService.listAllInventoryStoreDrugIssueDetail();
        InventoryStoreIssuePatientDetail patientDetails = hospitalRestCoreService.getInventoryStoreIssuePatientDetail();

        List<InventoryStoreDrug> drugs = hospitalRestCoreService.listAllInventoryStoreDrug(store);
        if (CollectionUtils.isEmpty(drugs))
            throw new ResourceNotFoundException(
                    String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_LIST, store.getName()));

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
            for (InventoryStoreDrugIssueDetail d : issueDetails) {

                InventoryStoreDrug inventoryStoreDrug = new InventoryStoreDrug();
                for (InventoryStoreDrug drug : drugs)
                    if (Objects.equals(drug.getDrug().getName(), d.getDrug().getName()))
                        if (Objects.equals(drug.getFormulation().getName(), d.getFormulation().getName()))
                            inventoryStoreDrug = drug;

                if (inventoryStoreDrug.getDrug() == null)
                    throw new ResourceNotFoundException(
                            String.format(OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_2,
                                    d.getDrug().getName(), d.getFormulation().getName(), store.getName()));

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

                if (inventoryStoreDrug.getCurrentQuantity() >= d.getQuantity()) {
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
                    inventoryStoreDrug.setCurrentQuantity(inventoryStoreDrug.getCurrentQuantity() - d.getQuantity());
                    inventoryStoreDrug.setLastModifiedDate(new Date());
                    inventoryStoreDrug.setLastModifiedBy(Context.getAuthenticatedUser());
                    hospitalRestCoreService.saveOrUpdateInventoryStoreDrug(inventoryStoreDrug);
                    hospitalRestCoreService.saveOrUpdateDrugTransactionDetail(transactionDetail);
                    detailList.add(transactionDetail);
                    d.setDeleted(true);
                    hospitalRestCoreService.saveOrUpdateInventoryStoreDrugIssueDetail(d);
                    patientDetail.setStoreDrugPatient(drugPatient);
                    patientDetail.setTransactionDetail(transactionDetail);
                    patientDetail.setQuantity(d.getQuantity());
                    hospitalRestCoreService.saveOrUpdateInventoryStoreDrugPatientDetail(patientDetail);
                } else
                    throw new ResourceNotFoundException(
                            String.format(OpenmrsCustomConstants.DRUG_ERROR_NOT_ENOUGH_DRUG_QUANTITY,
                                    d.getDrug().getName()));
            }

            patientDetails.setDeleted(true);
            hospitalRestCoreService.saveOrUpdateInventoryStoreIssuePatientDetail(patientDetails);
        }

        List<InventoryStoreDrugTransactionDetails> details = detailList.stream()
                .map(sdtds -> getTransactionDetails(sdtds)).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(details))
            Collections.sort(details);
        model.put("details", details);
        new ObjectMapper().writeValue(out, details);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/clear-issue", method = RequestMethod.DELETE)
    public ResponseEntity<Void> clearIssue(HttpServletRequest request, HttpServletResponse response)
            throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        List<InventoryStoreDrugIssueDetail> issueDetails = hospitalRestCoreService.listAllInventoryStoreDrugIssueDetail();
        InventoryStoreIssuePatientDetail patient = hospitalRestCoreService.getInventoryStoreIssuePatientDetail();

        if (issueDetails.size() > 0)
            for (InventoryStoreDrugIssueDetail detail : issueDetails) {
                detail.setDeleted(true);
                hospitalRestCoreService.saveOrUpdateInventoryStoreDrugIssueDetail(detail);
            }
        if (!StringUtils.isEmpty(patient.getPatientName())) {
            patient.setDeleted(true);
            hospitalRestCoreService.saveOrUpdateInventoryStoreIssuePatientDetail(patient);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/issues", method = RequestMethod.GET)
    public void getIssueList(@RequestParam(value="pageSize",required=false)  Integer pageSize,
            @RequestParam(value="currentPage",required=false)  Integer currentPage,
            @RequestParam(value="identifierOrName",required=false)  String identifierOrName,
            @RequestParam(value="billNo",required=false)  Integer billNo,
            @RequestParam(value="fromDate",required=false)  String fromDate,
            @RequestParam(value="toDate",required=false)  String toDate, Map<String, Object> model,
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
//            if (Objects.equals(s.getName(), "Pharmacy"))
//                store = s;

        int total = hospitalRestCoreService.countStoreDrugPatient(store.getId(), identifierOrName, billNo, fromDate, toDate);
        String temp = "";

        if (identifierOrName != null)
            temp = "?identifierOrName=" + identifierOrName;
        if (billNo != null) {
            if (StringUtils.isBlank(temp))
                temp = "?billNo=" + billNo;
            else
                temp += "&billNo=" + billNo;
        }
        if (fromDate != null) {
            if (StringUtils.isBlank(temp))
                temp = "?fromDate=" + fromDate;
            else
                temp += "&fromDate=" + fromDate;
        }
        if (toDate != null) {
            if (StringUtils.isBlank(temp))
                temp = "?toDate=" + toDate;
            else
                temp += "&toDate=" + toDate;
        }

        PagingUtil pagingUtil = new PagingUtil(RequestUtil.getCurrentLink(request) + temp, pageSize, currentPage,
                total);
        List<InventoryStoreDrugPatient> inventoryStoreDrugPatients = hospitalRestCoreService.listStoreDrugPatient(store.getId(),
                identifierOrName, billNo, fromDate, toDate, pagingUtil.getStartPos(), pagingUtil.getPageSize());

        List<InventoryStoreDrugPatientDetails> issues;

        if (CollectionUtils.isEmpty(inventoryStoreDrugPatients))
            issues = new ArrayList<InventoryStoreDrugPatientDetails>();
        else
            issues = inventoryStoreDrugPatients.stream()
                .map(isdp -> getInventoryStoreDrugPatientDetails(isdp)).collect(Collectors.toList());

        if (issues != null)
            Collections.sort(issues);
        model.put("identifierOrName", identifierOrName);
        model.put("billNo", billNo);
        model.put("pagingUtil", pagingUtil);
        model.put("issues", issues);
        new ObjectMapper().writeValue(out, issues);
    }

    @RequestMapping(value = "/issue-details", method = RequestMethod.GET)
    public void getIssueDetail(@RequestParam(value = "billNo") Integer billNo, Map<String, Object> model,
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
//            if (Objects.equals(s.getName(), "Pharmacy"))
//                store = s;


        InventoryStoreDrugPatient patient = hospitalRestCoreService.getInventoryStoreDrugPatientById(billNo);
        if (patient == null)
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_BILL_NO, billNo));

        List<InventoryStoreDrugPatientDetail> details = hospitalRestCoreService.listAllInventoryStoreDrugPatientDetail();
        List<InventoryStoreDrugPatientDetail> patientDetails = new ArrayList<InventoryStoreDrugPatientDetail>();

        for (InventoryStoreDrugPatientDetail d : details)
            if (Objects.equals(d.getStoreDrugPatient(), patient))
                patientDetails.add(d);

        List<InventoryStoreDrugTransactionDetail> drugDetails = hospitalRestCoreService.listAllStoreDrugTransactionDetail(store);
        List<InventoryStoreDrugTransactionDetail> transactionDetails = new ArrayList<InventoryStoreDrugTransactionDetail>();
        for (InventoryStoreDrugPatientDetail d : patientDetails)
            for (InventoryStoreDrugTransactionDetail td : drugDetails)
                if (Objects.equals(d.getTransactionDetail(), td))
                    transactionDetails.add(td);


        IssuePatientDetails patients = getIssuePatientDetails(patient);
        IssueDetails issues = getIssueDetails(patient);
        List<DrugDetails> drugs;

        if (CollectionUtils.isEmpty(transactionDetails))
            drugs = new ArrayList<DrugDetails>();
        else
            drugs = transactionDetails.stream()
                .map(dds -> getDrugDetails(dds)).collect(Collectors.toList());

        if (drugs != null)
            Collections.sort(drugs);

        Map<String, Object> bill = new HashMap<String, Object>();
        bill.put("patientDetails", patients);
        bill.put("drugDetails", drugs);
        bill.put("issueDetails", issues);

        model.put("bill", bill);
        new ObjectMapper().writeValue(out, bill);
    }

    public IssuePatientDetails getIssuePatientDetails(InventoryStoreDrugPatient inventoryStoreDrugPatient) {

        SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        int personId = hospitalRestCoreService.getPatientIdByIdentifierString(inventoryStoreDrugPatient.getIdentifier());
        Person person = hospitalRestCoreService.getPersonByPersonId(personId);

        Date bDate = person.getBirthdate();
        ZoneId zoneId = ZoneId.systemDefault();
        Instant iDate = bDate.toInstant();
        LocalDate birthDate = iDate.atZone(zoneId).toLocalDate();
        Integer age = Period.between(birthDate, LocalDate.now()).getYears();

        IssuePatientDetails ipds = new IssuePatientDetails();
        ipds.setBillNo(inventoryStoreDrugPatient.getId());
        ipds.setIdentifier(inventoryStoreDrugPatient.getIdentifier());
        ipds.setPatientName(inventoryStoreDrugPatient.getName());
        ipds.setAge(age);
        ipds.setIssueDate(formatterExt.format(inventoryStoreDrugPatient.getCreatedDate()));
        ipds.setGender(person.getGender());
        return ipds;
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

    public DrugDetails getDrugDetails(InventoryStoreDrugTransactionDetail inventoryStoreDrugTransactionDetail) {

        SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
        int price = inventoryStoreDrugTransactionDetail.getTotalPrice().intValueExact();
        int quantity = inventoryStoreDrugTransactionDetail.getIssueQuantity();
        int total =  quantity * price;

        DrugDetails dds = new DrugDetails();
        dds.setBrandName(inventoryStoreDrugTransactionDetail.getDrug().getName());
        dds.setFormulation(inventoryStoreDrugTransactionDetail.getFormulation().getName());
        dds.setBatchNo(inventoryStoreDrugTransactionDetail.getBatchNo());
        dds.setDateExpiry(formatterExt.format(inventoryStoreDrugTransactionDetail.getDateExpiry()));
        dds.setQuantity(inventoryStoreDrugTransactionDetail.getIssueQuantity());
        dds.setMrp(inventoryStoreDrugTransactionDetail.getTotalPrice().intValueExact());
        dds.setTotal(total);
        return dds;
    }

    public IssueDetails getIssueDetails(InventoryStoreDrugPatient inventoryStoreDrugPatient) {

        float discount = inventoryStoreDrugPatient.getWaiverPercentage();
        int totalAmount = inventoryStoreDrugPatient.getTotal();
        int discountAmount = 0;
        if (discount > 0.0)
            discountAmount = (int) ((discount / 100) * totalAmount);

        IssueDetails ids = new IssueDetails();
        ids.setTotalAmount(totalAmount);
        ids.setDiscount(discount);
        ids.setDiscountAmount(discountAmount);
        ids.setTotalAmountPayable(discountAmount > 0 ? (totalAmount - discountAmount) : totalAmount);
        return ids;
    }

    public InventoryStoreDrugIssueDetails getInventoryStoreDrugIssueDetails(InventoryStoreDrugIssueDetail InventoryStoreDrugIssueDetail) {
        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

        InventoryStoreDrugIssueDetails isdid = new InventoryStoreDrugIssueDetails();
        InventoryStore store = hospitalRestCoreService
                .getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
//        InventoryStore store = new InventoryStore();
//        List<InventoryStore> storeList = hospitalRestCoreService.listAllInventoryStore();
//
//        for (InventoryStore s : storeList)
//            if (Objects.equals(s.getName(), "Pharmacy"))
//                store = s;

        InventoryStoreDrugTransactionDetail transactionDetail = new InventoryStoreDrugTransactionDetail();
        List<InventoryStoreDrugTransactionDetail>  transactionDetails =
                hospitalRestCoreService.listAllStoreDrugTransactionDetail(store);

        for (InventoryStoreDrugTransactionDetail d : transactionDetails)
            if (Objects.equals(d.getDrug().getName(), InventoryStoreDrugIssueDetail.getDrug().getName()))
                if (Objects.equals(d.getFormulation().getName(), InventoryStoreDrugIssueDetail.getFormulation().getName()))
                    transactionDetail = d;

        if (transactionDetail.getDrug() == null)
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_TRANSACTION_DETAIL,
                    InventoryStoreDrugIssueDetail.getDrug().getName()));


        isdid.setDrugName(InventoryStoreDrugIssueDetail.getDrug().getName());
        isdid.setFormulation(InventoryStoreDrugIssueDetail.getFormulation().getName());
        isdid.setQuantity(InventoryStoreDrugIssueDetail.getQuantity());
        isdid.setMrp(transactionDetail.getTotalPrice());

        return isdid;
    }

    public PatientDetails getPatientDetails(PersonName personName) {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        String givenName = personName.getGivenName();
        String middleName = personName.getMiddleName();
        String familyName = personName.getFamilyName();
        String fullName = givenName + (middleName != null ? " " + middleName + " " : " ") + familyName;
        Integer patientId = personName.getPerson().getPersonId();

        PatientIdentifier patientIdentifier = hospitalRestCoreService.getPatientIdentifierByPatientId(patientId);
        Person person = hospitalRestCoreService.getPersonByPersonId(patientId);

        Date bDate = person.getBirthdate();
        ZoneId zoneId = ZoneId.systemDefault();
        Instant iDate = bDate.toInstant();
        LocalDate birthDate = iDate.atZone(zoneId).toLocalDate();
        Integer age = Period.between(birthDate, LocalDate.now()).getYears();

        PatientDetails pds = new PatientDetails();
        pds.setPatientId(patientId);
        pds.setPatientIdentifier(patientIdentifier.getIdentifier());
        pds.setPatientName(fullName);
        pds.setAge(age);
        pds.setGender(person.getGender());
        return pds;
    }

    public PatientDetails getInventoryStoreIssuePatientDetails(InventoryStoreIssuePatientDetail inventoryStoreIssuePatientDetail) {

        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        int personId = hospitalRestCoreService.getPatientIdByIdentifierString(inventoryStoreIssuePatientDetail.getPatientIdentifier());
        Person person = hospitalRestCoreService.getPersonByPersonId(personId);

        PatientDetails pds = new PatientDetails();
        pds.setPatientId(personId);
        pds.setPatientIdentifier(inventoryStoreIssuePatientDetail.getPatientIdentifier());
        pds.setPatientName(inventoryStoreIssuePatientDetail.getPatientName());
        pds.setCategory(inventoryStoreIssuePatientDetail.getCategory());
        pds.setAge(inventoryStoreIssuePatientDetail.getAge());
        pds.setGender(person.getGender());
        return pds;
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

    private InventoryStoreDrugPatientDetails getInventoryStoreDrugPatientDetails(InventoryStoreDrugPatient inventoryStoreDrugPatient) {

        SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
        HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
        Integer patientId = inventoryStoreDrugPatient.getPatient().getPatientId();

        Person person = hospitalRestCoreService.getPersonByPersonId(patientId);
        Date bDate = person.getBirthdate();
        ZoneId zoneId = ZoneId.systemDefault();
        Instant iDate = bDate.toInstant();
        LocalDate birthDate = iDate.atZone(zoneId).toLocalDate();
        Integer age = Period.between(birthDate, LocalDate.now()).getYears();


        InventoryStoreDrugPatientDetails isdpd = new InventoryStoreDrugPatientDetails();
        isdpd.setBillNo(inventoryStoreDrugPatient.getId());
        isdpd.setIdentifier(inventoryStoreDrugPatient.getIdentifier());
        isdpd.setPatientName(inventoryStoreDrugPatient.getName());
        isdpd.setPatientAge(age);
        isdpd.setIssueDate(formatterExt.format(inventoryStoreDrugPatient.getCreatedDate()));
        isdpd.setDescription(inventoryStoreDrugPatient.getPrescription());
        return isdpd;
    }
}
