package br.com.condominiosvirtuais.entity;

import org.richfaces.model.CalendarDataModelItem;

public class ItemCalendario implements CalendarDataModelItem {
	
	private boolean enabled;
	
    private String styleClass;
 
	@Override
	public Object getData() {
		return null;
	}

	@Override
	public int getDay() {
		return 0;
	}

	@Override
	public String getStyleClass() {		
		return this.styleClass;
	}

	@Override
	public Object getToolTip() {
		return null;
	}

	@Override
	public boolean hasToolTip() {
		return false;
	}

	@Override
	public boolean isEnabled() {	
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

}
