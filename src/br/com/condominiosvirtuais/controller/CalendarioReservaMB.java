package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

import br.com.condominiosvirtuais.entity.ItemCalendario;
import br.com.condominiosvirtuais.entity.Reserva;
import br.com.condominiosvirtuais.service.ReservaService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class CalendarioReservaMB implements CalendarDataModel, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(CalendarioReservaMB.class);
	
//  Pinta a cor de fundo de cinza nos caso dos dias desabilitados.
    private static final String DIA_DESABILITADO = "diaDesabilitado";     
	
	
	@Inject
	private ReservaService reservaServiceimpl = null;
	
	public CalendarDataModelItem[] getData(Date[] dateArray) {		
		CalendarDataModelItem[] itensCalendario = new ItemCalendario[dateArray.length];
		try {
	        Calendar calendar = GregorianCalendar.getInstance();
	        Calendar hoje = GregorianCalendar.getInstance();
	        hoje.setTime(new Date());
	        List<Reserva> listaReserva = null;
	        Integer idAmbiente = (Integer) ManagedBeanUtil.getSession(true).getAttribute("idAmbiente");                    	
	        listaReserva = this.reservaServiceimpl.buscarPorIdAmbienteEMaiorIgualDataEPendeteOUAprovado(idAmbiente, hoje.getTime());        
	        for (int i = 0; i < dateArray.length; i++) {	        
	        	calendar.setTime(dateArray[i]);
	            ItemCalendario itemCalendario = new ItemCalendario();
	            itemCalendario.setEnabled(Boolean.TRUE);
	         
    			itemCalendario.setStyleClass("");
	            // Desabilita as datas anteriores a data atual.
	            if (calendar.before(hoje)) {
	            	itemCalendario.setEnabled(Boolean.FALSE);
	            	itemCalendario.setStyleClass(DIA_DESABILITADO);
	            }else{ // Caso a data seja igual ou superior a atual, então verifica se já está reservada.
	            	for (Reserva reserva : listaReserva) {	
	            		SimpleDateFormat sd = new SimpleDateFormat(AplicacaoUtil.i18n("formatoData"));
	            		Date dataReservaCadastrada = reserva.getData();
	            		Date dataCalendario = calendar.getTime();             	 
	            		dataReservaCadastrada = sd.parse(sd.format(dataReservaCadastrada));
	            		dataCalendario = sd.parse(sd.format(dataCalendario));
	            		if (dataReservaCadastrada.equals(dataCalendario)){	            		
	            			itemCalendario.setEnabled(Boolean.FALSE);
	            			itemCalendario.setStyleClass(DIA_DESABILITADO);
	            		}
	            	}
	            }	          
	            itensCalendario[i] = itemCalendario;
	        }   	
        } catch (ParseException e) {
        	logger.error("erro offset " +e.getErrorOffset(), e);
        } catch (NumberFormatException e) {
			logger.error("", e);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
		} catch (Exception e) {
			logger.error("", e);			
		}
		return itensCalendario;
	}

	@Override
	public Object getToolTip(Date arg0) {		
		return null;
	}	
}
