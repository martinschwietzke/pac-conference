package com.prodyna.pac.conference.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.prodyna.pac.conference.conference.api.model.Conference;
import com.prodyna.pac.conference.talk.api.model.Talk;

public class ConferenceAgenda implements Serializable {

	private static final long serialVersionUID = 1L;
	private Conference conference;
	private List<Talk> talks;

	public ConferenceAgenda(Conference conference, List<Talk> talks) {
		this.conference = conference;
		this.talks = talks;
	}

	public List<DayAgenda> getDayAgendas() {

		List<DayAgenda> agenda = new ArrayList<DayAgenda>();

		long millis = TimeUnit.DAYS.toMillis(1);

		Calendar cal = Calendar.getInstance();

		cal.setTime(conference.getStart());
		cal.add(Calendar.DATE, 1);

		long endDay = conference.getEnd().getTime() / millis;
		long currDay = cal.getTime().getTime() / millis;

		while (endDay >= currDay) {

			DayAgenda da = new DayAgenda();
			da.setDate(cal.getTime());
			for (Talk t : talks) {
				long d1 = t.getStart().getTime() / millis;

				if (d1 == currDay) {
					da.addTalk(t);
				}
			}

			agenda.add(da);

			cal.add(Calendar.DATE, 1);

			currDay = cal.getTime().getTime() / millis;

		}

		return agenda;

	}
}
