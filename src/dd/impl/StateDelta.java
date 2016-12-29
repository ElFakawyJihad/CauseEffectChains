package dd.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Permet de comparer des traces, c'est a dire des listes d'etats
 * @author Admin
 *
 */
public class StateDelta {
	public List<Delta> deltas;	
	Trace traceFail;
	Trace traceOk;
	
	public StateDelta(Trace traceFail, Trace traceOk) {
		this.traceFail=traceFail;
		this.traceOk=traceOk;
		deltas = new ArrayList<Delta>();
	}
	
	/**
	 * retorune la liste des differences (variable) entre 2 trace (liste d'etat)
	 * @return
	 */
	public List<Delta> getDeltas() {
		List<Delta> deltas = new ArrayList<>();
		int iF=0;
		int iO=0;
		while(iF<traceFail.states.size() && iO<traceOk.states.size()){
			State sF=traceFail.states.get(iF);
			State sO=traceOk.states.get(iO);			

			if(sF.lineNumber == sO.lineNumber){
				deltas.addAll(compare(sF,sO));
				iO++;
				iF++;
			}else if(sF.lineNumber < sO.lineNumber){
				iF++;
			}else if(sF.lineNumber > sO.lineNumber){
				iO++;
			}
			
		}
		
		//supprimer les deltas presents plusieurs fois car : inutile
		List<Delta> deltaToDel= new ArrayList<>();
		for(int i=0;i<deltas.size();i++){
			
			if(i>0 && deltas.get(i).nameVariable.equals(deltas.get(i-1).nameVariable) ){
	
				if( (deltas.get(i).valueFail!=null && deltas.get(i).valueFail.equals(deltas.get(i-1).valueFail))
						|| deltas.get(i).valueFail == deltas.get(i-1).valueFail ){
					deltaToDel.add(deltas.get(i));
				}
				
			}
		}
		deltas.removeAll(deltaToDel);
		
		return deltas;
	}
	
	/**
	 * Compare 2 etats et retourne leurs differences (variables)
	 * @param sFail
	 * @param sOk
	 * @return
	 */
	public List<Delta> compare(State sFail, State sOk){
		List<Variable> lVF= sFail.variables;
		List<Variable> lVO= sOk.variables;
		
		List<Delta> list =new ArrayList<Delta>();
		List<Variable> listDelVar=new ArrayList<Variable>();
		
		for(Variable vF : lVF){
			for(Variable vO : lVO){

				if (vF.name.equals(vO.name) ){
					listDelVar.add(vF);//les variables presentes dans les 2 traces = a supprimer
					if( ( vF.value!=null && !(vF.value.equals(vO.value)) )  
							||  (vO.value!=null && !(vO.value.equals(vF.value))) ) {
						
							list.add( new Delta(vF.name,vF.value,vO.value,vF.line));
					}
				}
			}
		}

		
		lVF.removeAll(listDelVar);//les variables presentes dans les 2 traces = a supprimer
		//variable que dans trace fail = delta
		for(Variable v : lVF){
				list.add(new Delta(v.name,v.value,null,v.line));
		}
		
		return list;
	}
	
	
	
}
