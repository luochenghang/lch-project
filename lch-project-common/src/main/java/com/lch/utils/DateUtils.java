package com.lch.utils;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.time.FastDateFormat;

import com.google.common.collect.Lists;


/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM", "yyyy/MM/dd",
	        "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm",
	        "yyyy.MM.dd HH", "yyyy年MM月dd日", "yyyy年MM月dd日 HH时mm分ss秒", "yyyy年MM月dd日 HH时mm分", "yyyy年MM月dd日 HH时", "yyyy年MM月" };

	/**
	 * 得到日期字符串 ，转换格式（yyyy-MM-dd）
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 得到某个日期的年和月
	 *
	 * @param date
	 * @return
	 */
	public static String formatYyMmCN(Date date) {
		return formatDate(date, "yyyy年MM月");
	}

	/**
	 * 得到某个日期的年和月
	 *
	 * @param date
	 * @return
	 */
	public static String formatYyMm(Date date) {
		return formatDate(date, "yyyy-MM");
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(long dateTime, String pattern) {
		return formatDate(new Date(dateTime), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (date != null) {
			if (StringUtils.isBlank(pattern)) {
				pattern = "yyyy-MM-dd";
			}
			formatDate = FastDateFormat.getInstance(pattern).format(date);
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return FastDateFormat.getInstance(pattern).format(new Date());
	}

	/**
	 * 得到当前日期前后多少天，月，年的日期字符串
	 *
	 * @param pattern
	 *            格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 * @param amont
	 *            数量，前为负数，后为正数
	 * @param type
	 *            类型，可参考Calendar的常量(如：Calendar.HOUR、Calendar.MINUTE、Calendar.SECOND)
	 * @return
	 */
	public static String getDate(String pattern, int amont, int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(type, amont);
		return FastDateFormat.getInstance(pattern).format(calendar.getTime());
	}

	/**
	 * 得到当前日期前后多少天，月，年的日期毫秒值
	 *
	 * @param amont
	 *            数量，前为负数，后为正数
	 * @param type
	 *            类型，可参考Calendar的常量(如：Calendar.HOUR、Calendar.MINUTE、Calendar.SECOND)
	 * @return
	 */
	public static Long getDate(int amont, int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(type, amont);
		return calendar.getTime().getTime();
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到某个日期的月份
	 *
	 * @param date
	 * @return
	 */
	public static String getMonth(Date date) {
		return formatDate(date, "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 see to DateUtils#parsePatterns
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		}
		catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 *
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		return pastHour(date) / 24;
	}

	/**
	 * 获取过去的小时
	 *
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		return pastMinutes(date) / 60;
	}

	/**
	 * 获取过去的分钟
	 *
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		return pastSecond(date) / 60;
	}

	/**
	 * 获取过去的秒
	 *
	 * @param date
	 * @return
	 */
	public static long pastSecond(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t / 1000;
	}

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		return getDistanceOfTwoMinutes(before, after) / 1440;
	}

	/**
	 * 获取两个日期之间的分钟数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoMinutes(Date before, Date after) {
		return getDistanceOfTwoSecond(before, after) / 60;
	}

	/**
	 * 获取两个日期之间的秒数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoSecond(Date before, Date after) {
		return getDistanceOfMillisecond(before, after) / 1000;
	}

	/**
	 * 获取两个日期之间的毫秒数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfMillisecond(Date before, Date after) {
		return (after.getTime() - before.getTime());
	}

	/**
	 * 比较两个日期的大小
	 *
	 * @param before
	 * @param after
	 * @return true after>=before
	 */
	public static boolean compareTwoDays(Date before, Date after) {
		return getDistanceOfMillisecond(before, after) >= 0;
	}

	/**
	 * 判断两个日期是否是同一天
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static boolean isSameDay(Date before, Date after) {
		return Objects.equals(formatDate(before), formatDate(after));
	}

	/**
	 * 判断两个日期是否是同一月
	 * @param before
	 * @param after
	 * @return
	 */
	public static boolean isSameMonth(Date before, Date after) {
		Calendar cal1 = Calendar.getInstance();
        cal1.setTime(before);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(after);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	}

	/**
	 * 获取某月有几天
	 *
	 * @param date
	 *            日期
	 * @return 天数
	 */
	public static int getMonthHasDays(Date date) {
		String yyyyMM = FastDateFormat.getInstance("yyyyMM").format(date);
		String year = yyyyMM.substring(0, 4);
		String month = yyyyMM.substring(4, 6);
		String day31 = ",01,03,05,07,08,10,12,";
		String day30 = "04,06,09,11";
		int day = 0;
		if (day31.contains(month)) {
			day = 31;
		} else if (day30.contains(month)) {
			day = 30;
		} else {
			int y = Integer.parseInt(year);
			if ((y % 4 == 0 && (y % 100 != 0)) || y % 400 == 0) {
				day = 29;
			} else {
				day = 28;
			}
		}
		return day;
	}

	/**
	 * 获取日期是当年的第几周
	 *
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取一天的开始时间（如：2015-11-3 00:00:00.000）
	 *
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getOfDayFirst(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取一天的最后时间（如：2015-11-3 23:59:59.999）
	 *
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getOfDayLast(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取服务器启动时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * 格式化为日期范围字符串
	 *
	 * @param beginDate
	 *            2018-01-01
	 * @param endDate
	 *            2018-01-31
	 * @return 2018-01-01 ~ 2018-01-31
	 */
	public static String formatDateBetweenString(Date beginDate, Date endDate) {
		String begin = DateUtils.formatDate(beginDate);
		String end = DateUtils.formatDate(endDate);
		if (StringUtils.isNoneBlank(begin, end)) {
			return begin + " ~ " + end;
		}
		return null;
	}

	/**
	 * 解析日期范围字符串为日期对象
	 *
	 * @param dateString
	 *            2018-01-01 ~ 2018-01-31
	 * @return new Date[]{2018-01-01, 2018-01-31}
	 */
	public static Date[] parseDateBetweenString(String dateString) {
		Date beginDate = null;
		Date endDate = null;
		if (StringUtils.isNotBlank(dateString)) {
			String[] ss = StringUtils.split(dateString, "~");
			if (ss != null && ss.length == 2) {
				String begin = StringUtils.trim(ss[0]);
				String end = StringUtils.trim(ss[1]);
				if (StringUtils.isNoneBlank(begin, end)) {
					beginDate = DateUtils.parseDate(begin);
					endDate = DateUtils.parseDate(end);
				}
			}
		}
		return new Date[] { beginDate, endDate };
	}

	/**
	 * 获取当前月第一天
	 *
	 * @return
	 */
	public static Date getMonthFirstDay() {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, 0);
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
		Date date1 = calendar1.getTime();
		return date1;
	}

	/**
	 * 获取前一个月第一天
	 *
	 * @return
	 */
	public static Date getLastMonthFirstDay() {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
		Date date1 = calendar1.getTime();
		return date1;
	}

	/**
	 * 获取前一个月最后一天
	 *
	 * @return
	 */
	public static Date getLastMonthLastDay() {
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.DAY_OF_MONTH, 0);
		Date date2 = calendar2.getTime();
		return date2;
	}

	/**
	 * 获取未来某天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取未来某天的日期
	 *
	 * @param past
	 * @return
	 */
	public static Date getFeture(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		return calendar.getTime();
	}

	/**
	 * 获取n月后的日期
	 *
	 * @param n
	 * @return
	 */
	public static Date getFutureMonth(Date date, int n) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(date);
		curr.add(Calendar.MONTH, n);
		return curr.getTime();
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 * 获取指定时间的时间戳
	 *
	 * @param date
	 * @return
	 */
	public static Long getDateTimestamp(Date date) {
		if (date == null) {
			return 0L;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getTimeInMillis();
	}

	/**
	 * 获取未来某天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getFetureDate(Date date, int n) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(date);
		curr.add(Calendar.DAY_OF_MONTH, n);
		return formatDate(curr.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获取n周后的日期
	 *
	 * @param weeks
	 * @return
	 */
	public static String getWeekDay(Date date, int n) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(date);
		curr.add(Calendar.DAY_OF_MONTH, n * 7);
		return formatDate(curr.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获取n月后的日期
	 *
	 * @param n
	 * @return
	 */
	public static String getMonthDay(Date date, int n) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(date);
		curr.add(Calendar.MONTH, n);
		return formatDate(curr.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获取n年后的日期
	 *
	 * @param n
	 * @return
	 */
	public static String getYearDay(Date date, int n) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(date);
		curr.add(Calendar.YEAR, n);
		return formatDate(curr.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获得当天零时零分零秒
	 *
	 * @return
	 */
	public static Date getZeroPointDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.get(Calendar.HOUR_OF_DAY); // 24小时制
		return calendar.getTime();
	}

	/**
	 * 获得当天零时一分零秒
	 *
	 * @return
	 */
	public static Date getZeroOneMinDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.get(Calendar.HOUR_OF_DAY); // 24小时制
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 比较两日期大小
	 *
	 * @param before
	 * @param after
	 * @return true after > before
	 */
	public static boolean compareDate(Date before, Date after) {
		return after.getTime() - before.getTime() > 0;
	}

	// 获得某天最大时间 2017-10-15 23:59:59
	public static Date getEndOfDay(Date date) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
	}

	// 获得某天最小时间 2017-10-15 00:00:00
	public static Date getStartOfDay(Date date) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 获取指定日期时间
	 *
	 * @param day
	 * @param time
	 * @return
	 */
	public static Date getAppointDateTime(int day, String time) {
		String data = LocalDate.now().plusDays(day).toString();
		String datetimeText = data + " " + time;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime datetime = LocalDateTime.parse(datetimeText, formatter);
		return Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 获取指定日期时间
	 *
	 * @param day
	 * @param time
	 * @return
	 */
	public static Date getAppointDateTime(Date day, String time) {
		String datetimeText = formatDate(day) + " " + time;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime datetime = LocalDateTime.parse(datetimeText, formatter);
		return Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 获取上n个小时整点小时时间
	 *
	 * @param date
	 * @return
	 */
	public static String getLastHourTime(Date date, int n) {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY) - n);
		date = ca.getTime();
		return sdf.format(date);
	}

	// 获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取本周的开始时间
	public static Date getBeginDayOfWeek() {
		Date date = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}

	// 获取本周的结束时间
	public static Date getEndDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}

	// 获取本月的开始时间
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	// 获取本月的结束时间
	public static Date getEndDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}

	// 获取今年是哪一年
	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}

	// 获取本月是哪一月
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}

	/**
	 * 获取当前日期是当年的第几周
	 * @return
	 */
	public static String getYearWeek() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-w"));
	}

	/**
	 * 获取n天之后是当年的第几周
	 * @return
	 */
	public static String getYearWeek(int n) {
		return LocalDate.now().plusDays(n).format(DateTimeFormatter.ofPattern("YYYY-w"));
	}

	/**
	 * 获取两个日期之间的所有日期
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<String> getDays(String startTime, String endTime) {
		// 返回的日期集合
		List<String> days = Lists.newArrayList();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
			while (tempStart.before(tempEnd)) {
				days.add(dateFormat.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 获取两个时间中的每一天
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<Date> getDays(Date startTime, Date endTime) {
		// 定义一个接受时间的集合
		List<Date> lDate = Lists.newArrayList();
		lDate.add(startTime);

		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(startTime);

		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(endTime);
		// 测试此日期是否在指定日期之后
		while (endTime.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	/**
	 * @Title: converToDayOfWeek
	 * @Description: 数据库星期值转为中文
	 */
	public static String convertToDayOfWeek(String dayStr) {
		String dow = "";
		switch (dayStr) {
			case "0":
				dow = "星期一";
				break;
			case "1":
				dow = "星期二";
				break;
			case "2":
				dow = "星期三";
				break;
			case "3":
				dow = "星期四";
				break;
			case "4":
				dow = "星期五";
				break;
			case "5":
				dow = "星期六";
				break;
			case "6":
				dow = "星期天";
				break;
		}
		return dow;
	}

	public static void main(String[] args) {

		System.err.println(DateUtils.getFutureMonth(new Date(), 1));
		System.err.println(System.currentTimeMillis());
		System.err.println(getYearWeek(1));
	}

}
