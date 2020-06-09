package it.univpm.SpringBootApp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import it.univpm.SpringBootApp.model.*;

/**
 * Sottoclasse per calcolo statistiche di tipo numerico
 * @author Cingolani Cristian & Ascani Christian
 */

public class StatNum{
	
	private double sum;
	private int count;
	private double avg;
    private double min;
    private double max;
    private double dev;
    
	
	public StatNum() {
		
	}
	
	/**
     * Metodo che restituisce sum
     * @return sum
     */
    
    public double getSum() {
        return sum;
    }

    /**
     * Metodo che restituisce count
     * @return count
     */
    
    public int getCount() {
        return count;
    }
    
    /**
     * Metodo che restituisce avg
     * @return avg
     */
    
    public double getAvg() {
        return avg;
    }
    
    /**
     * Metodo che restituisce min
     * @return min
     */
    
    public double getMin() {
        return min;
    }
    
    /**
     * Metodo che restituisce max
     * @return max
     */
    
    public double getMax() {
        return max;
    }
    
    /**
     * Metodo che restituisce dev
     * @return dev
     */
    
    public double getDev() {
        return dev;
    }    
	
    /**
     * Metodo che calcola e imposta il valore di sum
     * @param values
     */
    private void setSum(ArrayList<Number> values) {
    	for (Number v : values){
            sum += v.doubleValue();
        }
    }    
    
    /**
     * Metodo che calcola e imposta il valore di count
     * @param values
     */
    private void setCount(ArrayList values) {
    	count= values.size();
    }
      
    
    /**
     * Metodo che calcola e imposta il valore di avg
     * @param avg
     */
    
    private void setAvg(ArrayList<Number> values) {
        avg = getSum()/getCount();
    }
    
    /**
     * Metodo che calcola e imposta il valore di min
     * @param values
     */

    private void setMin(ArrayList<Number> values) {
    	min = values.get(0).doubleValue();
    	for(Number n : values) {
    		if(n.doubleValue() < min) {
                min = n.doubleValue();
            }
        }
    }
    
    /**
     * Metodo che calcola e imposta il valore di max
     * @param max
     */

    private void setMax(ArrayList<Number> values) {
        max = values.get(0).doubleValue();
        for(Number n : values) {
    		if(n.doubleValue() > max) {
                max = n.doubleValue();
            }
        }
    }
    
    /**
     * Metodo che calcola e imposta il valore di dev
     * @param dev
     */

    private void setDev(ArrayList<Number> values) {
    	dev = 0;
    	for(Number numero : values) {
    		dev += Math.pow(numero.doubleValue() - getAvg(), 2);
    	}
        dev=((double) Math.pow(dev/getCount(), 0.5));
    }

    /**
	 * Questo metodo conta le occorrenze di un elemento all'interno di una lista
	 * 
	 * @param lista contiene i valori per i quali si vogliono calcolare le occorrenze
	 * @return restituisce una map chiave-valore dove le chiavi sono gli elementi della lista e i valori le corrispondenti occorrenze
	 */
	public static Map<Object, Integer> getUniqueElement(ArrayList<Object> lista) {
		Map<Object,Integer> mappa = new HashMap<>();  //creazione della mappa
		for(Object obj : lista) {  //scorre la lista
			if(mappa.containsKey(obj))  //controlla se la chiave esiste giï¿½
				mappa.replace(obj, mappa.get(obj) + 1);  //se esiste aumenta il suo valore di 1
			else
				mappa.put(obj, 1);  //se non esiste la crea e le assegna il valore 1
		}
		return mappa;
	}
	
	/**
	 * Metodo che restituisce una mappa nella quale vengono visualizzate tutte le statistiche non numeriche di una lista 
	 * 
	 * @param lista,  lista che fornisce i valori con i quali si possono calcolare tutte le statistiche non numeriche
	 * @return map che contiene come chiavi il nome della statistica e come valore quello calcolato tramite i metodi della classe
	 */
	public Map<String, Object> StrStat(String campo, ArrayList<Object> lista) {
		Map<String, Object> maps = new HashMap<>();  //crea una mappa che contiene le chiavi e i valori delle statistiche non numeriche
		setCount(lista);
		maps.put("field", campo);
		maps.put("count", getCount());
		maps.put("elementi unici", getUniqueElement(lista));
	    return maps;
	}
    
    /**
     * Metodo che restituisce una mappa nella quale vengono visualizzate tutte le statistiche numeriche di una lista 
     * 
     * @param numLista  lista che fornisce i valori con i quali si possono calcolare tutte le statistiche
     * @return map che contiene come chiavi il nome della statistica e come valore quello calcolato tramite i metodi della classe
     */
    public Map<String, Object> NumStat(String campo, ArrayList<Number> numLista) {
    	Map<String, Object> maps = new HashMap<>();  //crea una mappa che contiene le chiavi e i valori delle statistiche numeriche
    	setCount(numLista);
    	setSum(numLista);
    	setAvg(numLista);
    	setMin(numLista);
    	setMax(numLista);
    	setDev(numLista);
    	maps.put("field", campo);
    	maps.put("count", getCount());
    	maps.put("sum", getSum());
    	maps.put("avg", getAvg());
        maps.put("min", getMin());
        maps.put("max", getMax());
        maps.put("dev", getDev());
        return maps;
    }
	
