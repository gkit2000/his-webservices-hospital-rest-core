package org.openmrs.module.hospitalrestcore.inventory;

import java.util.Map;
import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugTransactionPayload {

    private String transactionUuid;

    private List<String> transactionDetailUuids;

    private String indentUuid;

    private Map<String, Integer> transfers;



    public String getTransactionUuid() {
        return transactionUuid;
    }

    public void setTransactionUuid(String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }

    public List<String> getTransactionDetailUuids() {
        return transactionDetailUuids;
    }

    public void setTransactionDetailUuids(List<String> transactionDetailUuids) {
        this.transactionDetailUuids = transactionDetailUuids;
    }

    public String getIndentUuid() {
        return indentUuid;
    }

    public void setIndentUuid(String indentUuid) {
        this.indentUuid = indentUuid;
    }

    public Map<String, Integer> getTransfers() {
        return transfers;
    }

    public void setTransfers(Map<String, Integer> transfers) {
        this.transfers = transfers;
    }
}
