package com.digitalojt.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalojt.web.consts.LogMessage;
import com.digitalojt.web.util.MessageManager;

/**
 * 抽象コントローラー
 * ※全てのコントローラークラスは、このクラスを継承すること
 *
 * @author dotlife
 *
 */
public class AbstractController {

    // ロガーは各コントローラで使えるように共通化
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 現在のメソッド名を取得
     * @return メソッド名
     */
    private String getMethodName() {
        return StackWalker.getInstance()
                .walk(frames -> frames.skip(2).findFirst().map(StackWalker.StackFrame::getMethodName))
                .orElse("UnknownMethod");
    }
    
	/**
	 * 処理開始のログ
	 * 
	 * @param logger ロガーオブジェクト
	 * @param action アクション名
	 */
	protected void logStart(String action) {
		logger.info(String.format(LogMessage.ACCESS_LOG));
		logger.info(String.format(LogMessage.APP_LOG, action, getMethodName(), LogMessage.PROCESS_START));
	}

	/**
	 * 処理終了のログ
	 * 
	 * @param logger ロガーオブジェクト
	 * @param action アクション名
	 */
	protected void logEnd(String action) {
		logger.info(String.format(LogMessage.APP_LOG, action, getMethodName(), LogMessage.PROCESS_END));
	}

	/**
	 * エラーログ
	 * 
	 * @param logger ロガーオブジェクト
	 * @param action アクション名
	 * @param e 例外オブジェクト
	 */
	protected void logError(String action, Exception e) {
		logger.error(String.format(LogMessage.ERROR_LOG, action, getMethodName(), e));
	}

	/**
	 * バリデーションエラーログ
	 * 
	 * @param logger ロガーオブジェクト
	 * @param action アクション名
	 * @param errorMsg エラーメッセージ
	 */
	protected void logValidationError(String action, String errorMsg) {
		logger.error(String.format(LogMessage.ERROR_LOG, action, getMethodName(), errorMsg));
	}

	/**
	 * エラーメッセージをフラッシュメッセージにセット
	 * 
	 * @param messageSource メッセージソース
	 * @param redirectAttributes リダイレクト属性
	 * @param messageConst メッセージ定数
	 */
	public void setFlashErrorMsg(MessageSource messageSource, RedirectAttributes redirectAttributes,
			String messageConst) {
		String errorMsg = MessageManager.getMessage(messageSource, messageConst);
		redirectAttributes.addFlashAttribute(LogMessage.FLASH_ATTRIBUTE_ERROR, errorMsg);
	}
}
