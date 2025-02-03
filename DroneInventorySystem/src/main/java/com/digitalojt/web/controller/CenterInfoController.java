package com.digitalojt.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.digitalojt.web.consts.LogMessage;
import com.digitalojt.web.consts.ModelAttributeContents;
import com.digitalojt.web.consts.Region;
import com.digitalojt.web.consts.UrlConsts;
import com.digitalojt.web.entity.CenterInfo;
import com.digitalojt.web.form.CenterInfoForm;
import com.digitalojt.web.service.CenterInfoService;
import com.digitalojt.web.util.MessageManager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 在庫センター情報画面のコントローラークラス
 * 
 * @author dotlife
 *
 */
@Controller
@RequiredArgsConstructor
public class CenterInfoController extends AbstractController {

	/** センター情報 サービス */
	private final CenterInfoService centerInfoService;

	/** メッセージソース */
	private final MessageSource messageSource;

	/**
	 * 都道府県Enumをリストに変換
	 * 
	 * @return
	 */
	@ModelAttribute(ModelAttributeContents.REGIONS)
	public List<Region> populateRegions() {
		return Arrays.asList(Region.values());
	}

	/**
	 * 初期表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(UrlConsts.CENTER_INFO)
	public String index(Model model) {

		logStart(LogMessage.HTTP_GET);

		// 在庫センター情報画面に表示するデータを取得
		List<CenterInfo> centerInfoList = centerInfoService.getCenterInfoData();

		// 画面表示用に商品情報リストをセット
		model.addAttribute(ModelAttributeContents.CENTER_INFO_LIST, centerInfoList);

		logEnd(LogMessage.HTTP_GET);

		return UrlConsts.CENTER_INFO_INDEX;
	}

	/**
	 * 検索結果表示
	 * 
	 * @param model
	 * @param form
	 * @return
	 */
	@PostMapping(UrlConsts.CENTER_INFO_SEARCH)
	public String search(Model model, @Valid CenterInfoForm form, BindingResult bindingResult) {

		logStart(LogMessage.HTTP_POST);

		// TODO: 入力値のバリデーションチェックに引っかかる場合は、InvalidInputExceptionをthrow
		// Valid項目チェック
		if (bindingResult.hasErrors()) {

			// エラーメッセージをプロパティファイルから取得
			String errorMsg = MessageManager.getMessage(messageSource,
					bindingResult.getGlobalError().getDefaultMessage());
			model.addAttribute(LogMessage.FLASH_ATTRIBUTE_ERROR, errorMsg);
			
			logValidationError(LogMessage.HTTP_POST, form + errorMsg);

			return UrlConsts.CENTER_INFO_INDEX;
		}

		// 在庫センター情報画面に表示するデータを取得
		List<CenterInfo> centerInfoList = centerInfoService.getCenterInfoData(form.getCenterName(), form.getRegion());

		// 画面表示用に商品情報リストをセット
		model.addAttribute(ModelAttributeContents.CENTER_INFO_LIST, centerInfoList);
		
		logEnd(LogMessage.HTTP_POST);

		return UrlConsts.CENTER_INFO_INDEX;
	}
}
