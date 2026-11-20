const form=document.getElementById('calculatorForm');
const periodSelect=document.getElementById('period');
const monthGroup=document.getElementById('monthGroup');
const rainfallLabel=document.getElementById('rainfallLabel');
const rainfallInput=document.getElementById('rainfall');
const emptyState=document.getElementById('emptyState');
const results=document.getElementById('results');
const errorMessage=document.getElementById('errorMessage');
const resultBadge=document.getElementById('resultBadge');
const fmt=new Intl.NumberFormat('en-IN');
const n=v=>fmt.format(v);
function updateMode(){
 const monthly=periodSelect.value==='monthly';
 monthGroup.classList.toggle('hidden',!monthly);
 rainfallLabel.textContent=monthly?'Rainfall for selected month (mm)':'Annual rainfall (mm)';
 rainfallInput.placeholder=monthly?'e.g. 80':'';
 rainfallInput.value=monthly?'80':'700';
 document.getElementById('harvestLabel').textContent=monthly?'Estimated rainwater for '+document.getElementById('month').value:'Estimated annual rainwater';
}
periodSelect.addEventListener('change',updateMode);
document.getElementById('month').addEventListener('change',()=>{if(periodSelect.value==='monthly')document.getElementById('harvestLabel').textContent='Estimated rainwater for '+document.getElementById('month').value;});
form.addEventListener('submit',async e=>{
 e.preventDefault();errorMessage.classList.add('hidden');
 const p={roofArea:Number(document.getElementById('roofArea').value),rainfallMm:Number(rainfallInput.value),roofType:document.getElementById('roofType').value,familyMembers:Number(document.getElementById('familyMembers').value),dailyNeedPerPerson:Number(document.getElementById('dailyNeed').value),period:periodSelect.value,month:document.getElementById('month').value};
 if(!Number.isFinite(p.roofArea)||p.roofArea<=0||!Number.isFinite(p.rainfallMm)||p.rainfallMm<0||!Number.isInteger(p.familyMembers)||p.familyMembers<1||!Number.isFinite(p.dailyNeedPerPerson)||p.dailyNeedPerPerson<0){showError('Please enter valid values. Roof area and family members must be greater than zero.');return;}
 const b=document.getElementById('calculateButton');b.disabled=true;b.textContent='Calculating...';
 try{const res=await fetch('/api/calculate',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(p)});const d=await res.json();if(!res.ok)throw new Error(d.message||d.error||'Could not calculate. Check your inputs.');showResults(d);}
 catch(err){showError(err.message||'Unable to connect to the Java backend.');}
 finally{b.disabled=false;b.textContent='Calculate potential';}
});
function showError(msg){errorMessage.textContent=msg;errorMessage.classList.remove('hidden');}
function showResults(d){
 emptyState.classList.add('hidden');results.classList.remove('hidden');
 const m=d.period==='monthly',unit=m?'litres/month':'litres/year';
 document.getElementById('harvestLabel').textContent=m?'Estimated rainwater for '+d.month:'Estimated annual rainwater';
 document.getElementById('harvestValue').textContent=n(d.collectedLitres);
 document.getElementById('harvestUnit').textContent=m?'litres for '+d.month:'litres per year';
 document.getElementById('averageLabel').textContent=m?'Daily average for this month':'Monthly average';
 document.getElementById('averageValue').textContent=n(m?Math.round(d.collectedLitres/30):d.monthlyAverageLitres);
 document.getElementById('averageUnit').textContent=m?'litres/day (approx.)':'litres/month';
 document.getElementById('demandLabel').textContent=m?'Household demand this month':'Annual household demand';
 document.getElementById('demandValue').textContent=n(d.householdDemandLitres);
 document.getElementById('demandUnit').textContent=unit;
 document.getElementById('balanceLabel').textContent=d.demandMet?'Potential surplus':'Rainwater available';
 document.getElementById('balanceValue').textContent=n(d.demandMet?d.surplusLitres:d.collectedLitres);
 document.getElementById('balanceUnit').textContent=unit;
 document.getElementById('otherLabel').textContent=d.demandMet?'Water shortage':'Remaining water needed';
 document.getElementById('otherValue').textContent=n(d.deficitLitres);
 document.getElementById('otherUnit').textContent=unit;
 resultBadge.textContent=d.demandMet?'Demand covered':'Demand not fully covered';
 resultBadge.classList.toggle('shortage',!d.demandMet);resultBadge.classList.remove('hidden');
 document.getElementById('resultMessage').textContent=d.demandMet?'The estimated rainwater collected is enough to meet household demand for this period, before storage and collection losses.':'The estimated rainwater does not cover the full household demand for this period. The remaining water needed is shown above.';
 const roof=document.getElementById('roofType').selectedOptions[0].textContent.split(' (')[0];
 document.getElementById('roofNote').textContent='Roof: '+roof+' · Runoff coefficient: '+d.runoffCoefficient.toFixed(2)+'.';
}