	/**
	 * Metodo che serve a visualizzare il tipo di statistiche in base al campo specificato
	 * 
	 * @param campo contiene il nome dell'attributo del quale si vogliono si vogliono calcolare le statistiche 
	 * @param lista contiene la lista dei valori utili per il calcolo delle statistiche
	 * @return maps, mappa delle statistiche
	 */
    public Map<String, Object> getStat(String campo, ArrayList<Object> lista) {
		Map<String, Object> maps = new HashMap<>();
		if(!lista.isEmpty()) {
			 // se il primo valore e' un numero crea una lista di numeri e gli passa i valori della lista castati a Number
			if (lista.get(0) instanceof Number) { 
				ArrayList<Number> numLista = new ArrayList<>();
				for (Object elem : lista) {
					numLista.add(((Number) elem));
				}
				maps = NumStat(campo, numLista); // calcola le statistiche numeriche
			} else {
				maps = StrStat(campo, lista);
			}
		}
		return maps;
	}
    
    /**
	 * Metodo che restituisce una mappa nella quale vengono visualizzate le statistiche
	 *  
	 * */
    public Map<String, Object> NumStatData(ArrayList<Number> numLista) {
    	Map<String, Object> maps = new LinkedHashMap<>();  //crea una mappa che contiene le chiavi e i valori delle statistiche numeriche
    	setCount(numLista);
    	setSum(numLista);
    	setAvg(numLista);
    	setMin(numLista);
    	setMax(numLista);
    	setDev(numLista);
    	maps.put("count", getCount());
    	maps.put("avg", getAvg());
        maps.put("min", getMin());
        maps.put("max", getMax());
        maps.put("dev", getDev());
        return maps;
    }
    
    public Map<String, Object> StatIstoYear(ArrayList<Number> numLista) {
    	Map<String, Object> maps = new LinkedHashMap<>();
    	int[] years = new int[11];
    	for(int i=0; i < 11; i++) {
    		years[i]=0;
    	}
    	
    	for(int i=0; i < numLista.size(); i++) {
    		for(int j=2010; j < 2021; j++) {
    			if(numLista.get(i).intValue()+1900==j) years[j-2010]++;
    		}
    	}
    	maps.put("2010",years[0]);
    	maps.put("2011",years[1]);
    	maps.put("2012",years[2]);
    	maps.put("2013",years[3]);
    	maps.put("2014",years[4]);
    	maps.put("2015",years[5]);
    	maps.put("2016",years[6]);
    	maps.put("2017",years[7]);
    	maps.put("2018",years[8]);
    	maps.put("2019",years[9]);
    	maps.put("2020",years[10]);
    	return maps;	
    }
    
    public Map<String, Object> StatIstoMonth(ArrayList<Number> numLista) {
    	Map<String, Object> maps = new LinkedHashMap<>();
    	int[] months = new int[12];
    	for(int i=0; i < 12; i++) {
    		months[i]=0;
    	}
    	
    	for(int i=0; i < numLista.size(); i++) {
    		for(int j=1; j < 13; j++) {
    			if(numLista.get(i).intValue()==j) months[j-1]++;
    		}
    	}
    	maps.put("Gennaio",months[0]);
    	maps.put("Febbraio",months[1]);
    	maps.put("Marzo",months[2]);
    	maps.put("Aprile",months[3]);
    	maps.put("Maggio",months[4]);
    	maps.put("Giugno",months[5]);
    	maps.put("Luglio",months[6]);
    	maps.put("Agosto",months[7]);
    	maps.put("Settembre",months[8]);
    	maps.put("Ottobre",months[9]);
    	maps.put("Novembre",months[10]);
    	maps.put("Dicembre",months[11]);
    	return maps;	
    }
    
    
    
    public Map<String, Object> StatIstoDay(ArrayList<Number> numLista) {
    	Map<String, Object> maps = new LinkedHashMap<>();
    	int[] days = new int[31];
    	for(int i=0; i < 31; i++) {
    		days[i]=0;
    	}
    	
    	for(int i=0; i < numLista.size(); i++) {
    		for(int j=1; j < 32; j++) {
    			if(numLista.get(i).intValue()==j) days[j-1]++;
    		}
    	}
    	maps.put("1",days[0]);
    	maps.put("2",days[1]);
    	maps.put("3",days[2]);
    	maps.put("4",days[3]);
    	maps.put("5",days[4]);
    	maps.put("6",days[5]);
    	maps.put("7",days[6]);
    	maps.put("8",days[7]);
    	maps.put("9",days[8]);
    	maps.put("10",days[9]);
    	maps.put("11",days[10]);
    	maps.put("12",days[11]);
    	maps.put("13",days[12]);
    	maps.put("14",days[13]);
    	maps.put("15",days[14]);
    	maps.put("16",days[15]);
    	maps.put("17",days[16]);
    	maps.put("18",days[17]);
    	maps.put("19",days[18]);
    	maps.put("20",days[19]);
    	maps.put("21",days[20]);
    	maps.put("22",days[21]);
    	maps.put("23",days[22]);
    	maps.put("24",days[23]);
    	maps.put("25",days[24]);
    	maps.put("26",days[25]);
    	maps.put("27",days[26]);
    	maps.put("28",days[27]);
    	maps.put("29",days[28]);
    	maps.put("30",days[29]);
    	maps.put("31",days[30]);
    	
 
    	return maps;	
    }
    

}
