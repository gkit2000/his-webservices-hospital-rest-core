package org.openmrs.module.hospitalrestcore.inventory.mainstore.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategory;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStore;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransactionDetail;
import org.openmrs.module.hospitalrestcore.util.PagingUtil;
import org.openmrs.module.hospitalrestcore.util.RequestUtil;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/viewStockBalance")
public class ViewStockBalanceController extends BaseRestController {

	@RequestMapping(value = "/drug ", method = RequestMethod.GET)
	public void getDrugList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam(value = "drugName", required = false) String drugName,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate, Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws ResponseException, JsonGenerationException, JsonMappingException, IOException, ParseException {

		response.setContentType("application/json");
		ServletOutputStream out = response.getOutputStream();

		HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);
		InventoryStore store = hospitalRestCoreService
				.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));

		int total = hospitalRestCoreService.countViewStockBalance(store.getId(), categoryId, drugName, fromDate, toDate,
				false);
		String temp = "";
		if (categoryId != null) {
			temp = "?categoryId=" + categoryId;
		}

		if (drugName != null) {
			if (StringUtils.isBlank(temp)) {
				temp = "?drugName=" + drugName;
			} else {
				temp += "&drugName=" + drugName;
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
		List<InventoryStoreDrugTransactionDetail> stockBalances = hospitalRestCoreService.listViewStockBalance(
				store.getId(), categoryId, drugName, fromDate, toDate, false, pagingUtil.getStartPos(),
				pagingUtil.getPageSize());
		List<InventoryDrugCategory> listCategory = hospitalRestCoreService.listDrugCategory("", 0, 0);
		if (stockBalances != null)
			Collections.sort(stockBalances);
		model.put("categoryId", categoryId);
		model.put("drugName", drugName);
		// model.put("fromDate", fromDate);
		// model.put("toDate", toDate);
		model.put("pagingUtil", pagingUtil);
		model.put("stockBalances", stockBalances);
		model.put("listCategory", listCategory);
		new ObjectMapper().writeValue(out, stockBalances);

	}
}
