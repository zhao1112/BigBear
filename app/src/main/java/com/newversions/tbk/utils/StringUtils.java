package com.newversions.tbk.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


/**
 * @author po.
 * @date 2016/10/17.
 */
public class StringUtils {

	/**
	 * 获取格式化后的URL
	 * <p/>
	 * 格式URL + APPID
	 */
	public static String getFormatUrl(String url) {
		return url;//+ "&" + FlavorsConfig.AppID
	}

	/**
	 * 判断是否有特殊字符
	 */
	public static boolean isConSpeCharacters(String string) {
		return string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() != 0;
	}

	/*
	 *时间转换器
	 * */
	public static String dateToStrLong(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm:ss");
		return formatter.format(dateDate);
	}

	public static boolean isEmpty(CharSequence str) {
		return (str == null || str.length() == 0 || str == "null");
	}

	/**
	 * 判断密码格式
	 *
	 * @param str
	 * @return
	 */
	public static boolean isPassword(CharSequence str) {
		return (str != null && str.length() >= 6 && str.length() <= 10);
	}


	/**
	 * 校验手机号
	 *
	 * @param mobile 手机号
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {


		return mobile.length() == 11;
	}

	/**
	 * 校验身份证
	 *
	 * @param idCard 身份证
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {
		return 18 == idCard.length() && validateDate(idCard.substring(6, 14)) && checkSum(idCard);
	}

	/**
	 * 验证身份证日期
	 */
	private static boolean validateDate(String date) {
		try {

			if (6 == date.length()) {
				int month = Integer.parseInt(date.substring(2, 4));
				int day = Integer.parseInt(date.substring(4));

				if (month > 0 && month < 13 && day > 0 && day < 31) {
					return true;
				}
			} else {
				Date birthday = new SimpleDateFormat("yyyyMMdd").parse(date);
				GregorianCalendar calendar = new GregorianCalendar();
				int month = Integer.parseInt(date.substring(4, 6));
				int day = Integer.parseInt(date.substring(6));
				if (month < 1 || month > 12) {
					return false;
				}
				calendar.setTime(birthday);
				boolean isValidate = false;
				switch (month) {
					case 1:
					case 3:
					case 5:
					case 7:
					case 8:
					case 10:
					case 12:
						isValidate = (day > 0 && day < 32);
						break;
					case 4:
					case 6:
					case 9:
					case 11:
						isValidate = (day > 0 && day < 31);
						break;
					case 2:
						if (calendar.isLeapYear(calendar.get(Calendar.YEAR))) {
							isValidate = (day > 0 && day < 30);
						} else {
							isValidate = (day > 0 && day < 29);
						}
						break;
				}
				return isValidate;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 验证身份请 校验位
	 */
	private static boolean checkSum(String id) {
		id = id.toUpperCase();
		String idPre = id.substring(0, 17);
		String sumCode = id.substring(17);
		if (!idPre.matches("^[0-9]*$")) {
			return false;
		}
		int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
		String[] verifyCode = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
		int sum = 0;
		try {
			for (int i = 0; i < idPre.length(); ++i) {
				int ai = Integer.parseInt(String.valueOf(idPre.charAt(i)));
				sum += ai * power[i];
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return sumCode.equalsIgnoreCase(verifyCode[sum % 11]);
	}

	/**
	 * 获取当前城市的城市ID
	 *
	 * @param adCode 区县COde
	 */
	public static String getCityCode(String adCode) {
		String cityCode = "";
		if (!TextUtils.isEmpty(adCode)) {
			cityCode = adCode;
			if (adCode.length() > 4) {
				cityCode = adCode.substring(0, 4);
			}
			if (adCode.startsWith("12") || adCode.startsWith("11") || adCode.startsWith("50") || adCode.startsWith(
					"31")) {
				cityCode = adCode.substring(0, 2);
			}
		}
		return cityCode;
	}


	/**
	 * 从url里面截取参数
	 *
	 * @param url
	 * @return
	 */
	public static HashMap<String, String> parseParams(String url) {
		HashMap<String, String> map = new HashMap<>();
		try {
			int start = url.indexOf("?");
			if (start >= 0) {
				String[] params = url.substring(start + 1).split("&");
				for (String param : params) {
					try {
						int e = param.indexOf("=");
						if (e > 0) {
							String k = param.substring(0, e);
							String v = param.substring(e + 1);
							map.put(k, v);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 用于比较字符串大小（续航里程）
	 * 不足3位在前方补零
	 */
	public static String addZeroForNum(String num) {
		String afterNum;
		switch (num.length()) {
			case 0:
				afterNum = "000";
				break;
			case 1:
				afterNum = "00" + num;
				break;
			case 2:
				afterNum = "0" + num;
				break;
			default:
				afterNum = num;
				break;
		}
		return afterNum;
	}

	/**
	 * 格式化时间  统一格式 2016-12-13 15:37
	 */
	public static String getFormatTime(String data) {
		String format = "";
		try {
			format = data.substring(0, data.length() - 3);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 添加标签
	 *
	 * @param str 正文
	 * @return
	 */
	public static SpannableString addImageLabel(Context context, int resid, String str) {
//		SpannableStringBuilder style = new SpannableStringBuilder("      " + str);
//           ImageSpan imageSpan = new ImageSpan(context, resid);
//           style.setSpan(imageSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		SpannableString sp = new SpannableString("  " + str);
		Drawable drawable = context.getResources().getDrawable(resid);
// 设置图标大小
//		drawable.setBounds(0, AppUtils.dip2px(context, 5), AppUtils.dip2px(context, 25), AppUtils.dip2px(context, 19));
//		StickerSpan span = new StickerSpan(drawable, ImageSpan.ALIGN_BASELINE);// 防止只有文字时，表情上移的问题
//		style.setSpan(span, 0, 3 + "]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

		//居中对齐imageSpan
		CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
		sp.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);

		//普通imageSpan 做对比
//		ImageSpan imageSpan2 = new ImageSpan(drawable);
//		sp.setSpan(imageSpan2, 3, 4, ImageSpan.ALIGN_BASELINE);
		return sp;
//		return style;
	}
}
