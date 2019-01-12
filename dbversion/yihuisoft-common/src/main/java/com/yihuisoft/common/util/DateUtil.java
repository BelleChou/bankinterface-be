package com.yihuisoft.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * @author tonywu
 * @version v1.0.0
 */
public class DateUtil {

	/**yyyy格式*/
	public static final String sdfyyyy = "yyyy";
	/**yyyy-MM-dd格式*/
	public static final String sdfyyyy_MM_dd = "yyyy-MM-dd";
	/**yyyy年MM月dd日格式*/
	public static final String sdfyyyyCMMCddC = "yyyy年MM月dd日";
	/**yyyyMMdd格式*/
	public static final String sdfyyyyMMdd = "yyyyMMdd";
	/**yyyy-MM-dd HH:mm:ss格式*/
	public static final String sdfyyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";
	/**yyyy-MM-dd HH:mm格式*/
	public static final String sdfyyyy_MM_ddHHmm = "yyyy-MM-dd HH:mm";
	/**yyyy年MM月dd日 HH:mm:ss格式*/
	public static final String sdfyyyyCMMCddCHHmmss = "yyyy年MM月dd日 HH:mm:ss";
	/**HH:mm:ss格式*/
	public static final String sdfHHmmss = "HH:mm:ss";
	/**yyyyMMddHHmmss格式*/
	public static final String sdfyyyyMMddHHmmss = "yyyyMMddHHmmss";
	/**yyyyMM格式*/
	public static final String sdfyyyyMM = "yyyyMM";
	/**yyyy-MM格式*/
	public static final String sdfyyyy_MM = "yyyy-MM";
	/**MMdd格式*/
	public static final String sdfMMdd = "MMdd";
	/**HHmm格式*/
	public static final String sdfHHmm = "HHmm";

	/**
	 * 获取yyyyMMddHHmmss格式
	 * @return
	 */
	public static String getSdfTimes() {
		return new SimpleDateFormat(sdfyyyyMMddHHmmss).format(new Date());
	}
	
