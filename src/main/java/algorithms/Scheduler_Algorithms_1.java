package algorithms;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common.DateUtil;
import scheduler_mgmt.model.dto.SchedulerDetail_DTO;
import scheduler_mgmt.model.master.SchedulerMaster;

public class Scheduler_Algorithms_1 {
	private static final Logger logger = LoggerFactory.getLogger(Scheduler_Algorithms_1.class);

	public CopyOnWriteArrayList<SchedulerDetail_DTO> dow1(SchedulerMaster sm) 
	{
		CopyOnWriteArrayList<SchedulerDetail_DTO> scheduleDetailDtos = new CopyOnWriteArrayList<SchedulerDetail_DTO>();
		SchedulerDetail_DTO scheduleDetailDto = new SchedulerDetail_DTO();
		java.util.GregorianCalendar cal = (java.util.GregorianCalendar) Calendar.getInstance();
		DateTimeFormatter dtOnly = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String frTime = null;
		String toTime = null;
		boolean dow_match = false;
		int day = 0;
		int rdow = 0;
		String dt = null;
		Timestamp startDate = sm.getFrDttm();
		Timestamp currDate = startDate;

		// Schedule Data - 1st-number of rows, 2nd-day number of week
		String[] strings = sm.getScheduleData().split(",");
		Integer nofRows = Integer.parseInt(strings[0]);
		rdow = Integer.parseInt(strings[1]);
		cal.setTimeInMillis(startDate.getTime());
		
		logger.info("dow1 rdow : "+rdow);
		logger.info("dow1 day number : "+cal.get(Calendar.DAY_OF_WEEK));
		logger.info("dow1 month number : "+cal.get(Calendar.MONTH));
		logger.info("dow1 year number : "+cal.get(Calendar.YEAR));
		
		
		for (int dctr = 0; dctr<nofRows && !dow_match; dctr++) 
		{				
			day = cal.get(Calendar.DAY_OF_WEEK);
						
			if (rdow==day-1)
			{
				dow_match = true;
				logger.info("found at day : "+day);
				currDate = new Timestamp(cal.getTimeInMillis());
				break;
			} else {
				currDate = DateUtil.addDays(currDate, 1, 0, 0, 0);
				cal.setTimeInMillis(currDate.getTime());				
			}
		}

		if (currDate != null) {
			for (long ctr = 0; ctr < nofRows; ctr++) {
				dt = dtOnly.format(currDate.toLocalDateTime());				
				frTime = dt + " " + sm.getFrtm();
				toTime = dt + " " + sm.getTotm();
				currDate = DateUtil.addDays(currDate, 7, 0, 0, 0);
				scheduleDetailDto = new SchedulerDetail_DTO();
				scheduleDetailDto.setFrDttm(frTime);
				scheduleDetailDto.setToDttm(toTime);
				scheduleDetailDto.setRuleLineSeqNo(sm.getRuleLineSeqNo());
				scheduleDetailDtos.add(scheduleDetailDto);
			}
		}
		return scheduleDetailDtos;
	}

