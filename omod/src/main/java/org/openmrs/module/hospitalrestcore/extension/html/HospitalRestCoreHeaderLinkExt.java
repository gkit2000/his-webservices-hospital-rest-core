package org.openmrs.module.hospitalrestcore.extension.html;

import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.HospitalRestCoreUtils;
import org.openmrs.module.web.extension.LinkExt;

public class HospitalRestCoreHeaderLinkExt extends LinkExt {
	
	@Override
	public String getLabel() {
		return Context.getMessageSourceService().getMessage("hospitalrestcore.header.link");
	}
	
	@Override
	public String getRequiredPrivilege() {
		return HospitalRestCoreUtils.PRIV_VIEW_BILLING;
	}
	
	@Override
	public String getUrl() {
		return "module/hospitalrestcore/orderList.list";
	}
	
}