	/**
	 * 获取YYYYMM格式
	 * @return
	 */
	public static String getMonth() {
		return new SimpleDateFormat(sdfyyyy_MM).format(new Date());
	}
	
	
	 /**
     * 获取上一个月
     *
     * @return
     */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        return dft.format(cal.getTime());
    }

    /**
     *
     * 描述:获取下一个月.
     *
     * @return
     */
    public static String getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        return dft.format(cal.getTime());
    }
	
	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getYear() {
		return new SimpleDateFormat(sdfyyyy).format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * @return
	 */
	public static String getDay() {
		return new SimpleDateFormat(sdfyyyy_MM_dd).format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * @return
	 */
	public static String getDays(){
		return new SimpleDateFormat(sdfyyyyMMdd).format(new Date());
	}

	
	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * @return
	 */
	public static String getTime() {
		return new SimpleDateFormat(sdfyyyy_MM_ddHHmmss).format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author fh
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(date==null || "".equals(date) || "null".equals(date))
				return null;
						
			return fmt.parse(date);
		} catch (ParseException e) {
			//e.printStackTrace();
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDate(String date,String sdfmt) {
		try {
			if(date==null || "".equals(date) || "null".equals(date))
				return null;
						
			return new SimpleDateFormat(sdfmt).parse(date);
		} catch (ParseException e) {
			//e.printStackTrace();
			System.out.println(e);
			return null;
		}
	}


	/**
	 * 格式化日期
	 * @return
	 */
	public static String fomatDate(Date date,String sdfmt) {
		
		try {
			if(date==null )
				return null;
						
			return new SimpleDateFormat(sdfmt).format(date);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e);
			return null;
		}
	}

	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDateToDate(Date date,String sdfmt) {
		return fomatDate(fomatDate(date,sdfmt),sdfmt);
	}

	/**
	 * 校验日期是否合法
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	
	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {

			return (int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);

		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			System.out.println(e);
			return 0;
		}
	}
	 
	/**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				//e.printStackTrace();
				System.out.println(e);
			}
			if(beginDate != null && endDate != null)
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * <li>功能描述：时间相减得到秒
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySubTime(String beginDateStr,String endDateStr){
        long second=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				//e.printStackTrace();
				System.out.println(e);
			}
		    if(beginDate != null && endDate != null)
               second=(endDate.getTime()-beginDate.getTime())/(1000);
      
        return second;
    }
    
    
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdfd.format(date);
    }

	/**
	 * 得到n天之后的日期
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(int days) {
		int daysInt = days;

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdfd.format(date);
	}
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static Date getAfterDay(int days) {    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		return canlendar.getTime();
    }

	/**
	 * 得到n天之后的日期
	 * @param days
	 * @return
	 */
	public static Date getAfterDay(int days,String sdfmt) {
		return fomatDate(fomatDate(getAfterDay(days),sdfmt),sdfmt);
	}

	/**
	 * 获取当前月的天数
	 * @return
	 */
	public static int getDayOfMonth(){
		Calendar calendar = Calendar.getInstance();
		return calendar.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 根据指定时间得到n小时之后的时间
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getAfterHour(Date date, int n) {
		Calendar canlendar = Calendar.getInstance();
		canlendar.setTime(date);
		canlendar.add(Calendar.HOUR, n);
		return canlendar.getTime();
	}

	/**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
		return sdf.format(date);
    }

	/**
	 * 返回第一个参数加上第二个参数（天）之后的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateNext(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * 根据日期字串,传入几天,得到向后几天日期
	 * @param date 日期
	 * @param day 几天
     * @return
     */
	public static Date getDateNext(String date, int day){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fomatDate(date));
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
    
    /**
     * 取得上月日期
     * @param monthNum 月数
     * @return
     */
    public static String getLastMonth(int monthNum) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.MONTH, -1*monthNum);
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		return dateFormat.format(c.getTime());
    }

	/**
	 * 下月第一天
	 * @return
	 */
	public static Date nextMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 上月第一天
	 * @return
	 */
	public static Date lastMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 将日期中的时分秒清零
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		return cal1.getTime();
	}

	/**
	 * 1970/01/01至今的秒数转换成Date
	 * @param timeStamp
	 * @return
	 */
	public static Date getDateByTimeStamp(Long timeStamp){
		return new Date(timeStamp*1000);
	}

	/**
	 * 1970/01/01至今的豪秒数转换成Date
	 * @param timeStampMs
	 * @return
	 */
	public static Date getDateByTimeStampMs(Long timeStampMs){
		return new Date(timeStampMs);
	}

	/**
	 * 时间转换成秒 1970/01/01至今的秒数（Date转long），等于new Date().getTime()/1000
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static long getTimeStampByDate(Date date)
	{
		long stamp = 0L;
		Date date1 = date;
		Date date2 = null;
		try {
			date2 = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).parse("1970/01/01 08:00:00");
			stamp = (date1.getTime() - date2.getTime()) / 1000L;
		} catch (Exception e) {
			stamp = 0L;
		}

		return stamp;
	}

	/**
	 * 时间转换成秒 1970/01/01至今的豪秒数（Date转long）
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static long getTimeStampMsByDate(Date date)
	{
		long stamp = 0L;
		Date date1 = date;
		Date date2 = null;
		try {
			date2 = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).parse("1970/01/01 08:00:00");
			stamp = (date1.getTime() - date2.getTime());
		} catch (Exception e) {
			stamp = 0L;
		}

		return stamp;
	}

	/**
	 * 获取当前时间之前或之后几小时 hour
	 * @param hour
	 * @return
	 */
	public static Date getTimeByHour(int hour) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

		//return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		return calendar.getTime();
	}


	/**
	 * main函数
	 * @param args
	 */
	public static void main(String[] args) {
//    	Date date = new Date();
//    	System.out.println(date);
//    	Date date2 = getAfterHour(date, 10);
//    	System.out.println(date2);
//		System.out.println(getDays());
		System.out.println(getTimeStampByDate(new Date())+"========="+getTimeStampByDate(DateUtil.getAfterDay(7)));

	}
}