	// ON SPECIFIC DAYS OF MONTH
	public CopyOnWriteArrayList<SchedulerDetail_DTO> dow5(SchedulerMaster sm) {
		CopyOnWriteArrayList<SchedulerDetail_DTO> scheduleDetailDtos = new CopyOnWriteArrayList<SchedulerDetail_DTO>();
		SchedulerDetail_DTO scheduleDetailDto = new SchedulerDetail_DTO();
		java.util.GregorianCalendar cal = (java.util.GregorianCalendar) Calendar.getInstance();
		DateTimeFormatter dtOnly = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String frTime = null;
		String toTime = null;		
		String dt = null;
		Timestamp startDate = sm.getFrDttm();
		
		// Schedule Data - 1st-number of rows, 2nd-day number of week
		String[] strings = sm.getScheduleData().split(",");
		Integer nofRows = Integer.parseInt(strings[0]);
		CopyOnWriteArrayList<Integer> daysList = new CopyOnWriteArrayList<Integer>();		
		Integer mth = null;
		Integer yr = null;
		String dayStr = null;		
		cal.setTimeInMillis(sm.getFrDttm().getTime());				
		mth = cal.get(Calendar.MONTH)+1;
		yr = cal.get(Calendar.YEAR);
		logger.info("start date param : "+sm.getFrDttm());
		logger.info("start date number : "+startDate.getTime());
		logger.info("day number : "+cal.get(Calendar.DAY_OF_WEEK));
		logger.info("month number : "+cal.get(Calendar.MONTH));
		logger.info("year number : "+cal.get(Calendar.YEAR));
				
		for (int i = 1; i < strings.length; i++) {
			daysList.add(Integer.parseInt(strings[i]));
		}

		Collections.sort(daysList);

		for (int i = 0; i < daysList.size(); i++) 
		{
			if(daysList.get(i)<10)
			{
			dayStr = '0'+Integer.toString(daysList.get(i)).trim();
			}
			else
			{
			dayStr = Integer.toString(daysList.get(i)).trim();	
			}
		//	logger.info("day number : "+dayStr);
						
				for (long mthctr = 0; mthctr < nofRows; mthctr++) 
				{
					logger.info("year : "+yr);
					if(mth>12)
					{
					mth=1;	
					yr++;	
					}
					
					if(mth==2 && (daysList.get(i)==30 || daysList.get(i)==31))
					{
					mth++;	
					continue;
					}
					
					if(daysList.get(i)==31 && (mth==4 || mth==6 || mth==9 || mth==11))
					{
					mth++;	
					continue;
					}
					
					if(!DateUtil.leapCheck(yr) && daysList.get(i)>28)
					{
					continue;	
					}
					
					dt= DateUtil.prepareTSString(daysList.get(i), mth, yr, dtOnly);
			//		logger.info("date : "+dt);
					frTime = dt + " " + sm.getFrtm();
					toTime = dt + " " + sm.getTotm();
					scheduleDetailDto = new SchedulerDetail_DTO();
					scheduleDetailDto.setFrDttm(frTime);
					scheduleDetailDto.setToDttm(toTime);
					scheduleDetailDto.setRuleLineSeqNo(sm.getRuleLineSeqNo());
					scheduleDetailDtos.add(scheduleDetailDto);
				}
			}
		
		return scheduleDetailDtos;
	}

	// DAYS PLUS
	public CopyOnWriteArrayList<SchedulerDetail_DTO> dow2(SchedulerMaster sm) {
		CopyOnWriteArrayList<SchedulerDetail_DTO> scheduleDetailDtos = new CopyOnWriteArrayList<SchedulerDetail_DTO>();
		SchedulerDetail_DTO scheduleDetailDto = new SchedulerDetail_DTO();
		DateTimeFormatter dtOnly = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String frTime = null;
		String toTime = null;
		String dt = null;
		Timestamp startDate = sm.getFrDttm();
		Timestamp currDate = startDate;
		
		// Schedule Data - 1st-number of rows, 2nd-days plus
		String[] strings = sm.getScheduleData().split(",");
		Integer nofRows = Integer.parseInt(strings[0]);
		Integer daysPlus = Integer.parseInt(strings[1]);		
		
		if (currDate != null) {
			for (long ctr = 0; ctr < nofRows; ctr++) {
				dt = dtOnly.format(currDate.toLocalDateTime());				
				frTime = dt + " " + sm.getFrtm();
				toTime = dt + " " + sm.getTotm();
				currDate = DateUtil.addDays(currDate, daysPlus, 0, 0, 0);
				scheduleDetailDto = new SchedulerDetail_DTO();
				scheduleDetailDto.setFrDttm(frTime);
				scheduleDetailDto.setToDttm(toTime);
				scheduleDetailDto.setRuleLineSeqNo(sm.getRuleLineSeqNo());
				scheduleDetailDtos.add(scheduleDetailDto);
			}
		}
		return scheduleDetailDtos;
	}
	
}
