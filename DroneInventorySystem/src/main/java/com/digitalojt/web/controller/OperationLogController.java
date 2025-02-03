package com.digitalojt.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.digitalojt.web.consts.LogMessage;
import com.digitalojt.web.consts.ModelAttributeContents;
import com.digitalojt.web.consts.UrlConsts;
import com.digitalojt.web.entity.OperationLog;
import com.digitalojt.web.service.OperationLogService;

import lombok.RequiredArgsConstructor;

/**
 * 操作履歴画面のコントローラークラス
 * 
 * @author dotlife
 *
 */
@Controller
@RequiredArgsConstructor
public class OperationLogController extends AbstractController {

	/** 操作履歴 サービス */
	private final OperationLogService operationLogService;

	/**
	 * 初期表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(UrlConsts.OPERATION_LOG)
	public String index(Model model) {

		logStart(LogMessage.HTTP_GET);

		// 操作履歴情報の取得
		List<OperationLog> operationLogList = operationLogService.getOperationLogList();
		model.addAttribute(ModelAttributeContents.OPERATION_LOG_LIST, operationLogList);

		logEnd(LogMessage.HTTP_GET);

		return UrlConsts.OPERATION_LOG_INDEX;
	}
}
