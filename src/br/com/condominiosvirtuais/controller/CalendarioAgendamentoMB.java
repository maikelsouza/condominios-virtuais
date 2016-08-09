package br.com.condominiosvirtuais.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

import br.com.condominiosvirtuais.entity.ItemCalendario;

@Named @RequestScoped
public class CalendarioAgendamentoMB implements CalendarDataModel {
	
	// CSS pinta a data de cinza. Obs.: css nativo do richfaces
	private static final String DATA_DESABILITADA = "rf-cal-boundary-day";

	@Override
	public CalendarDataModelItem[] getData(Date[] dateArray) {
		CalendarDataModelItem[] itensCalendario = new ItemCalendario[dateArray.length];
		Calendar calendar = GregorianCalendar.getInstance();
		Calendar hoje = GregorianCalendar.getInstance();
	    hoje.setTime(new Date());
        for (int i = 0; i < dateArray.length; i++) {
        	calendar.setTime(dateArray[i]);
            ItemCalendario itemCalendario = new ItemCalendario();
            itemCalendario.setEnabled(Boolean.TRUE);
			itemCalendario.setStyleClass("");
            //  Desabilita os domingos e datas anteriores a data atual.
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.before(hoje))   {
				itemCalendario.setEnabled(Boolean.FALSE);
				itemCalendario.setStyleClass(DATA_DESABILITADA);
			}
			itensCalendario[i] = itemCalendario;
        }	
		return itensCalendario;
	}

	@Override
	public Object getToolTip(Date arg0) {
		return null;
	}

}
