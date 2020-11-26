/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fo.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.chart.OhlcChartModel;
import org.primefaces.model.chart.OhlcChartSeries;
import org.primefaces.model.chart.PieChartModel;

@Named
@SessionScoped
public class WaterPay implements Serializable {
	private int past_coldwater;
	private int past_warmwater;
	private int present_coldwater;
	private int present_warmwater;
	private int pay;
	
	@PostConstruct
    public void init() {
    	setPast_coldwater(0);
    	setPast_warmwater(0);
    	setPresent_coldwater(0);
    	setPresent_warmwater(0);
    	setPay(0);
	}
	
	
	public int getPast_coldwater() {
		return past_coldwater;
	}
	
	public void setPast_coldwater(int past_coldwater) {
		this.past_coldwater = past_coldwater;
	}

	public int getPast_warmwater() {
		return past_warmwater;
	}

	public void setPast_warmwater(int past_warmwater) {
		this.past_warmwater = past_warmwater;
	}

	public int getPresent_coldwater() {
		return present_coldwater;
	}

	public void setPresent_coldwater(int present_coldwater) {
		this.present_coldwater = present_coldwater;
	}

	public int getPresent_warmwater() {
		return present_warmwater;
	}

	public void setPresent_warmwater(int present_warmwater) {
		this.present_warmwater = present_warmwater;
	}


	public int getPay() {
		return pay;
	}


	public void setPay(int pay) {
		this.pay = pay;
	}
	
	public void handleKeyEvent()
	{
		pay = present_warmwater;
	}
	
}