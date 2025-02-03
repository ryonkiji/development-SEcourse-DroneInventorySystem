package com.digitalojt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.digitalojt.web.consts.ModelAttributeContents;
import com.digitalojt.web.consts.UrlConsts;

/**
 * エラーコントローラー
 * 
 * @author dotlife
 */
@Controller
public class ErrorController {

	@GetMapping(UrlConsts.ERROR)
	public String handleError(Model model, @RequestParam(required = false) String errorMsg) {
		if (errorMsg != null) {
			model.addAttribute(ModelAttributeContents.ERROR_MSG, errorMsg); // エラーメッセージをモデルに追加
		}
		return UrlConsts.ERROR_VIEW;
	}
}
