package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;

import java.util.List;
import java.util.Objects;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugIssueDetailPayload {

    private InventoryDrug drug;
    private InventoryDrugFormulation formulation;
    private Integer quantity;

    HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

    public InventoryDrug getDrug() {
        return drug;
    }

    public void setDrug(String drug) {

        InventoryDrug inventoryDrug = new InventoryDrug();
        List<InventoryDrug> inventoryDrugs = hospitalRestCoreService.listAllInventoryDrug();

        for (InventoryDrug i : inventoryDrugs)
            if (Objects.equals(i.getName(), drug))
                inventoryDrug = i;

        if (inventoryDrug.getName() == null)
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG, drug));
        else
            this.drug = inventoryDrug;
    }

    public InventoryDrugFormulation getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {

        InventoryDrugFormulation drugFormulation1 = new InventoryDrugFormulation();
        List<InventoryDrugFormulation> formulationList = hospitalRestCoreService.listAllInventoryDrugFormulation();

        for (InventoryDrugFormulation drugFormulation : formulationList)
            if (Objects.equals(drugFormulation.getName(), formulation))
                drugFormulation1 = drugFormulation;

        if (drugFormulation1.getName() == null)
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_FORMULATION, formulation));
        else
            this.formulation = drugFormulation1;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
