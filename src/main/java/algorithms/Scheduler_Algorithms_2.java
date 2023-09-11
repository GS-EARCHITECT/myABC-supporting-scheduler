package algorithms;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import common.DateUtil;
import scheduler_mgmt.model.dto.SchedulerDetail_DTO;
import scheduler_mgmt.model.master.SchedulerMaster;

public class Scheduler_Algorithms_2 
{
	private static final Logger logger = LoggerFactory.getLogger(Scheduler_Algorithms_2.class);

	@Autowired
	
	
	public CopyOnWriteArrayList<SchedulerDetail_DTO> schedule_events(SchedulerMaster sm) {
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
		
		
		for (int dctr = 0; dctr<100 && !dow_match; dctr++) 
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
	
}
